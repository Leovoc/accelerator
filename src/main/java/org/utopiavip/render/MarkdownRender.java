package org.utopiavip.render;

import org.utopiavip.Camel;
import org.utopiavip.Resource;
import org.utopiavip.TypeMapper;
import org.utopiavip.bean.Column;
import org.utopiavip.bean.Table;

public class MarkdownRender implements Resource {


    public static String render2Markdown(Table table) {
        StringBuilder md = new StringBuilder("[toc]").append(nl).append("-").append(nl);

        // 更新信息
        md.append(h1).append("更新信息").append(nl);
        md.append(v).append("更新时间").append(v).append("更新人员").append(v).append("备注").append(v).append(nl);
        md.append(th3).append(nl);
        md.append(v).append("20170702").append(v).append("张三").append(v2).append(nl);

        // URI信息
        md.append(h1).append("URI信息").append(nl);
        appendCode(md, "POST /v1/banks");

        //Http Header
        md.append(h1).append("Http Header").append(nl);
        md.append(v).append("key").append(v).append("Value").append(v).append("备注").append(v).append(nl);
        md.append(th3).append(nl);

        // Params
        md.append(h1).append("Params").append(nl);
        appendParamTh(md);

        for (Column column : table.getColumns()) {
            md.append(v).append(Camel.toCamel(column.getColumnName())).append(v)
                    .append(column.isNullable() ? "N" : "Y").append(v)
                    .append(TypeMapper.get(column.getDataType())).append(v)
                    .append(column.getColumnComment()).append(v)
                    .append(v).append(nl);
        }

        // Result
        md.append(h1).append("Result").append(nl);
        md.append(h2).append("处理成功").append(nl);
        appendParamTh(md);
        md.append(v).append("code").append(v).append("Y").append(v).append("String").append(v).append(v)
                .append("固定值200").append(v).append(nl);
        md.append(v).append("message").append(v).append("Y").append(v).append("String").append(v).append(v).append(v).append(nl);
        md.append(v).append("data").append(v).append("Y").append(v).append("JSON对象").append(v).append(v).append(v).append(nl);

        // 状态码
        md.append(h1).append("返回码").append(nl);
        md.append(v).append("状态码").append(v).append("描述").append(v).append("备注").append(v).append(nl);
        md.append(th3).append(nl);
        md.append(v).append("200").append(v).append("处理成功").append(v).append(v).append(nl);
        md.append(v).append("500").append(v).append("系统内部错误").append(v).append(v).append(nl);

        // 请求示例
        md.append(h1).append("请求示例").append(nl);
        appendCode(md, "开发人员补充");
        md.append(h1).append("响应示例").append(nl);
        appendCode(md, "开发人员补充");

        return md.toString();
    }

    /**
     * 添加代码块
     *
     * @param sb
     * @param content
     * @return
     */
    public static StringBuilder appendCode(StringBuilder sb, String content) {
        return sb.append("```").append(nl).append(content).append(nl).append("```").append(nl);
    }

    /**
     * 添加标准参数表头
     *
     * @param sb
     * @return
     */
    public static StringBuilder appendParamTh(StringBuilder sb) {
        sb.append(v).append("参数名").append(v)
                .append("必要").append(v)
                .append("参数类型").append(v)
                .append("描述").append(v)
                .append("备注").append(v).append(nl);
        return sb.append(th5).append(nl);
    }

}
