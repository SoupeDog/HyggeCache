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
    private Long expireInMillis;
    private Boolean cacheNullValue;
    private Long nullValueExpireInMillis;
    private String prefix;

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
