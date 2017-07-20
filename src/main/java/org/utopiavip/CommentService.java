package org.utopiavip;

import com.hscf.common.text.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;
import org.utopiavip.service.AcceleratorService;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * 备注服务
 * 基于Oracle表字段备注生成Mysql备注
 */
@Service
@Transactional
public class CommentService {

    @Autowired
    private AcceleratorService acceleratorService;

    /*
COMMENT ON TABLE hr_organization
    IS 'HR组织';
COMMENT ON COLUMN hr_organization.org_id
    IS '组织ID';
COMMENT ON COLUMN hr_organization.org_name
    IS '名称';
COMMENT ON COLUMN hr_organization.org_number
    IS '组织编号';
COMMENT ON COLUMN hr_organization.org_type
    IS '类型, Ref<HR_ORG_TYPE>';
COMMENT ON COLUMN hr_organization.parent_org_id
    IS '上级组织ID';
COMMENT ON COLUMN hr_organization.manage_position_id
    IS '组织管理岗位ID';
    */

    /**
     * 将Oracle格式的表备注转成Mysql备注脚本
     * @param schame
     * @param tableName
     * @param oracleComments
     * @return
     */
    public String convert(String schame, String tableName, String oracleComments) {

        Table table = acceleratorService.getTable(schame, tableName);
        Map<String, String> columnComments = new HashMap<>();
        boolean isTableComment = true;

        String columnName = null;
        String columnComment = null;
        for (String columnDef : oracleComments.split("\nCOMMENT")) {
            if (isTableComment) {
                isTableComment = false; // 跳过第一条表备注
                continue;
            }

            // 提取字段名
            int columnStartIdx = columnDef.indexOf(".") + 1;
            int columnEndIdx = columnDef.indexOf("\n");
            columnName = columnDef.substring(columnStartIdx, columnEndIdx);

            // 提取备注
            int commentStartPos = columnDef.indexOf("IS ");
            columnComment = columnDef.substring(commentStartPos + 3, columnDef.length() - 1);
            columnComments.put(columnName, columnComment);
        }

        StringBuilder buf = new StringBuilder();
        String defaultValue = null;
        for (Column column : table.getColumns()) {
            // 有备注则添加
            defaultValue = columnComments.get(column.getColumnName());
            if (defaultValue != null) {
                //ALTER TABLE table_name MODIFY segment_name VARCHAR(240) DEFAULT 'value' COMMENT 'aaa';
                buf.append("ALTER TABLE ").append(tableName)
                        .append(" MODIFY ").append(column.getColumnName()).append(" ")
                        .append(column.getColumnType()).append(" ");

                if (!column.isNullable()) {
                    buf.append(" NOT NULL").append(" ");
                } else {
                    if (!"null".equals(column.getColumnDefault()) && !StringUtil.isEmpty(column.getColumnDefault())) {
                        if ("varchar".equals(column.getDataType())) {
                            buf.append("DEFAULT '").append(column.getColumnDefault()).append("' ");
                        } else {
                            buf.append("DEFAULT ").append(column.getColumnDefault()).append(" ");
                        }
                    }
                }

                buf.append(" COMMENT ").append(defaultValue.toString()).append(";\n");
            }
        }

        return buf.toString();
    }
}
