package com.face.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * 百度API配置文件
 */
@Configuration
@PropertySource("classpath:baidu.properties")
public class BaiduConfig {

    @Value("${app.id:}")
    public  String APP_ID;

    @Value("${api.key:}")
    public  String APP_KEY;

    @Value("${api.sercet.key:}")
    public  String APP_SERCET_KEY;

}
