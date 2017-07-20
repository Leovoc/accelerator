package org.utopiavip;

import java.util.HashMap;
import java.util.Map;

public class TypeMapper {

    /**
     * http://www.cnblogs.com/pianai-shu/p/6349183.html
     */
    public static Map<String, String> types = new HashMap<>();

    static {
        types.put("char", "String");
        types.put("varchar", "String");
        types.put("text", "String");
        types.put("mediumtext", "String");
        types.put("longtext", "String");
        types.put("longblob", "Blob");
        types.put("bigint", "Long");
        types.put("int", "Integer");
        types.put("decimal", "BigDecimal");
        types.put("double", "Double");
        types.put("timestamp", "Timestamp");
        types.put("datetime", "Date");
        types.put("bit", "Boolean");
        types.put("tinyint", "Boolean");
    }

    public static String get(String mysqlDataType) {
        return types.get(mysqlDataType).toString();
    }
}
