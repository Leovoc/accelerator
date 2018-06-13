package org.utopiavip.render;

import com.hscf.common.text.StringUtil;
import com.hscf.common.time.DateUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.utopiavip.Resource;
import org.utopiavip.TypeMapper;
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

        StringBuilder buf = new StringBuilder();
        buf.append(blank4).append("changeSet(author:'system', id:'").append(DateUtil.now().getTime()).append("') {").append(nl);
        buf.append(blank8).append("createTable(tableName: \"").append(table.getTableName()).append("\") {").append(nl);

        String columnName = null;
        String columnType = null;
        String defaultValue = null;
        int columnNameMaxLength = table.getColumnNameMaxLength();
        int columnTypeMaxLength = table.getColumnTypeMaxLength();
        for (Column column : table.getColumns()) {
            buf.append(blank12).append("column(name: '");
            columnName = rpad(column.getColumnName().toLowerCase() + "',", columnNameMaxLength + 4);
            buf.append(columnName);

            buf.append("type: '");
            columnType = rpad(column.getColumnType() + "',", columnTypeMaxLength + 4);
            buf.append(columnType);

            if(!StringUtil.isEmpty(column.getColumnDefault())){
                String defaultValueKey = TypeMapper.getLiquibaseValue(column.getColumnType().toLowerCase());
                buf.append(defaultValueKey+": '");
                defaultValue = rpad(column.getColumnDefault() + "',", columnTypeMaxLength + 4);
                buf.append(defaultValue);
            }
            if (column.isPrimaryKey() && !table.isPrimaryKeyUUID()) {
                buf.append(" autoIncrement: true, ");
            }

            buf.append("remarks: '").append(column.getColumnComment()).append("')");
            if (column.isPrimaryKey()) {
                buf.append(" {").append(nl).append(blank16)
                        .append("constraints(primaryKey: true)").append(nl)
                        .append(blank12).append("}");
            }
            if (!column.isNullable() && !column.isPrimaryKey()) {
                buf.append(" {").append(nl).append(blank16)
                        .append("constraints(nullable: false)").append(nl)
                        .append(blank12).append("}");
            }
            buf.append(nl);
        }

        buf.append(blank8).append("}").append(nl);

        Iterator<String> keys = table.getIndexs().keySet().iterator();

        while (keys.hasNext()) {
            String key = keys.next();
            if (!DEFAULT_INDEX_NAME.equals(key)) {
                List<Index> indexs = table.getIndexs().get(key);
                Index masterIndex = indexs.get(0);

                buf.append(blank8).append("createIndex(indexName: \"").append(masterIndex.getIndexName().toUpperCase())
                        .append("\",tableName: \"").append(masterIndex.getTableName());

                if (!DEFAULT_NON_UNIQUE.equals(masterIndex.getNonUnique())) {
                    buf.append("\",unique: \"").append(DEFAULT_NON_UNIQUE_VALUE);
                }

                buf.append("\") {").append(nl);

                for (Index index : indexs) {

                    buf.append(blank12).append("column(name: '").append(index.getColumnName().toLowerCase());

                    buf.append("')");

                    buf.append(nl);

                }
                buf.append(blank8).append("}").append(nl);
            }
        }
        buf.append(blank4).append("}").append(nl);
        return buf.toString();
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
