package com.face.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 上传文件相关的配置
 */
@Configuration
public class UploadConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("file:/root/springboot/File/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/root/springboot/images/");
        super.addResourceHandlers(registry);
    }
}
