package com.face.generator;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConditionalOnProperty(value = "genertor.enable",havingValue = "true")
public class GeneratorConfig {

    @Value("${project.path}")
    public String projectPath;

    @Value("${package.path}")
    public String packagePath;

    @Value("${table.name.arr}")
    public String tableNames;

    public String[] getTables(){
        return tableNames.split(",");
    }

}
