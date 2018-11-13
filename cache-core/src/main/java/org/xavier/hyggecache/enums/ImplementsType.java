package org.xavier.hyggecache.enums;

/**
 * 描述信息：<br/>
 * 实现类型枚举
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public enum ImplementsType {
    MEMORY(0, "Memory"),
    REDIS(1, "Redis");
    private final Integer index;
    private final String description;

    ImplementsType(Integer index, String description) {
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
