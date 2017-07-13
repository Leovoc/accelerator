package org.utopiavip.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.utopiavip.JavaRender;
import org.utopiavip.MarkdownRender;
import org.utopiavip.bean.Table;
import org.utopiavip.service.AcceleratorService;

@RestController
@RequestMapping(value = "/tool")
public class ToolController {

    @Autowired
    private AcceleratorService acceleratorService;

    @RequestMapping(value = "/toJava")
    public String toJava(@RequestParam String schema, @RequestParam String tableName) {
        Table table = acceleratorService.getTable(schema, tableName);
        return JavaRender.render2JavaBean(table);
    }

    @RequestMapping(value = "/toMarkdown")
    public String toMarkdown(@RequestParam String schema, @RequestParam String tableName) {
        Table table = acceleratorService.getTable(schema, tableName);
        return MarkdownRender.render2Markdown(table);
    }

}
