package com.face.db.plugin;

import com.face.db.plugin.dialect.Dialect;
import com.face.page.Page;
import com.face.utils.ReflectUtils;
import com.face.utils.SqlLogUtils;
import com.face.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.xml.bind.PropertyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * 分页插件
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
public class SqlPagerInterceptor implements Interceptor {

    private Dialect dialect;

    private String pageSqlId = "";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String sql = null;
        BoundSql boundSql = null;
        if (invocation.getTarget() instanceof RoutingStatementHandler) {
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtils.getValueByFieldName(
                    (RoutingStatementHandler) invocation.getTarget(), "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectUtils.
                    getValueByFieldName(delegate, "mappedStatement");
            Page page = Page.threadLocal.get();
            boundSql = delegate.getBoundSql();
            sql = boundSql.getSql();
            if (isPageSql(mappedStatement, page)) {
                int totalCount = getTotalCount(invocation,
                        delegate, mappedStatement);
                page.setTotal(totalCount);

                //替换查询sql为分页语句
                sql = generatePageSql(boundSql.getSql(), page);
                ReflectUtils.setValueByFieldName(boundSql, "sql", sql);

            } else {
                sql = generateSorderSql(boundSql.getSql(), page);
                ReflectUtils.setValueByFieldName(boundSql, "sql", sql);
            }
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String dialectClass = properties.getProperty("dialectClass");
        try {
            this.dialect = (Dialect) Class.forName(dialectClass).newInstance();
        } catch (Exception e) {
            throw new RuntimeException("cannot create dialect instance by dialectClass:" + dialectClass, e);
        }

        try {
            if (StringUtils.isEmpty(this.pageSqlId = properties.getProperty("pageSqlId"))) {
                throw new PropertyException("pageSqlId property is not found!");
            }
        } catch (PropertyException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     *
     * @param sql
     * @param page
     * @return
     */
    private String generatePageSql(String sql, Page page) {
        sql = generateSorderSql(sql, page);
        sql = dialect.getLimitString(sql, page.getPageNo(), page.getPageSize());
        return sql;
    }

    /**
     * 排序sql
     *
     * @param sql
     * @param page
     * @return
     */
    private String generateSorderSql(String sql, Page page) {
        if (Utils.isEmpty(page) || (!sql.startsWith("select") && !sql.startsWith("SELECT"))) {
            return sql;
        }
        //添加排序操作
        if (Utils.notEmpty(page.getSidx()) && Utils.notEmpty(page.getSord())) {
            String upperSql = sql.replaceAll("\n", "").replaceAll(" +", " ").replaceAll("\t", "").toUpperCase();

            sql += upperSql.contains(" ORDER BY ") ? " , " : " ORDER BY ";
            String colName = Utils.filed2ColName(page.getSidx());
            colName = colName.charAt(0) == '_' ? colName.substring(1) : colName;
            String sortName = colName;

            //字段是否包含前缀
            if (upperSql.indexOf("." + colName.toUpperCase()) != -1) {
                int index = upperSql.indexOf(colName.toUpperCase());
                upperSql = upperSql.substring(0, index);
                String prefix = upperSql.substring(upperSql.lastIndexOf(",") + 1);
                if (prefix.contains(" ")) {
                    prefix = upperSql.substring(upperSql.lastIndexOf(" ") + 1);
                }
                sortName = prefix + colName;
            }

            sql += sortName + " " + page.getSord();
        }
        return sql;
    }

    /**
     * 是否需要进行分页查询
     *
     * @return
     */
    private boolean isPageSql(MappedStatement statement, Page page) {
        if (Utils.isEmpty(page)) {
            return false;
        }
        if (page.getPageSize() <= 0) {
            return false;
        }
        if (!statement.getId().matches(".*(" + this.pageSqlId + ")$")) {
            return false;
        }
        return true;
    }

    /**
     * 获取查询数据的总数量
     *
     * @param invocation
     * @param delegate
     * @param mappedStatement
     * @return
     * @throws Exception
     */
    private int getTotalCount(Invocation invocation, BaseStatementHandler delegate, MappedStatement mappedStatement) throws Exception {
        BoundSql boundSql = delegate.getBoundSql();
        Object paramObject = boundSql.getParameterObject();
        String sql = boundSql.getSql();
        String countSqlId = mappedStatement.getId().replaceAll(pageSqlId, "Count");
        MappedStatement countMappedStatement = null;
        if (mappedStatement.getConfiguration().hasStatement(countSqlId)) {
            countMappedStatement = mappedStatement.getConfiguration().getMappedStatement(countSqlId);
        }
        String countSql = null;
        if (countMappedStatement != null) {
            countSql = countMappedStatement.getBoundSql(paramObject).getSql();
        } else {
            countSql = "SELECT COUNT(1) FROM (" + sql + ") T_COUNT";
        }
        int totalCount = 0;
        PreparedStatement countStmt = null;
        ResultSet resultSet = null;
        try {
            Connection conn = null;
            Object obj = invocation.getArgs()[0];
            if (obj instanceof Connection) {
                conn = (Connection) obj;
            } else if (obj instanceof PreparedStatement) {
                conn = ((PreparedStatement) obj).getConnection();
            }
            countStmt = conn.prepareStatement(countSql);
            BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(),
                    countSql, boundSql.getParameterMappings(), paramObject);
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            if (parameterMappings != null) {
                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    if (parameterMapping.getMode() != ParameterMode.OUT) {
                        String propertyName = parameterMapping.getProperty();
                        if (boundSql.hasAdditionalParameter(propertyName)) {
                            countBoundSql.setAdditionalParameter(propertyName, boundSql.getAdditionalParameter(propertyName));
                        }
                    }
                }
            }
            setParameters(countStmt, mappedStatement, countBoundSql, paramObject);
            long start = System.currentTimeMillis();
            resultSet = countStmt.executeQuery();
            long end = System.currentTimeMillis();
            if (resultSet.next()) {
                totalCount = resultSet.getInt(1);
            }
            SqlLogUtils.logSql(countBoundSql, countSqlId, end - start);
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } finally {
                if (countStmt != null) {
                    countStmt.close();
                }
            }
        }
        return totalCount;
    }

    /**
     * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.
     * DefaultParameterHandler
     *
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws SQLException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql, Object parameterObject) throws SQLException {
        ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings != null) {
            Configuration configuration = mappedStatement.getConfiguration();
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            for (int i = 0; i < parameterMappings.size(); i++) {
                ParameterMapping parameterMapping = parameterMappings.get(i);
                if (parameterMapping.getMode() != ParameterMode.OUT) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if (boundSql.hasAdditionalParameter(propertyName)) {
                        value = boundSql.getAdditionalParameter(propertyName);
                    } else if (parameterObject == null) {
                        value = null;
                    } else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject(parameterObject);
                        value = metaObject.getValue(propertyName);
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if (value != null & jdbcType == null) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    try {
                        typeHandler.setParameter(ps, i + 1, value, jdbcType);
                    } catch (TypeException e) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
                    } catch (SQLException e) {
                        throw new TypeException("Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e);
                    }
                }
            }
        }
    }


}