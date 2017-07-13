package org.utopiavip;

public class Camel {

    private static char underline = "_".toCharArray()[0];

    /**
     * 下划线命名法转驼峰命名法
     *
     * @param columnName
     * @return
     */
    public static String toCamel(String columnName) {
        String lowerColumnName = columnName.toLowerCase(); // 大小转小写
        StringBuilder newColumnName = new StringBuilder();

        if (lowerColumnName.contains("_")) {
            boolean isNextLetterNeedUpper = false;
            char[] columnLetters = lowerColumnName.toCharArray();

            for (char letter : columnLetters) {
                if (underline == letter) {
                    isNextLetterNeedUpper = true;
                    continue;
                }

                if (isNextLetterNeedUpper) {
                    newColumnName.append(String.valueOf(letter).toUpperCase()); // 下划线后的字母大写
                    isNextLetterNeedUpper = false;

                } else {
                    newColumnName.append(letter);
                }
            }

        } else {
            newColumnName.append(lowerColumnName);
        }

        return newColumnName.toString();
    }

}
