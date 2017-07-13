package org.utopiavip;

import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaRender {

    private static String modifier = "private";
    private static String blank4 = "    ";
    private static String blank = " ";
    private static char underline = "_".toCharArray()[0];
    private static String newline = "\n";
    private static String newline2 = "\n\n";
    private static String semicolon = ";";

    /**
     * http://www.cnblogs.com/pianai-shu/p/6349183.html
     */
    private static Map<String, String> types = new HashMap<>();

    static {
        types.put("char", "String");
        types.put("varchar", "String");
        types.put("text", "String");
        types.put("mediumtext", "String");
        types.put("longtext", "String");
        types.put("longblob", "Blob");
        types.put("bigint", "Long");
        types.put("int", "Integer");
        types.put("decimal", "BigDecimal");
        types.put("decimal", "BigDecimal");
        types.put("double", "Double");
        types.put("timestamp", "Timestamp");
        types.put("datetime", "Date");
        types.put("bit", "Boolean");
        types.put("tinyint", "Boolean");
    }

    public static String render2JavaBean(Table table) {
        StringBuilder bean = new StringBuilder();
        // Import
        bean.append("import com.hand.hap.cloud.mybatis.annotation.ModifyAudit;").append(newline);
        bean.append("import com.hand.hap.cloud.mybatis.annotation.VersionAudit;").append(newline);
        bean.append("import lombok.AllArgsConstructor;").append(newline);
        bean.append("import lombok.Data;").append(newline);
        bean.append("import lombok.NoArgsConstructor;").append(newline);
        bean.append("import com.hand.hap.cloud.mybatis.domain.AuditDomain;").append(newline);
        bean.append("import javax.persistence.GeneratedValue;").append(newline);
        bean.append("import javax.persistence.Id;").append(newline).append(newline);

        // Annotations
        bean.append("@ModifyAudit").append(newline)
                .append("@VersionAudit").append(newline)
                .append("@Data").append(newline)
                .append("@NoArgsConstructor").append(newline)
                .append("@AllArgsConstructor").append(newline);

        // Class
        bean.append("public class").append(blank).append(Camel.toCamel(table.getTableName())).append(blank);

        if (table.isPrimaryKeyUUID()) {
            bean.append("extends BaseDomain");
        } else {
            bean.append("extends AuditDomain");
        }
        bean.append("{").append(newline2);

        // Attributes
        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                bean.append(blank4).append("@Id").append(newline);
                if (table.isPrimaryKeyUUID()) {
                    bean.append(blank4).append("@GeneratedValue(generator = \"UUID\")").append(newline);
                } else {
                    bean.append(blank4).append("@GeneratedValue").append(newline);
                }
            }
            bean.append(blank4)
                    .append(modifier)
                    .append(blank)
                    .append(types.get(column.getDataType().toString()))
                    .append(blank)
                    .append(Camel.toCamel(column.getColumnName()))
                    .append(semicolon)
                    .append(newline);
        }
        bean.append("}").append(newline);
        return bean.toString();
    }

}
