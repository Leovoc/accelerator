package org.utopiavip.bean;

import lombok.Data;
import org.utopiavip.Camel;

/**
 * 数据库表字段
 */
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

    public String getCamelColumnName() {
        return Camel.toCamel(this.getColumnName());
    }
}
