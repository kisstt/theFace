package com.face.generator;

import com.face.generator.mysql.GenerateDao;
import com.face.generator.mysql.GenerateMapper;
import com.face.generator.mysql.GeneratePo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.sql.SQLException;

@SpringBootApplication(scanBasePackages = {"com.face.generator"})
@PropertySource(value = {"classpath:application-generate.properties"})
public class GeneratorApplication {
    public static void main(String[] args) throws IOException, SQLException {
        ConfigurableApplicationContext app= SpringApplication.run(GeneratorApplication.class, args);
        GeneratorConfig config = app.getBean(GeneratorConfig.class);
        String[] tableNames = config.getTables();
        System.out.println("Po， Dao，Mapper生成开始");
        for (String tableName:tableNames){
            GenerateDao.generateIDao(config.projectPath,
                    config.packagePath,tableName);
            GenerateMapper.generate(config.projectPath,
                    config.packagePath,tableName,"");
            GeneratePo.generatePo(config.projectPath,
                    config.packagePath,tableName);
        }
        System.out.println("Po， Dao，Mapper生成结束");
        app.close();
    }
}
