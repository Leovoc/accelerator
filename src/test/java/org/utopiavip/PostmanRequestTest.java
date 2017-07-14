package org.utopiavip;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.utopiavip.bean.PostmanRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Yongjun.Chen on 17/7/14.
 */
public class PostmanRequestTest {

    @Test
    public void test() {
        PostmanRequest request = new PostmanRequest();
        System.out.println(JSON.toJSONString(new PostmanRequest()));
        List<String> demo = new ArrayList<>();
        demo.add(UUID.randomUUID().toString());
        demo.add(UUID.randomUUID().toString());
       // System.out.println(JSON.toJSONString(demo));
    }
}
