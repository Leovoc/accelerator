package org.utopiavip.service;

import org.springframework.stereotype.Service;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

import javax.transaction.Transactional;

/**
 * 根据表生成select 语句
 */
@Service
@Transactional
public class SelectService {

    public String generateSelectSql(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        for (Column column : table.getColumns()) {
            sql.append(column.getColumnName()).append(" \n");
        }
        sql.append(" from ").append(table.getTableName());
        return sql.toString();
    }

}
