package com.face.generator.utils;

import java.util.HashMap;

public class GenerateUtils {
    public static String MYSQL_SQL = "SHOW FULL COLUMNS FROM `?`";
    public static HashMap<String, String> DATA_TYPE_HM = new HashMap<String, String>();

    static {
        GenerateUtils.DATA_TYPE_HM.put("varchar", "String");
        GenerateUtils.DATA_TYPE_HM.put("int", "Integer");
        GenerateUtils.DATA_TYPE_HM.put("datetime", "Date");
        GenerateUtils.DATA_TYPE_HM.put("text", "String");
    }

    /**
     * 表字段名 to 字段名
     *
     * @param columnName
     * @return
     */
    public static String columnName2fieldName(String columnName) {
        StringBuffer fieldName = new StringBuffer();
        for (int i = 0; i < columnName.length(); i++) {
            if (columnName.charAt(i) == '_' || columnName.charAt(i) == '-') {
                i++;
                fieldName.append(columnName.substring(i, i + 1).toUpperCase());
            } else {
                fieldName.append(columnName.substring(i, i + 1).toLowerCase());
            }
        }
        return fieldName.toString();
    }

    /**
     * 数据 to java名（首字母大写）
     *
     * @param tableName
     * @param prefix
     * @param postfix
     * @return
     */
    public static String string2JavaNameFirstUp(String tableName, String prefix, String postfix) {
        String javaName = columnName2fieldName(tableName);
        return prefix + javaName.substring(0, 1).toUpperCase() + javaName.substring(1) + postfix;
    }

    public static String string2JavaNameFirstUp(String tableName) {
        String javaName = columnName2fieldName(tableName);
        return javaName.substring(0, 1).toUpperCase() + javaName.substring(1);
    }

    /**
     * 数据 to java名（首字母小写）
     *
     * @param tableName
     * @param prefix
     * @param postfix
     * @return
     */
    public static String string2JavaNameFirstLower(String tableName, String prefix, String postfix) {
        String javaName = columnName2fieldName(tableName);
        return prefix + javaName + postfix;
    }

    public static String string2JavaNameFirstLower(String tableName) {
        String javaName = columnName2fieldName(tableName);
        return javaName;
    }

    public static String getDataType(String columnName) {
        for (String key : DATA_TYPE_HM.keySet()) {
            if (columnName.contains(key)) {
                return DATA_TYPE_HM.get(key);
            }
        }
        return "String"; // 缺省使用String
    }

    public static String processField(String field) {
        StringBuffer sb = new StringBuffer(field.length());
        //field = field.toLowerCase();
        String[] fields = field.toLowerCase().split("_");
        String temp = null;
        sb.append(fields[0]);
        for (int i = 1; i < fields.length; i++) {
            temp = fields[i].trim();
            sb.append(temp.substring(0, 1).toUpperCase()).append(temp.substring(1));
        }
        return sb.toString();
    }

    public static String firstToUpper(String field) {
        String temp = field.substring(0, 1).toUpperCase() + field.substring(1);
        return temp;
    }
}
