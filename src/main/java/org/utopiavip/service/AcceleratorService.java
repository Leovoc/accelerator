package org.utopiavip.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utopiavip.SqlResource;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Index;
import org.utopiavip.bean.Table;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AcceleratorService {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private QueryService queryService;

    private static String DATABASE_CHANGE_LOG ="databasechangelog";

    private static String DATABASE_CHANGE_LOG_LOCK ="databasechangeloglock";


    public List<Table> getTable(String schema){

        List<Table> tables = new ArrayList<>();
        Connection connection = connectionService.getConnection(schema);

        ResultSet resultTableSet = queryService.executeQuery(connection, SqlResource.queryTable(schema));

        try {
            while (resultTableSet.next()) {

                String schemaTemp = resultTableSet.getString(SqlResource.T_TABLE_SCHEMA);
                String tableNameTemp = resultTableSet.getString(SqlResource.T_TABLE_NAME);
                if(!DATABASE_CHANGE_LOG.equals(tableNameTemp)
                        &&!DATABASE_CHANGE_LOG_LOCK.equals(tableNameTemp)){
                    Table table = getTable(schemaTemp,tableNameTemp);
                    tables.add(table);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tables;
    }

    public Table getTable(String schema, String tableName) {
        Connection connection = connectionService.getConnection(schema);
        ResultSet resultColumnSet = queryService.executeQuery(connection, SqlResource.queryColumn(schema, tableName));

        ResultSet resultIndexSet = queryService.executeQuery(connection, SqlResource.queryIndex(schema, tableName));

        Table table = new Table();
        table.setTableSchema(schema);
        table.setTableName(tableName);
        List<Column> columnList = new ArrayList<>();
        Map<String,List<Index>> indexList = new HashMap<>();

        int columnNameMaxLength = 0;
        int columnTypeMaxLength = 0;

        int columnLength = 0;
        int columnTypeLength = 0;

        try {
            Column column = null;
            while (resultColumnSet.next()) {
                column = new Column();
                column.setColumnName(resultColumnSet.getString(SqlResource.C_COLUMN_NAME));
                column.setColumnType(resultColumnSet.getString(SqlResource.C_COLUMN_TYPE));
                column.setColumnComment(resultColumnSet.getString(SqlResource.C_COLUMN_COMMENT));
                column.setColumnSeq(resultColumnSet.getInt(SqlResource.C_COLUMN_SEQ));
                column.setNullable(!"NO".equals(resultColumnSet.getString(SqlResource.C_IS_NULLABLE)));
                column.setDataType(resultColumnSet.getString(SqlResource.C_DATA_TYPE));
                column.setColumnKey(resultColumnSet.getString(SqlResource.C_COLUMN_KEY));
                column.setColumnDefault(resultColumnSet.getString(SqlResource.C_COLUMN_DEFAULT) != null
                        ? resultColumnSet.getString(SqlResource.C_COLUMN_DEFAULT).replace("b", "").replace("'", "").toString()
                        : resultColumnSet.getString(SqlResource.C_COLUMN_DEFAULT));
                if ("PRI".equals(column.getColumnKey())) { // primary key
                    column.setPrimaryKey(true);
                    table.setPrimaryKeyUUID("varchar".equals(column.getDataType()));
                }
                if ("ou_org_id".equals(column.getColumnName())) {
                    table.setMultiTenant(true);
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
                System.out.println("column ： ====  "+column);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Index index = null;
            List<Index> indexsTemp = null;
            while (resultIndexSet.next()) {
                index = new Index();

                index.setColumnName(resultIndexSet.getString(SqlResource.C_COLUMN_NAME));
                index.setTableSchema(resultIndexSet.getString(SqlResource.C_TABLE_SCHEMA));

                index.setTableName(resultIndexSet.getString(SqlResource.C_TABLE_NAME));
                index.setNonUnique(resultIndexSet.getString(SqlResource.C_INDEX_NON_UNIQUE));
                index.setIndexSchema(resultIndexSet.getString(SqlResource.C_INDEX_SCHEMA));
                index.setIndexName(resultIndexSet.getString(SqlResource.C_INDEX_NAME));
                index.setIndexType(resultIndexSet.getString(SqlResource.C_INDEX_TYPE));
                if(indexList.get(index.getIndexName())!=null){
                    indexsTemp = indexList.get(index.getIndexName());
                }else{
                    indexsTemp = new ArrayList<>();
                }

                indexsTemp.add(index);
                indexList.put(index.getIndexName(),indexsTemp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setColumns(columnList);
        table.setIndexs(indexList);
        table.setColumnNameMaxLength(columnNameMaxLength);
        table.setColumnTypeMaxLength(columnTypeMaxLength);
        return table;
    }
}
