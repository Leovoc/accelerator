package org.utopiavip.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.utopiavip.CommentService;
import org.utopiavip.bean.Comment;
import org.utopiavip.render.JavaBeanRender;
import org.utopiavip.render.LiquibaseRender;
import org.utopiavip.render.MarkdownRender;
import org.utopiavip.bean.Table;
import org.utopiavip.render.PostmanRender;
import org.utopiavip.service.AcceleratorService;

@RestController
@RequestMapping(value = "/accelerator")
public class AcceleratorController {

    @Autowired
    private AcceleratorService acceleratorService;

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/toJava")
    public String toJava(@RequestParam String schema, @RequestParam String tableName) {
        Table table = acceleratorService.getTable(schema, tableName);
        return JavaBeanRender.getInstance().render(table);
    }

    @RequestMapping(value = "/toMarkdown")
    public String toMarkdown(@RequestParam String schema, @RequestParam String tableName) {
        Table table = acceleratorService.getTable(schema, tableName);
        return MarkdownRender.getInstance().render(table);
    }

    @RequestMapping(value = "/toPostman")
    public String toPostman(@RequestParam String schema, @RequestParam String tableName) {
        Table table = acceleratorService.getTable(schema, tableName);
        return PostmanRender.getInstance().render(table);
    }

    @RequestMapping(value = "/toLiquibaseGroovy")
    public String toLiquibaseGroovy(@RequestParam String schema, @RequestParam String tableName) {
        Table table = acceleratorService.getTable(schema, tableName);
        return LiquibaseRender.getInstance().render(table);
    }

    @RequestMapping(value = "/generateComment")
    public String convertComment(Comment comment) {
        return commentService.convert(comment.getSchema(), comment.getTableName(), comment.getComments());
    }
}
