package org.xavier.hyggecache.config;

import java.util.function.Function;

/**
 * 描述信息：<br/>
 * 缓存操作类必须包含的配置项
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public class CacheOperatorConfig {
    /**
     * cacheKey 前缀
     */
    private String prefix;

    /**
     * 过期时间
     */
    private Long expireInMillis;

    /**
     * 查询结果为空标记过期时间(毫秒)
     */
    private Long nullValueExpireInMillis;

    /**
     * key 重写函数
     */
    private Function keyConverter;

    public CacheOperatorConfig() {
        // 有 key 脱敏需求可自行重置该函数，默认 key 不变
        keyConverter = key -> key;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getExpireInMillis() {
        return expireInMillis;
    }

    public void setExpireInMillis(Long expireInMillis) {
        this.expireInMillis = expireInMillis;
    }

    public Long getNullValueExpireInMillis() {
        return nullValueExpireInMillis;
    }

    public void setNullValueExpireInMillis(Long nullValueExpireInMillis) {
        this.nullValueExpireInMillis = nullValueExpireInMillis;
    }

    public Function getKeyConverter() {
        return keyConverter;
    }

    public void setKeyConverter(Function keyConverter) {
        this.keyConverter = keyConverter;
    }
}
