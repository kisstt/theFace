package com.face.generator.mysql;

import com.face.generator.utils.DateUtils;
import com.face.generator.utils.GenerateUtils;
import com.face.generator.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneratePo {
    /**
     * 生成po
     *
     * @param tableName
     * @throws SQLException
     */
    public static void generatePo(String filePath, String packagePath, String tableName) throws SQLException, IOException {
        tableName = tableName.trim();
        DomainClassProperty domainClassProperty = new DomainClassProperty(tableName);
        String poPath = filePath+"/"+packagePath.replace(".", "/")+"/po";
        // 类名
        File file = new File(poPath+"/" + domainClassProperty.getPoClassName() + ".java");

        // 行并接开始
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("package "+packagePath+".po;");
        lines.add("");
        lines.add("import java.io.Serializable;");

        if(domainClassProperty.isHasDateType()){
            lines.add("import java.util.Date;");
            lines.add("import org.springframework.format.annotation.DateTimeFormat;");
            lines.add("import com.fasterxml.jackson.annotation.JsonFormat;");
        }

        lines.add("import lombok.Data;");
        lines.add("");
        //注释
        lines.add("/**");
        lines.add(" *");
        lines.add(" * Generator create");
        lines.add(" * @author Generator");
        lines.add(" * @date " + DateUtils.date2String(DateUtils.getNowDate()));
        lines.add(" */");
        lines.add("");
        lines.add("@Data");
        lines.add("public class " + domainClassProperty.getPoClassName() + " implements Serializable { ");
        // -----------------------------------
        // 取数据库表结构拼接属性字段
        ArrayList<String> columnList = domainClassProperty.getAlColumn();
        for (int i = 0; i < columnList.size(); i++) {
            //判断是否有备注
            if(Utils.notEmpty(domainClassProperty.getCommentMap().get(columnList.get(i)))){
                String comment = domainClassProperty.getCommentMap().get(columnList.get(i));
                lines.add("\t/**" );
                lines.add("\t*"+comment );
                lines.add("\t*/");
            }
            String name = GenerateUtils.string2JavaNameFirstLower(columnList.get(i), "", "");
            String type = domainClassProperty.getHmColumnDataType().get(columnList.get(i));

            //时间格式需要加时间类型注解
            if ("date".equalsIgnoreCase(type)) {
                lines.add("\t@DateTimeFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")");
                lines.add("\t@JsonFormat(pattern=\"yyyy-MM-dd HH:mm:ss\")");
            }
            String field = "private ";
            field += type;
            field += " " + name + ";\n";
            lines.add("\t" + field);
        }

        lines.add("}");
        // 写文件
        writeFile(file, lines);
    }

    private static void writeFile(File file, List<String> lines) {
        try {
            FileUtils.writeLines(file, "UTF-8", lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}