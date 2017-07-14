package org.utopiavip.bean;

import lombok.Data;
import org.utopiavip.Camel;

import java.util.List;

/**
 * 数据库表
 */
@Data
public class Table {

    private String tableSchema;
    private String tableName;
    private String tableComment;
    private boolean isPrimaryKeyUUID;

    private List<Column> columns;

    public String getCammelTableName() {
        return Camel.toCamel(this.getTableName());
    }
}
