package org.utopiavip.render;

import com.hscf.common.time.DateUtil;
import org.utopiavip.Resource;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

public class LiquibaseRender implements Resource{

    private LiquibaseRender() {}

    private static final LiquibaseRender render = new LiquibaseRender();

    public static LiquibaseRender getInstance() {
        return render;
    }

    @Override
    public String render(Table table) {
        StringBuilder sb = new StringBuilder();
        sb.append(blank4).append("changeSet(author:'5641', id:'").append(DateUtil.date2Str(DateUtil.now())).append("') {").append(nl);
        sb.append(blank8).append("createTable(tableName: \"").append(table.getTableName()).append("\") {").append(nl);

        String columnName = null;
        String columnType = null;
        int columnNameMaxLength = table.getColumnNameMaxLength();
        int columnTypeMaxLength = table.getColumnTypeMaxLength();
        for (Column column : table.getColumns()) {
            sb.append(blank12).append("column(name: '");
            columnName = rpad(column.getColumnName() + "',", columnNameMaxLength + 4);

            sb.append(columnName);
            sb.append("type: '");
            columnType = rpad(column.getColumnType() + "',", columnTypeMaxLength + 4);
            sb.append(columnType);

            if (column.isPrimaryKey() && !table.isPrimaryKeyUUID()) {
                sb.append(" autoIncrement: true, ");
            }

            sb.append("remarks: '").append(column.getColumnComment()).append("')");
            if (column.isPrimaryKey()) {
                sb.append(" {").append(nl).append(blank16)
                        .append("constraints(primaryKey: true)").append(nl)
                        .append(blank12).append("}");
            }
            sb.append(nl);
        }
        sb.append(blank8).append("}").append(nl);
        sb.append(blank4).append("}").append(nl);
        return sb.toString();
    }


    /**
     * 右补齐
     * @return
     */
    private static String rpad(String str, int length) {
        return String.format("%1$-" + length + "s", str);
    }
}
