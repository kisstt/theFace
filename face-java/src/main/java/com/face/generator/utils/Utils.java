package com.face.generator.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class Utils {
    /**
     * 判断字符串是否为空
     *
     * @param str
     *            String
     * @return boolean
     */
    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断集合]是否为空
     *
     * @param coll
     *            Collection
     * @return boolean
     */
    public static boolean notEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Collection<?> coll) {
        if (coll == null || coll.size() == 0) {
            return true;
        }

        return false;
    }

    /**
     * 判断int数组是否为空
     *
     * @param arg
     *            int[]
     * @return boolean
     */
    public static boolean notEmpty(int[] arg) {
        return !isEmpty(arg);
    }

    public static boolean isEmpty(int[] intArr) {
        if (intArr == null || intArr.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断long数组是否为空
     *
     * @param arg
     *            long[]
     * @return boolean
     */
    public static boolean notEmpty(long[] arg) {
        return !isEmpty(arg);
    }

    public static boolean isEmpty(long[] longArr) {
        if (longArr == null || longArr.length == 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断Map是否为空
     *
     * @param arg
     *            Map
     * @return boolean
     */
    public static boolean notEmpty(Map<?, ?> arg) {
        return !isEmpty(arg);
    }

    public static boolean isEmpty(Map<?, ?> map) {
        if (map == null || map.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean notEmpty(Object arg) {
        return !isEmpty(arg);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return false;
    }

    /** 至少有一个为空 */
    public static boolean isAnyEmpty(Object... args) {
        for (Object object : args) {
            if (isEmpty(object))
                return true;
        }
        return false;
    }

    /**
     * 全部为空
     * @param args
     * @return
     */
    public static boolean isAllEmpty(Object... args) {
        for (Object object : args) {
            if (notEmpty(object))
                return false;
        }
        return true;
    }

    /**
     * 获取第一个非空数据
     *
     * @param args
     * @return
     */
    public static String getFirstNotNull(String... args) {
        for (String string : args) {
            if (notEmpty(string)) {
                return string;
            }
        }
        return null;
    }

    /**
     * 对象属性转换成数据库字段
     *
     * @param filed
     * @return
     */
    public static String filed2ColName(String filed) {
        if (filed == null || filed.trim().equals(""))
            return filed;
        int stringLength = filed.length();
        StringBuilder sBuilder = new StringBuilder();
        for (int y = 0; y < stringLength; y++) {
            Character c = filed.charAt(y);
            if (Character.isUpperCase(c)) {
                sBuilder.append("_");
                sBuilder.append(c);
            } else {
                sBuilder.append(c);
            }
        }
        return sBuilder.toString();
    }

    /**
     * 判断方法返回值类型
     *
     * @param type
     * @return
     */
    public static boolean isNumber(Type type) {
        return type == Integer.class || type == Long.class;
    }

    /**
     * 判断方法返回值类型
     *
     * @param type
     * @return
     */
    public static boolean isDouble(Type type) {
        return type == Double.class || type == Float.class;
    }

    public static ArrayList<Long> str2LongList(String str, String split){
        String[] strArr = str.split(split);
        ArrayList<Long> idList = new ArrayList<Long>();
        for(String tmp : strArr){
            if(isEmpty(tmp)){
                continue;
            }
            idList.add(Long.valueOf(tmp));
        }

        return idList;
    }

    /**
     * 表字段名 to 类字段名
     *
     * @param colName
     * @return
     */
    public static String colName2fieldName(String colName) {
        StringBuffer fieldName = new StringBuffer();
        for (int i = 0; i < colName.length(); i++) {
            if (colName.charAt(i) == '_') {
                i++;
                fieldName.append(colName.substring(i, i + 1).toUpperCase());
            } else {
                fieldName.append(colName.substring(i, i + 1).toLowerCase());
            }
        }
        return fieldName.toString();
    }

    /**
     *
     * @param fieldName
     * @return
     */
    public static String field2MethodName(String fieldName) {
        StringBuilder methodName = new StringBuilder();
        methodName.append("set");
        methodName.append(fieldName.substring(0, 1).toUpperCase());
        methodName.append(fieldName.substring(1));
        return methodName.toString();
    }


    /**
     *  类字段名 to 表字段名
     *
     * @param fieldName
     * @return
     */
    public static String fieldName2ColName(String fieldName) {
        StringBuffer colName = new StringBuffer();
        for (int i = 0; i < fieldName.length(); i++) {
            Character c = fieldName.charAt(i);
            if (i != 0 && Character.isUpperCase(c)) {
                colName.append("_");
            }
            colName.append(fieldName.substring(i, i + 1).toLowerCase());
        }
        return colName.toString();
    }

    /**
     *
     * @param paramMap
     * @return
     */
    public static String map2Link(Map<String, String> paramMap) {
        Set<String> keySet = paramMap.keySet();
        String link = "";
        String val = "";
        for(String key : keySet){
            val = isEmpty(paramMap.get(key)) ? "" : paramMap.get(key);
            link += key+"="+val+"&";
        }

        if(notEmpty(link)){
            link = link.substring(0, link.length()-1);
        }

        return link;
    }

}
