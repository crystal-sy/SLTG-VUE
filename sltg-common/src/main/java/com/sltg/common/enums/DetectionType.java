package com.sltg.common.enums;

import com.sltg.common.utils.StringUtils;

/**
 * 检测类型
 * 
 * @author styra~
 */
public enum DetectionType {
    FALSE("0", "虚假"),
    SUSPECTED_FRAUD("1", "疑似诈骗"),
    REAL("2", "真实"),
    UNDETERMINED("3", "待定"),
    DIFF_SCENE("4", "分情况"),
    OTHERS("5", "其他");

    private final String code;
    private final String value;

    DetectionType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public static String getTypeInfo(String code) {
        if (StringUtils.isEmpty(code)) {
            return "";
        }

        for (DetectionType type : DetectionType.values()) {
            if (type.code.equals(code)) {
                return type.value;
            }
        }
        return "";
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
