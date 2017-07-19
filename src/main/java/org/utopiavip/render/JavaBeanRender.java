package org.utopiavip.render;

import org.utopiavip.Camel;
import org.utopiavip.Resource;
import org.utopiavip.TypeMapper;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

import java.util.List;

public class JavaBeanRender implements Resource {

    private JavaBeanRender() {}

    private static final JavaBeanRender render = new JavaBeanRender();

    public static JavaBeanRender getInstance() {
        return render;
    }

    @Override
    public String render(Table table) {
        StringBuilder bean = new StringBuilder();
        // Import
        bean.append("import com.hand.hap.cloud.mybatis.annotation.ModifyAudit;").append(nl);
        bean.append("import com.hand.hap.cloud.mybatis.annotation.VersionAudit;").append(nl);
        bean.append("import lombok.AllArgsConstructor;").append(nl);
        bean.append("import lombok.Data;").append(nl);
        bean.append("import lombok.NoArgsConstructor;").append(nl);
        if (table.isMultiTenant()) {
            if (table.isPrimaryKeyUUID()) {
                bean.append("import com.hand.hap.cloud.mybatis.domain.BaseDiDomain;").append(nl);
            } else {
                bean.append("import com.hand.hap.cloud.mybatis.domain.AuditDiDomain;").append(nl);
            }
        } else {
            if (table.isPrimaryKeyUUID()) {
                bean.append("import com.hand.hap.cloud.mybatis.domain.BaseDomain;").append(nl);
            } else {
                bean.append("import com.hand.hap.cloud.mybatis.domain.AuditDomain;").append(nl);
            }
        }
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

        if (table.isMultiTenant()) {
            if (table.isPrimaryKeyUUID()) {
                bean.append("extends BaseDiDomain");
            } else {
                bean.append("extends AuditDiDomain");
            }
        } else {
            if (table.isPrimaryKeyUUID()) {
                bean.append("extends BaseDomain");
            } else {
                bean.append("extends AuditDomain");
            }
        }

        bean.append("{").append(nl2);

        // Attributes
        List<Column> columns = table.getColumns();
        for (Column column : columns) {
            if (table.isMultiTenant() && "ou_org_id".equals(column.getColumnName())) {
                continue;
            }

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
