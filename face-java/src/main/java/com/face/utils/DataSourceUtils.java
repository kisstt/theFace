package com.face.utils;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

public class DataSourceUtils {
    private DataSourceUtils() {

    }

    private static final int TX_METHOD_TIMEOUT = 500;

    /**
     * @param dataSource
     * @param interceptors
     * @param mapperPaths
     * @param typeAliases
     * @return
     * @throws IOException
     */
    public static SqlSessionFactoryBean getSqlSessionFactoryBean(DataSource dataSource, Interceptor[] interceptors, String mapperPaths, String typeAliases) throws IOException {
        Configuration configuration = new Configuration();
        //设置驼峰
        configuration.setMapUnderscoreToCamelCase(true);

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String[] paths = mapperPaths.split(Constants.SPLIT_COMMON);

        //mapper目录
        List<Resource> list = new ArrayList<Resource>();

        for (int i = 0; i < paths.length; i++) {
            Resource[] resources = resolver.getResources(paths[i]);
            for (Resource resource : resources) {
                list.add(resource);
            }
        }

        Resource[] resourceArr = new Resource[list.size()];
        list.toArray(resourceArr);

        sqlSessionFactoryBean.setMapperLocations(resourceArr);
        sqlSessionFactoryBean.setConfiguration(configuration);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliases);
        sqlSessionFactoryBean.setPlugins(interceptors);
        return sqlSessionFactoryBean;
    }

    public static TransactionInterceptor getTransactionInterceptor(PlatformTransactionManager transactionManager) {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        RuleBasedTransactionAttribute ruleBasedTransactionAttribute = new RuleBasedTransactionAttribute();
        ruleBasedTransactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));

        ruleBasedTransactionAttribute.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        ruleBasedTransactionAttribute.setTimeout(TX_METHOD_TIMEOUT);

        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("save*", ruleBasedTransactionAttribute);
        txMap.put("add*", ruleBasedTransactionAttribute);
        txMap.put("edit*", ruleBasedTransactionAttribute);
        txMap.put("modify*", ruleBasedTransactionAttribute);
        txMap.put("del*", ruleBasedTransactionAttribute);
        txMap.put("lock*", ruleBasedTransactionAttribute);

        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    /**
     * @param jdbcType
     * @return
     */
    public static String getDialect(String jdbcType) {

        if ("oracle".equalsIgnoreCase(jdbcType)) {
            return "com.sunlord.common.core.db.plugin.dialect.OracleDialect";
        }
        if ("mysql".equalsIgnoreCase(jdbcType)) {
            return "com.face.db.plugin.dialect.MySQLDialect";
        }

        return "com.sunlord.common.core.db.plugin.dialect.PostgreSQLDialect";
    }
}
