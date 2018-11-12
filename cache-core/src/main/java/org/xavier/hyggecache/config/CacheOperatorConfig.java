package org.xavier.hyggecache.config;

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
     * 过期时间
     */
    private Long expireInMillis;

    public CacheOperatorConfig() {
    }

    public Long getExpireInMillis() {
        return expireInMillis;
    }

    public void setExpireInMillis(Long expireInMillis) {
        this.expireInMillis = expireInMillis;
    }
}
