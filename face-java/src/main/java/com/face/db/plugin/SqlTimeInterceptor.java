/**
 *
 */
package com.face.db.plugin;


import com.face.utils.SqlLogUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;

/**
 * @author Administrator
 *
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})})
public class SqlTimeInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        StatementHandler statementHandler = (StatementHandler) target;
        BoundSql boundSql = statementHandler.getBoundSql();
        long start = System.currentTimeMillis();
        Object res = invocation.proceed();
        long end = System.currentTimeMillis();
        SqlLogUtils.logSql(boundSql, "", end - start);
        return res;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}

