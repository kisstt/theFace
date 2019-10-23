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

    @Value("${app.id:}")
    public static String APP_ID;

    @Value("${api.key:}")
    public static String APP_KEY;

    @Value("${api.sercet.key:}")
    public static String APP_SERCET_KEY;

}
