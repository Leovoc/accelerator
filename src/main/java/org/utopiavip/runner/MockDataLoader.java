package org.utopiavip.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.utopiavip.Entity.ColumnMock;
import org.utopiavip.Entity.TableColumnMock;
import org.utopiavip.bean.Column;
import org.utopiavip.dao.ColumnMockDao;
import org.utopiavip.dao.TableColumnMockDao;
import org.utopiavip.util.RandomUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MockDataLoader implements CommandLineRunner{

    private static final Logger logger = LoggerFactory.getLogger(MockDataLoader.class);
    private static final Map<String, JSONArray> codeMetadatas = new HashMap<>(); // 编码与描述 key,value模拟
    private static final Map<String, List<String>> columnMetadatas = new HashMap<>(); // 单个字段值模拟

    @Autowired
    private TableColumnMockDao tableColumnMockDao;

    @Autowired
    private ColumnMockDao columnMockDao;

    public void loadMetadata() {
        logger.info("Start loading metadata...");
        List<TableColumnMock> mds = tableColumnMockDao.findAll();
        if (mds != null && !mds.isEmpty()) {
            JSONArray data = null;
            for (TableColumnMock metadata : mds) {
                try {
                    data = JSON.parseArray(metadata.getData());
                    codeMetadatas.put(metadata.getTableName() + "_" + metadata.getColumnName(), data);
                    logger.info("Table {} Column {} metadata loaded", metadata.getTableName(), metadata.getColumnName());
                } catch (Exception e) {
                    logger.error("解析JSON数据出错, raw text [{}]", metadata.getData());
                }
            }
        }

        List<ColumnMock> columnMockList = columnMockDao.findAll();
        if (columnMockList != null && !columnMockList.isEmpty()) {
            List<String> columnValues = null;
            for (ColumnMock columnMock : columnMockList) {
                try {
                    columnValues = Arrays.asList(columnMock.getData().split(","));
                    columnMetadatas.put(columnMock.getColumnName(), columnValues);
                    logger.info("Column {} metatada loaded", columnMock.getColumnName());
                } catch (Exception e) {
                    logger.error("解析单个字段数据出错, raw text [{}]", columnMock.getData());
                }

            }
        }

        logger.info("Metadata loaded...");
    }

    @Override
    public void run(String... args) throws Exception {
        loadMetadata();
    }

    /**
     * 模拟特定表的特定字段数据
     * @param row
     * @param column
     * @return
     */
    public boolean mockSpecifiedTableColumnData(JSONObject row, String tableName, Column column) {
        JSONArray data = codeMetadatas.get(tableName + "_" + column.getColumnName());
        if (data == null) {
            return false;
        }

        JSONObject record = (JSONObject) data.get(RandomUtil.getRandom(data.size()));
        row.put(column.getCamelColumnName(), record.getString("code"));
        row.put(column.getCamelColumnName() + "Desc", record.getString("desc"));
        return true;
    }

    /**
     * 模拟特定字段数据
     * @param row
     * @param column
     * @return
     */
    public boolean mockColumnData(JSONObject row, Column column) {
        List<String> data = columnMetadatas.get(column.getColumnName());
        if (data == null || data.isEmpty()) {
            return false;
        }

        row.put(column.getCamelColumnName(), data.get(RandomUtil.getRandom(data.size())));
        return true;
    }

}
