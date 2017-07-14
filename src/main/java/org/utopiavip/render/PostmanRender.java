package org.utopiavip.render;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.utopiavip.Camel;
import org.utopiavip.Resource;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.PostmanCollection;
import org.utopiavip.bean.PostmanRequest;
import org.utopiavip.bean.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PostmanRender implements Resource{

    private PostmanRender() {}

    private static final PostmanRender render = new PostmanRender();

    public static PostmanRender getInstance() {
        return render;
    }

    @Override
    public String render(Table table) {

        PostmanCollection collection = new PostmanCollection();
        PostmanRequest request = new PostmanRequest();
        List<String> order = new ArrayList<>();
        order.add(UUID.randomUUID().toString());
        collection.setOrder(order);
        collection.setName(table.getCammelTableName());

        request.setCollectionId(collection.getId());
        request.setDataMode("raw"); // raw, urlencoded
        request.setName(table.getCammelTableName());

        List<JSONObject> headerData = new ArrayList<>();
        JSONObject contentType = JSON.parseObject("{'key': 'Content-Type','value': 'application/json','description': 'contenttype','enabled': true}");
        JSONObject authorization = JSON.parseObject("{'key': 'Authorization','value': '111','description': '','enabled': true}");
        headerData.add(contentType);
        headerData.add(authorization);
        request.setHeaderData(headerData);

        // for urlencoded
/*        JSONObject paramData = null;
        List<JSONObject> data = new ArrayList<>();
        for (Column column : table.getColumns()) {
            paramData = new JSONObject();
            paramData.put("key", column.getCamelColumnName());
            paramData.put("value", "");
            paramData.put("description", "");
            paramData.put("type", "text");
            paramData.put("enabled", true);
            data.add(paramData);
        }
        request.setData(data);*/

        // for json
        JSONObject rawModeData = new JSONObject();
        for (Column column : table.getColumns()) {
            rawModeData.put(column.getCamelColumnName(), "");
        }
        request.setRawModeData(rawModeData.toJSONString());
        collection.getRequests().add(request);
        return JSON.toJSONString(collection);
    }

}
