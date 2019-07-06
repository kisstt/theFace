package com.face.generator;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;

/**
 * mybatis-plus自动生成配置
 */
public class CodeGenerator {

    public static void main(String[] args) {
        GlobalConfig globalConfig=new GlobalConfig();
        globalConfig.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        globalConfig.setAuthor("jobob");
        globalConfig.setOpen(false);

        DataSourceConfig dataSourceConfig=new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://127.0.0.1:3306/facedb?useSSL=false&serverTimezone=UTC");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("fhx.1234");

        TemplateConfig templateConfig = new TemplateConfig()
                .setEntity("templates/entity2.java");

        AutoGenerator mpg = new AutoGenerator();
        mpg.setTemplate(templateConfig);

    }
}
