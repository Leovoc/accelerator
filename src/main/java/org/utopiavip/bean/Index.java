package org.utopiavip.bean;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 数据库索引
 */
@Data
public class Index {

    private String columnName ;
    private String tableSchema;
    private String tableName;
    private String nonUnique;
    private String indexSchema;
    private String indexName;
    private String indexType;

}
