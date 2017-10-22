package org.utopiavip;

public class SqlResource {

    public static final int C_COLUMN_NAME = 1;
    public static final int C_IS_NULLABLE = 2;
    public static final int C_COLUMN_TYPE = 3;
    public static final int C_DATA_TYPE = 4;
    public static final int C_COLUMN_COMMENT = 5;
    public static final int C_COLUMN_SEQ = 6;
    public static final int C_COLUMN_KEY = 7;
    public static final int C_COLUMN_DEFAULT = 8;
    public static final int C_TABLE_SCHEMA = 2;
    public static final int C_TABLE_NAME=3;
    public static final int C_INDEX_NON_UNIQUE=4;
    public static final int C_INDEX_SCHEMA=5;
    public static final int C_INDEX_NAME=6;
    public static final int C_INDEX_TYPE=7;

    public static final int T_TABLE_SCHEMA = 1;
    public static final int T_TABLE_NAME=2;


    public static String queryColumn(String schema, String table) {
        return
                " select " +
                " 	column_name, " +
                " 	is_nullable, " +
                " 	column_type, " +
                " 	data_type, " +
                " 	column_comment, " +
                "   ordinal_position, " +
                "   column_key, " +
                "   column_default " +
                " from " +
                " 	information_schema.columns " +
                " where " +
                " 	table_schema = '" + schema +"' " +
                " and table_name = '" + table + "' " +
                " order by ordinal_position asc ";
    }


    public static String queryIndex(String schema, String table) {

        return "select " +
                "   column_name ," +
                "   table_schema," +
                "   table_name," +
                "   non_unique," +
                "   index_schema," +
                "   index_name," +
                "   index_type   " +
                "from " +
                "   information_schema.statistics " +
                "where " +
                "   table_schema = '"+schema+"' " +
                "and table_name = '" + table+"'" ;

    }


    public static String queryTable(String schema) {

        return "select" +
                "   table_schema, " +
                "   table_name " +
                "from" +
                "   information_schema.tables " +
                "where " +
                "   table_schema = '"+schema+"'" +
                "and table_type = 'BASE TABLE' " ;

    }
}
