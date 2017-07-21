package org.utopiavip.service;

import org.springframework.stereotype.Service;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 根据表生成select 语句
 */
@Service
@Transactional
public class SelectService {

    public String generateSelectSql(Table table) {
        StringBuilder sql = new StringBuilder();
        sql.append("select \n");
        List<Column> columns = table.getColumns();
        int length = columns.size();
        int idx = 1;
        for (Column column : columns) {
            if (idx == length) {
                sql.append(column.getColumnName()).append("\n");
            } else {
                sql.append(column.getColumnName()).append(",\n");
            }
            idx++;
    }
        sql.append("from ").append(table.getTableName());
        return sql.toString();
    }

}
