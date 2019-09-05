package com.face.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 百度API配置文件
 */
@Configuration
@PropertySource("classpath:baidu.properties")
public class BaiduConfig {

    @Value("app.id:")
    public static String appID;

    @Value("api.key:")
    public static String appKey;

    @Value("api.sercet.key:")
    public static String appSercetKey;

}
