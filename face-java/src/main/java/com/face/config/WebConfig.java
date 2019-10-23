package com.face.config;

import com.face.db.plugin.GlobalInterceptor;
import com.face.resolver.MultiRequestBodyArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public GlobalInterceptor getSessionInterceptor() {
        return new GlobalInterceptor();
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getSessionInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login", "/api/user/register");
        super.addInterceptors(registry);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(new MultiRequestBodyArgumentResolver());
    }
}
