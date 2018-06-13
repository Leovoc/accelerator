package org.utopiavip.controller;

import com.hscf.common.http.EasyHttpBuilder;
import com.hscf.common.http.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class HelloController {


    @RequestMapping(value = "/test")
    public void test(HttpServletResponse response) throws IOException, InterruptedException {

        Thread.sleep(10000);

        //Fund


        // datasource => remote-call(data) => write response

        Response response1 = EasyHttpBuilder.build().post("http://blog.csdn.net/zhaoyanjun6/article/details/55051917");

        response.setHeader("Content-Disposition", "attachment; filename=dict.txt");
        response.setContentType("application/octet-stream; charset=utf-8");

        PrintWriter writer = response.getWriter();
        writer.print(response1.text());
        writer.flush();
        writer.close();

    }
}
