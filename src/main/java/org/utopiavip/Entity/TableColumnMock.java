package org.utopiavip.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class TableColumnMock {

    @Id
    @GeneratedValue
    private Long id;
    private String tableName;
    private String columnName;
    private String data;

}
