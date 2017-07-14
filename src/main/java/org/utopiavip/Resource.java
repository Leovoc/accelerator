package org.utopiavip;

import org.utopiavip.bean.Table;

public interface Resource {

    // 普通资源
    String modifier = "private";
    String blank4 = "    ";
    String blank = " ";
    String blank8 = blank4 + blank4;
    String blank12 = blank8 + blank4;
    String blank16 = blank8 + blank8;
    char underline = "_".toCharArray()[0];
    String nl = "\n"; // newline
    String nl2 = "\n\n"; // newline2
    String semicolon = ";";

    // Markdown资源
    String v = "|"; // vertical 竖
    String v2 = "||";
    String v3 = "|||";
    String h = "------------"; // horizontal 横
    String h1 = "#";
    String h2 = "##";

    String th3 = "|------------|-------------|------------|"; // 3列表头
    String th5 = "|------------|-------------|------------|-------------|------------|"; // 5列表头

    String render(Table table);
}
