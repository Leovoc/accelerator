package org.utopiavip;

public class SqlResource {

    public static final int C_COLUMN_NAME = 1;
    public static final int C_IS_NULLABLE = 2;
    public static final int C_COLUMN_TYPE = 3;
    public static final int C_DATA_TYPE = 4;
    public static final int C_COLUMN_COMMENT = 5;
    public static final int C_COLUMN_SEQ = 6;
    public static final int C_COLUMN_KEY = 7;

    public static String queryColumn(String schema, String table) {
        return
                " select " +
                " 	column_name, " +
                " 	is_nullable, " +
                " 	column_type, " +
                " 	data_type, " +
                " 	column_comment, " +
                "   ordinal_position, " +
                "   column_key " +
                " from " +
                " 	information_schema.columns " +
                " where " +
                " 	table_schema = '" + schema +"' " +
                " and table_name = '" + table + "' " +
                " order by ordinal_position asc ";
    }

}
