package org.utopiavip.bean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.*;

/**
 * Postman请求
 */
@Data
public class PostmanRequest {

    private String id = UUID.randomUUID().toString();
    private String headers = "Content-Type: application/json\nAuthorization: {{access_token}}\n";
    private List headerData = Collections.EMPTY_LIST;
    private String url = "{{host}}/{{server}}/{{version}}/resourceGroups";
    private List queryParams = Collections.EMPTY_LIST;
    private String preRequestScript = "";
    private JSONObject pathVariables = new JSONObject();
    private List pathVariableData = Collections.EMPTY_LIST;
    private String method = "POST";
    private List data = Collections.EMPTY_LIST;
    private String dataMode;
    private String tests = "";
    private String currentHelper = "normal";
    private JSONObject helperAttributes = new JSONObject();
    private long time = new Date().getTime();
    private String name = "Sample";
    private String description = "Description";
    private String collectionId;
    private List responses = Collections.EMPTY_LIST;
    private String rawModeData;
}
