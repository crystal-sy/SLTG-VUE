package com.sltg.common.core.text;

import com.sltg.common.utils.StringUtils;

/**
 * 字符串格式化
 * 
 * @author styra~
 */
public class StrFormatter {
    public static final String EMPTY_JSON = "{}";
    public static final char C_BACKSLASH = '\\';
    public static final char C_DELIM_START = '{';
    public static final char C_DELIM_END = '}';

    /**
     * 格式化字符串<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param strPattern 字符串模板
     * @param argArray 参数列表
     * @return 结果
     */
    public static String format(final String strPattern, final Object... argArray) {
        if (StringUtils.isEmpty(strPattern) || StringUtils.isEmpty(argArray)) {
            return strPattern;
        }
        final int strPatternLength = strPattern.length();

        // 初始化定义好的长度以获得更好的性能
        StringBuilder buff = new StringBuilder(strPatternLength + 50);

        int handledPosition = 0;
        int delimitIndex;// 占位符所在位置
        for (int argIndex = 0; argIndex < argArray.length; argIndex++) {
            delimitIndex = strPattern.indexOf(EMPTY_JSON, handledPosition);
            if (delimitIndex == -1) {
                if (handledPosition == 0) {
                    return strPattern;
                } else { // 字符串模板剩余部分不再包含占位符，加入剩余部分后返回结果
                    buff.append(strPattern, handledPosition, strPatternLength);
                    return buff.toString();
                }
            } else {
                if (delimitIndex > 0 && strPattern.charAt(delimitIndex - 1) == C_BACKSLASH) {
                    if (delimitIndex > 1 && strPattern.charAt(delimitIndex - 2) == C_BACKSLASH) {
                        // 转义符之前还有一个转义符，占位符依旧有效
                        buff.append(strPattern, handledPosition, delimitIndex - 1);
                        buff.append(Convert.utf8Str(argArray[argIndex]));
                        handledPosition = delimitIndex + 2;
                    } else {
                        // 占位符被转义
                        argIndex--;
                        buff.append(strPattern, handledPosition, delimitIndex - 1);
                        buff.append(C_DELIM_START);
                        handledPosition = delimitIndex + 1;
                    }
                } else {
                    // 正常占位符
                    buff.append(strPattern, handledPosition, delimitIndex);
                    buff.append(Convert.utf8Str(argArray[argIndex]));
                    handledPosition = delimitIndex + 2;
                }
            }
        }
        // 加入最后一个占位符后所有的字符
        buff.append(strPattern, handledPosition, strPattern.length());
        return buff.toString();
    }
}
