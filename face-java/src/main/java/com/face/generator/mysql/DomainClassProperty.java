package com.face.generator.mysql;

import com.face.generator.utils.GenerateUtils;
import com.face.generator.utils.Utils;
import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.face.generator.utils.AppContextUtils.getBean;

@Data
public class DomainClassProperty {
    private String tableName;
    private String daoClassName;
    private String daoIfClassName;
    private String poClassName;
    private String poInstanceName;
    private String id;

    private String idFirstUp;
    private String cacheClassName;
    private String objName;
    private String svcClassName;
    private String doClassName;
    private String doInstanceName;

    private boolean hasDateType;

    private ArrayList<String> alColumn = new ArrayList<String>();
    private Map<String, String> commentMap = new HashMap<String, String>();
    private Map<String, String> jsonColumn = new HashMap<String, String>();
    private ArrayList<String> autoIncreamentColumn = new ArrayList<String>();
    private HashMap<String, String> hmColumnDataType = new HashMap<String, String>();
    private ArrayList<String> pkColumn = new ArrayList<String>();

    public DomainClassProperty(String tableName) {
        this.tableName = tableName;
        this.setDaoIfClassName(GenerateUtils.string2JavaNameFirstUp(tableName, "I", "Dao"));
        this.daoClassName = GenerateUtils.string2JavaNameFirstUp(tableName, "", "Dao");
        this.setPoClassName(GenerateUtils.string2JavaNameFirstUp(tableName, "", "Po"));
        this.setPoInstanceName(GenerateUtils.string2JavaNameFirstLower(tableName, "", "Po"));
        this.setSvcClassName(GenerateUtils.string2JavaNameFirstUp(tableName, "", "Svc"));
        this.setDoClassName(GenerateUtils.string2JavaNameFirstUp(tableName, "", "Do"));
        this.setDoInstanceName(GenerateUtils.string2JavaNameFirstLower(tableName, "", "Do"));
        JdbcTemplate jdbcTemplate = (JdbcTemplate) getBean("jdbcTemplate");
        String SQL = GenerateUtils.MYSQL_SQL;
        List<Map<String, Object>> columnList = jdbcTemplate.queryForList(SQL.replace("?", tableName));
        for (Map<String, Object> map : columnList) {
            String column = (String) map.get("Field");
            String dataType = (String) map.get("Type");
            String comment = (String) map.get("Comment");
            String pk = (String) map.get("Key");
            String extra = (String) map.get("Extra");
            if ("json".equals(dataType.toLowerCase())) {
                jsonColumn.put(column, null);
            }
            alColumn.add(column);
            commentMap.put(column, comment);
            String filedType = GenerateUtils.getDataType(dataType);
            if ("date".equalsIgnoreCase(filedType)) {
                hasDateType = true;
            }
            hmColumnDataType.put(column, filedType);
            if (Utils.notEmpty(pk)) {
                pkColumn.add(column);
                if (Utils.notEmpty(extra) && extra.equals("auto_increment")) {
                    autoIncreamentColumn.add(column);
                }
            }
        }

    }


}
