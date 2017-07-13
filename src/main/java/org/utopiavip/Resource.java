package org.utopiavip;

public interface Resource {

    // 普通资源
    String modifier = "private";
    String blank4 = "    ";
    String blank = " ";
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
}
