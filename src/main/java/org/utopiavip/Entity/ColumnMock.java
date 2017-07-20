package org.utopiavip.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class ColumnMock {

    @Id
    @GeneratedValue
    private Long id;
    private String columnName;
    private String data;
}
