package com.face.config;

import com.face.db.plugin.GlobalInterceptor;
import com.face.resolver.MultiRequestBodyArgumentResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;
import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {


    @Value("${image.dir}")
    public  String IMAGE_DIR;

    @Value("${upload.dir}")
    public  String UPLOAD_URL;

    @Value("${image.url}")
    public  String IMAGE_URL;


    @Bean
    public GlobalInterceptor getSessionInterceptor() {
        return new GlobalInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login",
                        "/api/user/register",
                        "/api/user/search",
                        "/api/face/search");
        super.addInterceptors(registry);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new MultiRequestBodyArgumentResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations(IMAGE_DIR);
        super.addResourceHandlers(registry);
    }
}
