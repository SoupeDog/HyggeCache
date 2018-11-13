package org.xavier.hyggecache.enums;

/**
 * 描述信息：<br/>
 * 序列化代理枚举
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public enum SerializerPolicyEnum {
    JACKSON(0, "Jacson_SerializerPolicy"),
    FASTJSON(1, "Fastjson_SerializerPolicy"),
    CUSTOM(2, "Custom");
    private final Integer index;
    private final String description;

    SerializerPolicyEnum(Integer index, String description) {
        this.index = index;
        this.description = description;
    }

    public Integer getIndex() {
        return index;
    }

    public String getDescription() {
        return description;
    }
}
