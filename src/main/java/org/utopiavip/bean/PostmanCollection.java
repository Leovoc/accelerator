package org.utopiavip.bean;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

import java.util.*;

@Data
public class PostmanCollection {

    private String id = UUID.randomUUID().toString();
    private String name = "Sample";
    private String description = "Description";
    private List order = Collections.EMPTY_LIST;
    private List folders = Collections.EMPTY_LIST;
    private long timestamp = new Date().getTime();
    private String owner = "1699399";
    private List<PostmanRequest> requests = new ArrayList<>();
}
