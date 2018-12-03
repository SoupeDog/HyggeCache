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

    public static Long getAsLong(String target, Long defaultWhenUnexpected) {
        Long result = null;
        if (!"".equals(target.trim())) {
            try {
                result = Long.valueOf(target);
            } catch (NumberFormatException e) {
                result = defaultWhenUnexpected;
            }
        }
        return result;
    }

    public static Integer getAsInteger(String target, Integer defaultWhenUnexpected) {
        Integer result = null;
        if (!"".equals(target.trim())) {
            try {
                result = Integer.valueOf(target);
            } catch (NumberFormatException e) {
                result = defaultWhenUnexpected;
            }
        }
        return result;
    }

    public static SerializerPolicyEnum getAsSerializerPolicyEnum(String target, SerializerPolicyEnum defaultWhenUnexpected) {
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
            case "1":
                result = true;
                break;
            case "FALSE":
            case "0":
                result = false;
                break;
            default:
                result = defaultWhenUnexpected;
        }
        return result;
    }

    public static String mergeAsString(String highestPriority, String secondPriority, String thirdPriority) {
        return (String) mergeObject(highestPriority, secondPriority, thirdPriority);
    }

    public static Long mergeAsLong(Long highestPriority, Long secondPriority, Long thirdPriority) {
        return (Long) mergeObject(highestPriority, secondPriority, thirdPriority);
    }

    public static Integer mergeAsInteger(Integer highestPriority, Integer secondPriority, Integer thirdPriority) {
        return (Integer) mergeObject(highestPriority, secondPriority, thirdPriority);
    }

    public static Boolean mergeAsBoolean(Boolean highestPriority, Boolean secondPriority, Boolean thirdPriority) {
        return (Boolean) mergeObject(highestPriority, secondPriority, thirdPriority);
    }

    public static SerializerPolicyEnum mergeAsSerializerPolicyEnum(SerializerPolicyEnum highestPriority, SerializerPolicyEnum secondPriority, SerializerPolicyEnum thirdPriority) {
        return (SerializerPolicyEnum) mergeObject(highestPriority, secondPriority, thirdPriority);
    }

    private static Object mergeObject(Object highestPriority, Object secondPriority, Object thirdPriority) {
        if (highestPriority != null) {
            return highestPriority;
        } else if (secondPriority != null) {
            return secondPriority;
        } else {
            return thirdPriority;
        }
    }
}