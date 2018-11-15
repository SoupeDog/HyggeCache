package org.xavier.hyggecache.utils;

import org.xavier.hyggecache.enums.SerializerPolicyEnum;

/**
 * 描述信息：<br/>
 * 注解参数解析工具<br/>
 * 注解参数里的空字符串"" 将被解析为 null<br/>
 * 数字溢出则返回对应类型的最大值
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class AnnotationPropertiesHelper {

    public static String getAsString(String target) {
        if ("".equals(target.trim())) {
            return null;
        }
        return target;
    }

    public static Long getAsLong(String target, Long defaultWhenException) {
        Long result = null;
        if (!"".equals(target.trim())) {
            try {
                result = Long.valueOf(target);
            } catch (NumberFormatException e) {
                result = defaultWhenException;
            }
        }
        return result;
    }

    public static Integer getAsInteger(String target, Integer defaultWhenException) {
        Integer result = null;
        if (!"".equals(target.trim())) {
            try {
                result = Integer.valueOf(target);
            } catch (NumberFormatException e) {
                result = defaultWhenException;
            }
        }
        return result;
    }

    public static SerializerPolicyEnum getAsSerializerPolicyEnum(String target, SerializerPolicyEnum defaultWhenUnexpected) {
        SerializerPolicyEnum result = null;
        switch (target.trim().toUpperCase()) {
            case "JACKSON":
                return SerializerPolicyEnum.JACKSON;
            case "FASTJSON":
                return SerializerPolicyEnum.FASTJSON;
            case "CUSTOM":
                return SerializerPolicyEnum.CUSTOM;
            default:
                return defaultWhenUnexpected;
        }
    }

    public static Boolean getAsBoolean(String target, Boolean defaultWhenUnexpected) {
        Boolean result = null;
        switch (target.trim().toUpperCase()) {
            case "TRUE":
                result = true;
                break;
            case "FALSE":
                result = false;
                break;
            default:
                result = defaultWhenUnexpected;
        }
        return result;
    }
}