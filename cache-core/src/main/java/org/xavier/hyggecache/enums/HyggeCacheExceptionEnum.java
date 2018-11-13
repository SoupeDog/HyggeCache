package org.xavier.hyggecache.enums;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public enum HyggeCacheExceptionEnum {
    SERIALIZE(0, "Serialize Exception"),
    CACHE_KEY(1, "CacheKey Exception"),
    CACHE_OPERATOR(2, "Cache_Operator Exception");
    private final Integer index;
    private final String description;

    HyggeCacheExceptionEnum(Integer index, String description) {
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
