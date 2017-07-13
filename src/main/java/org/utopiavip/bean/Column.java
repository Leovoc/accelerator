package org.utopiavip.bean;

import lombok.Data;

@Data
public class Column {
    private String columnName;
    private boolean isNullable;
    private String columnType;
    private Integer columnSeq;
    private String columnComment;
    private String dataType;
    private String columnKey;
    private boolean isPrimaryKey;
}
