package org.utopiavip.render;

import com.hscf.common.time.DateUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.utopiavip.Resource;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Index;
import org.utopiavip.bean.Table;

import java.util.Iterator;
import java.util.List;

public class LiquibaseRender implements Resource {

    private static final String DEFAULT_INDEX_NAME = "PRIMARY";

    private static final String DEFAULT_NON_UNIQUE = "1";
    private static final String DEFAULT_NON_UNIQUE_VALUE = "true";


    private LiquibaseRender() {
    }

    private static final LiquibaseRender render = new LiquibaseRender();

    public static LiquibaseRender getInstance() {
        return render;
    }

    @Override
    public String render(List<Table> tables) {

        StringBuilder sb = new StringBuilder();

        for (Table table : tables) {
            sb.append(render(table));
            sb.append(nl);
        }
        return sb.toString();
    }


    @Override
    public String render(Table table) {

        //睡眠一毫秒 避免ID 重复
        try {
            Thread.sleep(1L);
        }catch (Exception e){
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        sb.append(blank4).append("changeSet(author:'system', id:'").append(DateUtil.now().getTime()).append("') {").append(nl);
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
            if (!column.isNullable() && !column.isPrimaryKey()) {
                sb.append(" {").append(nl).append(blank16)
                        .append("constraints(nullable: false)").append(nl)
                        .append(blank12).append("}");
            }
            sb.append(nl);
        }

        sb.append(blank8).append("}").append(nl);

        Iterator<String> keys = table.getIndexs().keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            if (!DEFAULT_INDEX_NAME.equals(key)) {
                List<Index> indexs = table.getIndexs().get(key);
                Index masterIndex = indexs.get(0);

                sb.append(blank8).append("createIndex(indexName: \"").append(masterIndex.getIndexName())
                        .append("\",tableName: \"").append(masterIndex.getTableName());

                if (!DEFAULT_NON_UNIQUE.equals(masterIndex.getNonUnique())) {
                    sb.append("\",unique: \"").append(DEFAULT_NON_UNIQUE_VALUE);
                }

                sb.append("\") {").append(nl);

                for (Index index : indexs) {

                    sb.append(blank12).append("column(name: '").append(index.getColumnName());

                    sb.append("')");

                    sb.append(nl);

                }
                sb.append(blank8).append("}").append(nl);
            }
        }
        sb.append(blank4).append("}").append(nl);
        return sb.toString();
    }


    /**
     * 右补齐
     *
     * @return
     */
    private static String rpad(String str, int length) {
        return String.format("%1$-" + length + "s", str);
    }
}
