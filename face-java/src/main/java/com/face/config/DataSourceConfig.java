package com.face.config;

import com.face.db.MapperScanner;
import com.face.db.plugin.SqlPagerInterceptor;
import com.face.db.plugin.SqlTimeInterceptor;
import com.face.utils.DataSourceUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@Primary
@MapperScanner(value = "com.face.dao")
public class DataSourceConfig {

    @Value("${face.db.jdbc-type}")
    public String jdbcType;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "face.db")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        String mapperPath = "classpath*:com/face/mapper/*.xml";
        String typeAliases = "com.face.po";
        SqlPagerInterceptor pagerInterceptor = new SqlPagerInterceptor();
        //日志格式化插件
        SqlTimeInterceptor timeInterceptor = new SqlTimeInterceptor();
        Properties properties = new Properties();
        properties.setProperty("dialect", jdbcType);
        properties.setProperty("dialectClass", DataSourceUtils.getDialect(jdbcType));
        properties.setProperty("pageSqlId", "ByPage");
        pagerInterceptor.setProperties(properties);
        Interceptor[] interceptors = new Interceptor[]{pagerInterceptor, timeInterceptor};
        SqlSessionFactoryBean sqlSessionFactoryBean = DataSourceUtils.getSqlSessionFactoryBean(dataSource, interceptors, mapperPath, typeAliases);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean("txManager")
    public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
