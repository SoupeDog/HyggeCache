package org.xavier.hyggecache.config;


/**
 * 描述信息：<br/>
 * 全局配置对象
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class GlobalConfig extends CoreConfig {
    /**
     * cacheKey 前缀
     */
    private String prefix;

    public GlobalConfig(GlobalConfigProperties globalConfigProperties) {
        this.expireInMillis = globalConfigProperties.getExpireInMillis();
        this.cacheNullValue = globalConfigProperties.getCacheNullValue();
        this.nullValueExpireInMillis = globalConfigProperties.getNullValueExpireInMillis();
        this.prefix = globalConfigProperties.getPrefix();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
