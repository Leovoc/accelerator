package org.utopiavip.mock;

import com.alibaba.fastjson.JSONObject;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mock {

    private static final String[] orgNameColumns = {"request_org_name", "guarantee_org_name"};
    private static List<String> orgNameColumnList = null;

    private static final String[] amountColumns = {"double", "decimal"};
    private static List<String> amountColumnList = null;

    private static final String[] timeColumns = {"datetime", "timestamp", "date"};
    private static List<String> timeColumnsList = null;

    static {
        orgNameColumnList = Arrays.asList(orgNameColumns);
        amountColumnList = Arrays.asList(amountColumns);
        timeColumnsList = Arrays.asList(timeColumns);

    }

    public static JSONObject mock200() {
        JSONObject retObj = new JSONObject();
        retObj.put("code", "200");
        retObj.put("message", "");
        retObj.put("data", null);
        return retObj;
    }

    /**
     * Mock分页数据
     *
     * @param table
     * @param length 数据条数
     * @return
     */
    public static JSONObject mockPageQuery(Table table, int length) {
        if (length <= 0) {
            length = 10;
        }
        JSONObject retObj = new JSONObject();
        retObj.put("code", "200");
        retObj.put("message", "");

        JSONObject data = new JSONObject();
        data.put("totalPages", 17);
        data.put("totalElements", 167);
        data.put("numberOfElements", length);
        data.put("size", 10);
        data.put("number", 1);

        List<JSONObject> content = new ArrayList<>();
        for (int i =0; i < length; i++) {
            JSONObject row = new JSONObject();
            for (Column column : table.getColumns()) {
                mockColumn(row, column);
            }
            content.add(row);
        }
        data.put("content", content);
        retObj.put("data", data);

        return retObj;
    }

    public static void mockColumn(JSONObject row, Column column) {
        String columnName = column.getColumnName();
        String dataType = column.getDataType();

        // 组织名称
        if (orgNameColumnList.contains(columnName)) {
            row.put(columnName, OrgMocker.getOrg());

        }
        // 金额字段
        else if (amountColumnList.contains(columnName)) {
            if (columnName.contains("_rate")) {
                row.put(columnName, PublicMocker.mockRate());
            } else {
                row.put(columnName, PublicMocker.mockAmount());
            }
        }
        // 时间
        else if (timeColumnsList.contains(dataType)) {
            row.put(columnName, PublicMocker.mockTime());
        }
        // UUID
        else if (columnName.contains("id")) {
            row.put(columnName, PublicMocker.mockUUID());
        }
        // 单据
        else if (columnName.contains("_number") && !"object_version_number".equals(columnName)) {
            row.put(columnName, PublicMocker.mockDocNumber());
        }
        else if ("created_by".equals(columnName) || "last_updated_by".equals(columnName)) {
            row.put(columnName, PublicMocker.mockUUID());
        }
        else if ("version".equals(columnName) || "object_version_number".equals(columnName)) {
            row.put(columnName, 0);
        }
        else if (columnName.contains("_name")) {
            row.put(columnName, PublicMocker.mockUserName());
        }
        // code, status, type
        else if (columnName.contains("code") || columnName.contains("status") || columnName.contains("type")) {
            row.put(columnName, PublicMocker.mockVarchar());
            row.put(columnName + "_desc", PublicMocker.mockVarchar());
        }
        // varchar
        else if ("varchar".equals(dataType)) {
            row.put(columnName, PublicMocker.mockVarchar());
        }
        // int
        else if ("int".equals(dataType)) {
            row.put(columnName, PublicMocker.mockInt());
        }
        else {
            row.put(columnName, null);
        }
    }


}
