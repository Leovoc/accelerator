package org.utopiavip.mock;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;
import org.utopiavip.runner.MockDataLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MockService {

    @Autowired
    private MockDataLoader mockDataLoader;

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
    public JSONObject mockPageQuery(Table table, int length) {
        if (length <= 0) {
            length = 10;
        }
        String tableName = table.getTableName();
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
                mockColumn(row, tableName, column);
            }
            content.add(row);
        }
        data.put("content", content);
        retObj.put("data", data);

        return retObj;
    }

    public void mockColumn(JSONObject row, String tableName, Column column) {
        String columnName = column.getColumnName();
        String camelColumnName = column.getCamelColumnName();
        String dataType = column.getDataType();

        // 优先Mock预设的业务数据
        boolean isBusinessDataMocked = mockDataLoader.mockSpecifiedTableColumnData(row, tableName, column);
        if (isBusinessDataMocked) {
            return;
        }

        boolean isSingleDataMocked = mockDataLoader.mockColumnData(row, column);
        if (isSingleDataMocked) {
            return;
        }

        // 组织名称
        if (orgNameColumnList.contains(columnName)) {
            row.put(camelColumnName, OrgMocker.getOrg());

        }
        // 金额字段
        else if (amountColumnList.contains(columnName)) {
            if (columnName.contains("_rate")) {
                row.put(camelColumnName, PublicMocker.mockRate());
            } else {
                row.put(camelColumnName, PublicMocker.mockAmount());
            }
        }
        // 时间
        else if (timeColumnsList.contains(dataType)) {
            row.put(camelColumnName, PublicMocker.mockTime());
        }
        // UUID
        else if (columnName.contains("id")) {
            row.put(camelColumnName, PublicMocker.mockUUID());
        }
        // 单据
        else if (columnName.contains("_number") && !"object_version_number".equals(columnName)) {
            row.put(camelColumnName, PublicMocker.mockDocNumber());
        }
        else if ("created_by".equals(columnName) || "last_updated_by".equals(columnName)) {
            row.put(camelColumnName, PublicMocker.mockUUID());
        }
        else if ("version".equals(columnName) || "object_version_number".equals(columnName)) {
            row.put(camelColumnName, 0);
        }
        else if (columnName.contains("_name")) {
            row.put(camelColumnName, PublicMocker.mockUserName());
        }
        // code, status, type
        else if (columnName.contains("code") || columnName.contains("status") || columnName.contains("type")) {
            row.put(camelColumnName, PublicMocker.mockVarchar());
            row.put(camelColumnName + "Desc", PublicMocker.mockVarchar());
        }
        // varchar
        else if ("varchar".equals(dataType)) {
            row.put(camelColumnName, PublicMocker.mockVarchar());
        }
        // int
        else if ("int".equals(dataType)) {
            row.put(camelColumnName, PublicMocker.mockInt());
        }
        else {
            row.put(camelColumnName, null);
        }
    }


}
