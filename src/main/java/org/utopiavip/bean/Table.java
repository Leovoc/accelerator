package org.utopiavip.bean;

import lombok.Data;
import org.utopiavip.Camel;

import java.util.List;
import java.util.Map;

/**
 * 数据库表
 */
@Data
public class Table {

    private String tableSchema;
    private String tableName;
    private String tableComment;
    private boolean isPrimaryKeyUUID;
    private int columnNameMaxLength;
    private int columnTypeMaxLength;
    private boolean isMultiTenant;
    private List<Column> columns;

    private Map<String,List<Index>> indexs;

    public String getCammelTableName() {
        return Camel.toCamel(this.getTableName());
    }
}
