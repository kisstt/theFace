/**
 * 
 */
package com.face.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;

import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * @author Administrator
 *
 */
@Slf4j
public class SqlLogUtils {

	private SqlLogUtils() {
		
	}

	/**
	 * 记录SQL日志
	 * 
	 * @param sql  String
	 * @param costTime  long
	 */
	public static void logSql(String sql, String sqlId, long costTime) {
		if(Utils.notEmpty(sqlId)){
			sql = sqlId + "\n"+sql;
		}
		sql = sql.replaceAll("\n", " ").replaceAll(" +", " ").replaceAll("\t", "");
		StringBuffer buff = new StringBuffer();
//		UserSessionInfo userInfo = UserSessionInfo.getUserSessionInfo();
//		if(Utils.isEmpty(userInfo)){
//			buff.append("******* <").append("SYSTEM").append("> | <").append("SYSTEM").append("> *******\n");
//		}else{
//			buff.append("******* <").append(userInfo.getIPAddress()).append("> | <").append(userInfo.getUserCode()).append("> *******\n");
//		}
		buff.append(sql).append("\n");
		buff.append("[SQL_EXECUTE_TIME(ms)]:").append(costTime).append("\n");
		log.debug(buff.toString());
	}

	/**
	 * 
	 * @param boundSql
	 * @param costTime
	 */
	public static void logSql(BoundSql boundSql, String sqlId,long costTime) {
		String sql = boundSql.getSql();
		List<ParameterMapping> paramList = boundSql.getParameterMappings();
		
		for (int i = 0; i < paramList.size(); i++) {
			String key = paramList.get(i).getProperty();
			Class<?> keyType = paramList.get(i).getJavaType();
			sql = decorateSql(sql,key,keyType, boundSql);
		}
		
		if(sql.contains(Constants.SQL_REPLACE_KEY)){
			sql = sql.replace(Constants.SQL_REPLACE_KEY, "?");
		}
		logSql(sql, sqlId, costTime);
	}
	
	/**
	 * 装饰sql语句
	 * @param sql
	 * @param key
	 * @param keyType
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static String decorateSql(String sql, String key,Class<?> keyType, BoundSql boundSql){
		String value = "null";
		try {
			Object valObj = boundSql.getParameterObject();
			if(boundSql.hasAdditionalParameter(key)){
				valObj = boundSql.getAdditionalParameter(key);
			}
			

			if(valObj instanceof String){
				value = "'"+(String) valObj+"'";
			}else if(valObj instanceof Long || valObj instanceof Integer 
					||valObj instanceof Float ||valObj instanceof Double
					||valObj instanceof  Short){
				value = valObj.toString();
			}else if(valObj instanceof Date){
				value = "'"+DateUtils.date2String((Date) valObj)+"'";
			}else if(valObj instanceof HashMap){
				Object val = ((HashMap) valObj).get(key);
				if(val instanceof String){
					value = "'"+(String) val+"'";
				}else if(val instanceof Long || val instanceof Integer 
						||val instanceof Float ||val instanceof Double
						||val instanceof  Short){
					value = val.toString();
				}else if(val instanceof Date){
					value = "'"+DateUtils.date2String((Date) val)+"'";
				}else{
					value = "'"+String.valueOf(val)+"'";
				}
			}else{
				Object val = ReflectUtils.getValueByFieldName(valObj, key);
				if(null == val){
					value = "null";
				}
				else if(keyType == String.class){
					value = "'"+(String) val+"'";
				}else if(keyType == Long.class || keyType == Integer.class
						||keyType ==  Float.class ||keyType ==  Double.class
						||keyType ==  Short.class){
					value = val.toString();
				}else if(keyType == Date.class){
					value = "'"+DateUtils.date2String((Date) val)+"'";
				}else{
					value = "'"+String.valueOf(val)+"'";
				}
			}
			//防止值中包含关键字?,导致sql打印有问题
			if(value.contains("?")){
				value = value.replace("?", Constants.SQL_REPLACE_KEY);
			}
			//sql = sql.replaceFirst("\\?", value);
			int index = sql.indexOf('?');
			sql = sql.substring(0, index) + value+sql.substring(index+1);
		} catch (Exception e) {
			log.error(sql+"----"+value);
			log.error(e.getMessage(), e);
		}
		
		return sql;
	}
}
