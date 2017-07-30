package org.utopiavip.mock;

import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;
import org.utopiavip.runner.MockDataLoader;
import org.utopiavip.util.CamelUtil;
import org.utopiavip.util.RandomUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class MockService {

    @Autowired
    private MockDataLoader mockDataLoader;

    private static PegDownProcessor mdParser = new PegDownProcessor(Extensions.ALL_WITH_OPTIONALS);

    private static final String[] orgNameColumns = {"request_org_name", "guarantee_org_name"};
    private static List<String> orgNameColumnList = null;

    private static final String[] amountColumns = {"double", "decimal"};
    private static List<String> amountColumnList = null;

    private static final String[] javaAmountColumns = {"double", "bigdecimal", "float"};
    private static List<String> javaAmountColumnList = null;

    private static final String[] timeColumns = {"datetime", "timestamp", "date"};
    private static List<String> timeColumnsList = null;

    static {
        orgNameColumnList = Arrays.asList(orgNameColumns);
        amountColumnList = Arrays.asList(amountColumns);
        javaAmountColumnList = Arrays.asList(javaAmountColumns);
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
     * 根据表Mock分页数据
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
        for (int i = 0; i < length; i++) {
            JSONObject row = new JSONObject();
            for (Column column : table.getColumns()) {
                mockDbColumn(row, tableName, column);
            }
            content.add(row);
        }
        data.put("content", content);
        retObj.put("data", data);

        return retObj;
    }

    /**
     * Mock DB字段
     *
     * @param row
     * @param tableName
     * @param column
     */
    public void mockDbColumn(JSONObject row, String tableName, Column column) {
        String columnName = column.getColumnName();
        String camelColumnName = column.getCamelColumnName();
        String dataType = column.getDataType();

        // 优先Mock预设的业务数据
        boolean isBusinessDataMocked = mockDataLoader.mockSpecifiedTableColumnData(row, tableName, column);
        if (isBusinessDataMocked) {
            return;
        }

        boolean isSingleDataMocked = mockDataLoader.mockColumnData(row, column.getCamelColumnName());
        if (isSingleDataMocked) {
            return;
        }

        // 组织名称
        if (orgNameColumnList.contains(columnName)) {
            row.put(camelColumnName, OrgMocker.getOrg());

        }
        // 金额字段
        else if (amountColumnList.contains(dataType)) {
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
        } else if ("created_by".equals(columnName) || "last_updated_by".equals(columnName)) {
            row.put(camelColumnName, PublicMocker.mockUUID());
        } else if ("version".equals(columnName) || "object_version_number".equals(columnName)) {
            row.put(camelColumnName, 0);
        } else if (columnName.contains("_name")) {
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
        } else {
            row.put(camelColumnName, "");
        }
    }

    /**
     * Mock Java字段
     *
     * @param row
     * @param columnName
     * @param columnType
     */
    public void mockJavaColumn(JSONObject row, String columnName, String columnType) {

        String camelColumnName = columnName;
        String underlineColumnName = CamelUtil.camel2Underline(columnName);
        String lowerColumnName = camelColumnName.toLowerCase();

        boolean isSingleDataMocked = mockDataLoader.mockColumnData(row, camelColumnName);
        if (isSingleDataMocked) {
            return;
        }

        // 组织名称
        if (orgNameColumnList.contains(underlineColumnName)) {
            row.put(camelColumnName, OrgMocker.getOrg());
        }
        // 金额字段
        else if (javaAmountColumnList.contains(columnType)) {
            if (camelColumnName.contains("Rate")) {
                row.put(camelColumnName, PublicMocker.mockRate());
            } else {
                row.put(camelColumnName, PublicMocker.mockAmount());
            }
        }
        // 时间
        else if (underlineColumnName.contains("date") || underlineColumnName.contains("time")) {
            row.put(camelColumnName, PublicMocker.mockTime());
        }
        else if (camelColumnName.equals("isEnabled")) {
            row.put(camelColumnName, (RandomUtil.getRandom(2) == 0) ? true : false);
        }
        // UUID
        else if (camelColumnName.contains("Id")) {
            row.put(camelColumnName, PublicMocker.mockUUID());
        }
        // 单据
        else if (camelColumnName.contains("Number") && !"object_version_number".equals(underlineColumnName)) {
            row.put(camelColumnName, PublicMocker.mockDocNumber());
        } else if ("created_by".equals(underlineColumnName) || "last_updated_by".equals(underlineColumnName)) {
            row.put(camelColumnName, PublicMocker.mockUUID());
        } else if ("version".equals(underlineColumnName) || "object_version_number".equals(underlineColumnName)) {
            row.put(camelColumnName, 0);
        } else if (camelColumnName.contains("Name")) {
            row.put(camelColumnName, PublicMocker.mockUserName());
        }
        // code, status, type
        else if (underlineColumnName.contains("code") || underlineColumnName.contains("status") || underlineColumnName.contains("type")) {
            row.put(camelColumnName, PublicMocker.mockVarchar());
            row.put(camelColumnName + "Desc", PublicMocker.mockVarchar());
        }
        // varchar
        else if ("String".equals(columnType)) {
            row.put(camelColumnName, PublicMocker.mockVarchar());
        }
        // int
        else if ("int".equals(columnType) || "Integer".equals(columnType)) {
            row.put(camelColumnName, PublicMocker.mockInt());
        } else {
            row.put(camelColumnName, "");
        }
    }

    /**
     * 根据Markdown文档Mock数据
     *
     * @param markdownContent markdown文本
     * @param length mock数据量
     * @return
     */
    public JSONObject mockPageDataByMarkdown(String markdownContent, int length) {
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

        String html = mdParser.markdownToHtml(markdownContent);
        Document doc = Jsoup.parse(html);

        Elements trs = doc.select("table").select("tr"); // 获取所有table tr
        Elements tds = null;
        int rows = trs.size();
        String columnName = null;
        String columnType = null;
        JSONObject row = null;
        for (int j = 0; j < length; j++) {
            row = new JSONObject();
            for (int i = 1; i < rows; i++) { // 忽略标题行
                tds = trs.get(i).select("td");
                columnName = tds.get(0).text();
                columnType = tds.get(2).text();
                mockJavaColumn(row, columnName, columnType);
            }
            content.add(row);
        }

        data.put("content", content);
        retObj.put("data", data);

        return retObj;
    }


}
