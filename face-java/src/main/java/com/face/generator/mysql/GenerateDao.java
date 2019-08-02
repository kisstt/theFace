package com.face.generator.mysql;


import com.face.generator.utils.DateUtils;
import com.face.generator.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateDao {


    /**
     * 生成IDao接口
     */
    public static void generateIDao(String filePath, String packagePath, String tableName) throws SQLException, IOException {
        DomainClassProperty domainClassProperty = new DomainClassProperty(tableName);
        String daoPath = filePath+"/"+packagePath.replace(".", "/")+"/dao";
        // 类名
        String daoIfclassName = domainClassProperty.getDaoIfClassName();
        String poClassName = domainClassProperty.getPoClassName();
        String poInstanceName = domainClassProperty.getPoInstanceName();
        File file = new File(daoPath+"/" + daoIfclassName + ".java");

        String replaceHead = null;
        String replaceService = null;
        if(file.exists()){
            String oldFile= FileUtils.readFileToString(file, "UTF-8");
            if(oldFile.indexOf("/**头部内容结束")!= -1){
                replaceHead = oldFile.substring(0, oldFile.indexOf("/**头部内容结束")).replaceAll("\r\n\r\n", "\r\n");
                replaceService = oldFile.substring(oldFile.indexOf("被覆盖")+3).replaceAll("\r\n\r\n", "\r\n");
            }
        }

        // 行并接开始
        ArrayList<String> lines = new ArrayList<String>();

        if(Utils.notEmpty(replaceHead)){
            lines.add(replaceHead);
        }else{
            lines.add("package "+packagePath+".dao;");
            lines.add("import org.springframework.dao.DataAccessException;");
            lines.add("import java.util.List;");
            lines.add("import org.apache.ibatis.annotations.Mapper;");
            lines.add("import "+packagePath+".po." + poClassName + ";");
        }

        //注释
        lines.add("/**头部内容结束");
        lines.add(" *");
        lines.add(" * Generator create");
        lines.add(" * @author Generator");
        lines.add(" * @date " + DateUtils.date2String(DateUtils.getNowDate()));
        lines.add(" */");
        lines.add("");
        lines.add(" @Mapper");
        lines.add("public interface " + daoIfclassName + " { ");
        lines.add("");
        // 方法开始
        lines.add("\tint insert(" + poClassName + " " + poInstanceName + ") throws DataAccessException;");
        lines.add("");
        lines.add("\tint update(" + poClassName + " " + poInstanceName + ") throws DataAccessException;");
        lines.add("");
        lines.add("\tList<" + poClassName + ">  list(" + poClassName + " " + poInstanceName + ") throws DataAccessException;");
        lines.add("");
        lines.add("\t//业务代码请写在下面，防止后续生成被覆盖");

        if(Utils.notEmpty(replaceService)){
            lines.add(replaceService);
        }else{
            lines.add("");
            lines.add("}");
        }
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