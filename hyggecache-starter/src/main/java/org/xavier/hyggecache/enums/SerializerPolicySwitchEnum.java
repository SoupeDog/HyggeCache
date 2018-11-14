package org.xavier.hyggecache.enums;

/**
 * 描述信息：<br/>
 * 指定自动初始化的序列化工具
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public enum SerializerPolicySwitchEnum {
    JACKSON(0, "Jackson"),
    FASTJSON(1, "Fastjson"),
    BOTH(2, "Both"),
    NONE(3, "None");
    private final Integer index;
    private final String description;

    SerializerPolicySwitchEnum(Integer index, String description) {
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
