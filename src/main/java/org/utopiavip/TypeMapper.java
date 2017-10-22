package org.utopiavip;

import com.hscf.common.text.StringUtil;

import java.util.HashMap;
import java.util.Map;

public class TypeMapper {

    /**
     * http://www.cnblogs.com/pianai-shu/p/6349183.html
     */
    public static Map<String, String> JAVA_TYPES = new HashMap<>();
    public static Map<String, String> LIQUIBASE_DB_KEY_MAP = new HashMap<>();

    static {
        JAVA_TYPES.put("char", "String");
        JAVA_TYPES.put("varchar", "String");
        JAVA_TYPES.put("text", "String");
        JAVA_TYPES.put("mediumtext", "String");
        JAVA_TYPES.put("longtext", "String");
        JAVA_TYPES.put("longblob", "Blob");
        JAVA_TYPES.put("bigint", "Long");
        JAVA_TYPES.put("int", "Integer");
        JAVA_TYPES.put("decimal", "BigDecimal");
        JAVA_TYPES.put("double", "Double");
        JAVA_TYPES.put("timestamp", "Timestamp");
        JAVA_TYPES.put("datetime", "Date");
        JAVA_TYPES.put("date", "Date");
        JAVA_TYPES.put("bit", "Boolean");
        JAVA_TYPES.put("tinyint", "Boolean");
    }

    static {
        LIQUIBASE_DB_KEY_MAP.put("varchar", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("bigint", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("longtext", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("datetime", "defaultValueDate");
        LIQUIBASE_DB_KEY_MAP.put("int", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("tinyint", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("decimal", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("double", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("longblob", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("text", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("date", "defaultValueDate");
        LIQUIBASE_DB_KEY_MAP.put("mediumtext", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("bit", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("char", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("blob", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("timestamp", "defaultValueDate");
        LIQUIBASE_DB_KEY_MAP.put("smallint", "defaultValueNumeric");
        LIQUIBASE_DB_KEY_MAP.put("time", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("set", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("enum", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("float", "defaultValue");
        LIQUIBASE_DB_KEY_MAP.put("mediumblob", "defaultValue");
    }

    public static String get(String mysqlDataType) {
        return JAVA_TYPES.get(mysqlDataType).toString();
    }

    public static String getLiquibaseValue(String defaultValueKey) {

        defaultValueKey = defaultValueKey.split("\\(")[0];
        String defaultValueDesc = LIQUIBASE_DB_KEY_MAP.get(defaultValueKey).toString();
        if(StringUtil.isEmpty(defaultValueDesc)){
            defaultValueDesc = "defaultValue";
        }
        return defaultValueDesc;
    }
}
