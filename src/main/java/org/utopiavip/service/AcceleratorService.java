package org.utopiavip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utopiavip.SqlResource;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AcceleratorService {

    @Autowired
    private ConnectionService connectionService;

    @Autowired
    private QueryService queryService;

    public Table getTable(String schema, String tableName) {
        Connection connection = connectionService.getConnection(schema);
        ResultSet resultSet = queryService.executeQuery(connection, SqlResource.queryColumn(schema, tableName));

        Table table = new Table();
        table.setTableSchema(schema);
        table.setTableName(tableName);
        List<Column> columnList = new ArrayList<>();
        int columnNameMaxLength = 0;
        int columnTypeMaxLength = 0;
        int columnLength = 0;
        int columnTypeLength = 0;

        try {
            Column column = null;
            while (resultSet.next()) {
                column = new Column();
                column.setColumnName(resultSet.getString(SqlResource.C_COLUMN_NAME));
                column.setColumnType(resultSet.getString(SqlResource.C_COLUMN_TYPE));
                column.setColumnComment(resultSet.getString(SqlResource.C_COLUMN_COMMENT));
                column.setColumnSeq(resultSet.getInt(SqlResource.C_COLUMN_SEQ));
                column.setNullable(!"NO".equals(resultSet.getString(SqlResource.C_IS_NULLABLE)));
                column.setDataType(resultSet.getString(SqlResource.C_DATA_TYPE));
                column.setColumnKey(resultSet.getString(SqlResource.C_COLUMN_KEY));
                column.setColumnDefault(resultSet.getString(SqlResource.C_COLUMN_DEFAULT));
                if ("PRI".equals(column.getColumnKey())) { // primary key
                    column.setPrimaryKey(true);
                    table.setPrimaryKeyUUID("varchar".equals(column.getDataType()));
                }

                columnLength = column.getCamelColumnName().length();
                columnTypeLength = column.getColumnType().length();
                if (columnLength > columnNameMaxLength) {
                    columnNameMaxLength = columnLength;
                }
                if (columnTypeLength > columnTypeMaxLength) {
                    columnTypeMaxLength = columnTypeLength;
                }
                columnList.add(column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setColumns(columnList);
        table.setColumnNameMaxLength(columnNameMaxLength);
        table.setColumnTypeMaxLength(columnTypeMaxLength);
        return table;
    }
}
