package com.face.generator.mysql;

import com.face.generator.utils.GenerateUtils;
import com.face.generator.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenerateMapper {
	
	    //需要替换的业务代码，防止被覆盖
        private static String replaceCode = null;

		private static String full_table_name = null;

	    public static void generate(String filePath, String packagePath, String tableName, String schema) throws SQLException,IOException {
            replaceCode = null;
			full_table_name = Utils.isEmpty(schema) ? tableName : schema+"."+tableName;
	    	tableName = tableName.trim();
	    	DomainClassProperty domainClassProperty = new DomainClassProperty(tableName);
			buildMapperXml(filePath, packagePath, domainClassProperty);
		}
	    
	    private static void buildMapperXml(String filePath, String packagePath, DomainClassProperty domainClassProperty) throws IOException {
	    	ArrayList<String> columns = domainClassProperty.getAlColumn();
	    	String tableName = domainClassProperty.getTableName();
			String mapperPath = filePath+"/"+packagePath.replace(".", "/")+"/mapper";
	        File mapperXmlFile = new File(mapperPath+"/" + GenerateUtils.firstToUpper(GenerateUtils.processField(tableName)) + "Mapper.xml");

            if(mapperXmlFile.exists()){
                String oldFile= FileUtils.readFileToString(mapperXmlFile, "UTF-8");
                if(oldFile.indexOf("被覆盖-->")!= -1){
                    replaceCode = oldFile.substring(oldFile.indexOf("被覆盖-->")+6, oldFile.length()-9).replaceAll("\r\n\r\n", "\r\n");
                }
            }
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(mapperXmlFile)));
	        bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
	        bw.newLine();
	        bw.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" ");
	        bw.newLine();
	        bw.write("    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
	        bw.newLine();
	        bw.write("<mapper namespace=\"" + packagePath + ".dao." + domainClassProperty.getDaoIfClassName() + "\">");
	        bw.newLine();
	        bw.newLine();
	        // 下面开始写SqlMapper中的方法
	        buildSQL(bw, columns,domainClassProperty, packagePath);
	        bw.write("</mapper>");
	        bw.flush();
	        bw.close();
	    }
	    private static void buildSQL( BufferedWriter bw, List<String> columns, DomainClassProperty domainClassProperty, String packagePath) throws IOException {
	        int size = columns.size();
            String tableName = domainClassProperty.getTableName();
	        ArrayList<String> pkColumns = domainClassProperty.getPkColumn();
	        List<String> autoIncreColumns =  domainClassProperty.getAutoIncreamentColumn();

	        boolean needJsonMap = false;
			for ( int i = 0 ; i < size ; i++ ) {
				boolean isJson = domainClassProperty.getJsonColumn().containsKey(columns.get(i));
				needJsonMap = true;
				break;
			}

			String poPath ="";
			//创建resultMap
			if(needJsonMap){
				//bw.write("\t<resultMap id=\"SolrTestMap\" type=\"com.sunlord.tms.po.SolrTestPo\">");

			}

	      //---------------  insert方法（匹配有值的字段）
	        bw.write("\t<!-- 添加 （匹配有值的字段）-->");
	        bw.newLine();
	        bw.write("\t<insert id=\"insert\" ");
        	if (Utils.notEmpty(autoIncreColumns)) {
        		bw.write(" keyProperty=\""+GenerateUtils.string2JavaNameFirstLower(autoIncreColumns.get(0),"","")+"\" useGeneratedKeys=\"true\" ");
        	}
	        bw.write(" parameterType=\"" + packagePath + ".po." + domainClassProperty.getPoClassName() + "\">");
	        bw.newLine();
	        
	        bw.write("\t\t INSERT INTO " + full_table_name);
	        bw.newLine();
	        bw.write("\t\t <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >");
	        bw.newLine();
	 
	        String tempField = null;
	        for ( int i = 0 ; i < size ; i++ ) {
	            tempField = GenerateUtils.processField(columns.get(i));
	            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
	            bw.newLine();
				bw.write("\t\t\t\t " + columns.get(i) + ",");
	            bw.newLine();
	            bw.write("\t\t\t</if>");
	            bw.newLine();
	        }
	 
	        bw.newLine();
	        bw.write("\t\t </trim>");
	        bw.newLine();
	 
	        bw.write("\t\t <trim prefix=\"VALUES (\" suffix=\")\" suffixOverrides=\",\" >");
	        bw.newLine();
	 
	        tempField = null;
	        for ( int i = 0 ; i < size ; i++ ) {
				boolean isJson = domainClassProperty.getJsonColumn().containsKey(columns.get(i));
	            tempField = GenerateUtils.processField(columns.get(i));
	            bw.write("\t\t\t<if test=\"" + tempField + " !=null\">");
	            bw.newLine();
				if (isJson) {
					bw.write("\t\t\t\t #{" + tempField + ", javaType=Object,jdbcType=OTHER, typeHandler=com.sunlord.common.core.handler.JsonTypeHandler},");
				} else {
					bw.write("\t\t\t\t #{" + tempField + "},");
				}
	            bw.newLine();
	            bw.write("\t\t\t</if>");
	            bw.newLine();
	        }
	 
	        bw.write("\t\t </trim>");
	        bw.newLine();
	        bw.write("\t</insert>");
	        bw.newLine();
	        bw.newLine();
	        //---------------  添加insert完毕
	 
	        
	        // 修改update方法
	        if(null!=pkColumns&&pkColumns.size()>0){
	        	bw.write("\t<!-- 修 改-->");
	 	        bw.newLine();
	 	        bw.write("\t<update id=\"update\" parameterType=\"" + packagePath + ".po." + domainClassProperty.getPoClassName() + "\">");
	 	        bw.newLine();
	 	        bw.write("\t\t UPDATE " + full_table_name);
	 	        bw.newLine();
	 	        bw.write("\t\t <trim prefix=\"set\"  suffixOverrides=\",\" >");
	 	        bw.newLine();

	 	        for ( int i = 0 ; i < size ; i++ ) {

	 	        	if (!Utils.isEmpty(autoIncreColumns)){
						if (autoIncreColumns.get(0).equals(columns.get(i))) {
							//不更新主键
							continue;
						}
					}

	 	            tempField = GenerateUtils.processField(columns.get(i));
	 	            bw.write("\t\t\t<if test=\"" + tempField + " != null\">");
	 	            bw.newLine();

					boolean isJson = domainClassProperty.getJsonColumn().containsKey(columns.get(i));
					if (isJson) {
						bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + ", javaType=Object,jdbcType=OTHER, typeHandler=com.sunlord.common.core.handler.JsonTypeHandler},");
					} else {
						bw.write("\t\t\t\t " + columns.get(i) + " = #{" + tempField + "},");
					}

	 	            bw.newLine();
	 	            bw.write("\t\t\t</if>");
	 	            bw.newLine();
	 	        }
	 	 
	 	        bw.newLine();
	 	        bw.write("\t\t </trim>");
	 	        bw.newLine();
	 	        
	 	        bw.write("\t\t <where> ");
	 	        bw.newLine();
				bw.write("\t\t <trim prefixOverrides=\"and\" >");
				bw.newLine();
				for(int i=0;i<pkColumns.size();i++){
					String temp = pkColumns.get(i);
					 bw.write("\t\t\t\t and " + temp + " = #{" + GenerateUtils.processField(temp) + "} ");
					 bw.newLine();
				}
				bw.write("\t\t </trim>");
				bw.newLine();
	            bw.write("\t\t </where> ");
	 	        bw.newLine();
	 	        bw.write("\t</update>");
	 	        bw.newLine();
	 	        bw.newLine();
	        	
	        }
	        // update方法完毕
	        // 查询
	        bw.write("\t<!-- 查询 -->");
	        bw.newLine();
	        bw.write("\t<select id=\"list\" parameterType=\"" + packagePath + ".po." + domainClassProperty.getPoClassName() + "\" resultType=\"" + packagePath + ".po." + domainClassProperty.getPoClassName() + "\" > ");
	        bw.newLine();
	        bw.write("\t\t SELECT");
	        bw.newLine();
	        bw.write("\t\t");
	 
	        for ( int i = 0 ; i < size ; i++ ) {
	            bw.write(" " + columns.get(i));
	            if ( i != size - 1 ) {
	                bw.write(",");
	            }
	        }
	 
	        bw.newLine(); 
	        bw.write("\t\t FROM " + full_table_name);
	        bw.newLine();
	        bw.write("\t\t<trim prefix=\"where\" prefixOverrides=\"and\" >");
	        bw.newLine();
	        
	        tempField = null;
	        for ( int i = 0 ; i < size ; i++ ) {
	            tempField = GenerateUtils.processField(columns.get(i));
				String type = domainClassProperty.getHmColumnDataType().get(columns.get(i));
				if("String".equals(type)){
					bw.write("\t\t\t<if test=\"" + tempField + " != null and "+tempField+" != ''\">");
				}else{
					bw.write("\t\t\t<if test=\"" + tempField + " != null \">");
				}

	            bw.newLine();
	            bw.write("\t\t\t\t and " + columns.get(i) + " = #{" + tempField + "} ");
	            bw.newLine();
	            bw.write("\t\t\t</if>");
	            bw.newLine();
	        }
	        
	        bw.newLine();
            bw.write("\t\t</trim>");
            bw.newLine();
            bw.write("\t</select>");
	        bw.newLine();
	        //-------------------查询完毕

            //添加注释
            bw.write("\t <!-- 新增的业务请写在下面，防止重新生成后被覆盖-->");

            if(Utils.notEmpty(replaceCode)){
                bw.write(replaceCode);
            }
            bw.newLine();
	    }
	}