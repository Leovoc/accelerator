package org.utopiavip.bean;

import lombok.Data;

import java.util.List;

@Data
public class Table {

    private String tableSchema;
    private String tableName;
    private String tableComment;
    private boolean isPrimaryKeyUUID;

    private List<Column> columns;
}
