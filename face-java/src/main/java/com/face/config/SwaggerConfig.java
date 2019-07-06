package com.face.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Value("${swagger.show}")
    private boolean showSwagger;


    @Bean
    public Docket swaggerSpringPlugin(){
     if(showSwagger){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }else {
        return new Docket( DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis( RequestHandlerSelectors.basePackage("cmo.00.00.0.0"))
                .paths( PathSelectors.any())
                .build();
    }
    }

    private ApiInfo apiInfo() {
        if(showSwagger){
            return new ApiInfoBuilder()
                    //页面标题
                    .title("face-CRUD项目")
                    //描述
                    .description("api查询测试接口")
                    .termsOfServiceUrl("API terms of service")
                    //版本号
                    .version("1.0")
                    .build();
        }else {
            return new ApiInfoBuilder()
                    //页面标题
                    .title("项目生产阶段禁止使用swagger查看接口")
                    .description("基于springBoot的整合开发")
                    .termsOfServiceUrl("http://www.baodu.com/")
                    .contact("")
                    .version("")
                    .build();
        }

    }

}
