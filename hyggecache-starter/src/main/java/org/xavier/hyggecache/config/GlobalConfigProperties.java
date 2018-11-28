package org.xavier.hyggecache.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 描述信息：<br/>
 * 全局属性
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.01
 * @since Jdk 1.8
 */
@ConfigurationProperties(prefix = "hyggecache.default")
public class GlobalConfigProperties {
    /**
     * 默认缓存过期时间(毫秒)，默认 1 小时
     */
    private Long expireInMillis;
    /**
     * 是否为不存在的查询结果进行 null 标识，默认 标识
     */
    private Boolean cacheNullValue;
    /**
     * 默认 null 标识过期时间(毫秒)，默认 5 秒
     */
    private Long nullValueExpireInMillis;
    /**
     * 默认的缓存 key 前缀
     */
    private String prefix;
    /**
     * 热点 key 最小 QPS
     */
    private Integer hotKeyMinQPS;
    /**
     * 热点 key 刷新检测间隔
     */
    private Long hotKeyRescueDelta;

    public Long getExpireInMillis() {
        return expireInMillis == null ? 3600000L : expireInMillis;
    }

    public void setExpireInMillis(Long expireInMillis) {
        this.expireInMillis = expireInMillis;
    }

    public Boolean getCacheNullValue() {
        return cacheNullValue == null ? true : cacheNullValue;
    }

    public void setCacheNullValue(Boolean cacheNullValue) {
        this.cacheNullValue = cacheNullValue;
    }

    public Long getNullValueExpireInMillis() {
        return nullValueExpireInMillis == null ? 5000L : nullValueExpireInMillis;
    }

    public void setNullValueExpireInMillis(Long nullValueExpireInMillis) {
        this.nullValueExpireInMillis = nullValueExpireInMillis;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
