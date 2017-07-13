package org.utopiavip;

import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaRender implements Resource{

    public static String render2JavaBean(Table table) {
        StringBuilder bean = new StringBuilder();
        // Import
        bean.append("import com.hand.hap.cloud.mybatis.annotation.ModifyAudit;").append(nl);
        bean.append("import com.hand.hap.cloud.mybatis.annotation.VersionAudit;").append(nl);
        bean.append("import lombok.AllArgsConstructor;").append(nl);
        bean.append("import lombok.Data;").append(nl);
        bean.append("import lombok.NoArgsConstructor;").append(nl);
        bean.append("import com.hand.hap.cloud.mybatis.domain.AuditDomain;").append(nl);
        bean.append("import javax.persistence.GeneratedValue;").append(nl);
        bean.append("import javax.persistence.Id;").append(nl).append(nl);

        // Annotations
        bean.append("@ModifyAudit").append(nl)
                .append("@VersionAudit").append(nl)
                .append("@Data").append(nl)
                .append("@NoArgsConstructor").append(nl)
                .append("@AllArgsConstructor").append(nl);

        // Class
        bean.append("public class").append(blank).append(Camel.toCamel(table.getTableName())).append(blank);

        if (table.isPrimaryKeyUUID()) {
            bean.append("extends BaseDomain");
        } else {
            bean.append("extends AuditDomain");
        }
        bean.append("{").append(nl2);

        // Attributes
        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            if (column.isPrimaryKey()) {
                bean.append(blank4).append("@Id").append(nl);
                if (table.isPrimaryKeyUUID()) {
                    bean.append(blank4).append("@GeneratedValue(generator = \"UUID\")").append(nl);
                } else {
                    bean.append(blank4).append("@GeneratedValue").append(nl);
                }
            }
            bean.append(blank4)
                    .append(modifier)
                    .append(blank)
                    .append(TypeMapper.get(column.getDataType().toString()))
                    .append(blank)
                    .append(Camel.toCamel(column.getColumnName()))
                    .append(semicolon)
                    .append(nl);
        }
        bean.append("}").append(nl);
        return bean.toString();
    }

}
