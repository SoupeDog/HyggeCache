package org.xavier.hyggecache.enums;

/**
 * 描述信息：<br/>
 * 缓存操作类类型
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public enum CacheHelperType {
    GET(0, "Get"),
    PUT(1, "Put"),
    INVALIDATE(2, "Invalidate");
    private final Integer index;
    private final String description;

    CacheHelperType(Integer index, String description) {
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
