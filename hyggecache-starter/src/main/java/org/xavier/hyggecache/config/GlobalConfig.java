package org.xavier.hyggecache.config;


import org.xavier.hyggecache.enums.SerializerPolicyEnum;

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
     * 是否启动 HyggeCache
     */
    private Boolean isHyggeCacheActive;
    /**
     * cacheKey 前缀
     */
    protected String prefix;

    /**
     * 序列化工具类型
     */
    protected SerializerPolicyEnum serializerPolicy;

    /**
     * 序列化工具类型 BeanName ,SerializerPolicyEnum 非 Custom 可不填
     */
    protected String serializerName;

    /**
     * 是否开启热点 key 挽救
     */
    protected Boolean hotKeyCheck;

    public GlobalConfig() {
    }

    public GlobalConfig(GlobalConfigProperties globalConfigProperties) {
        this.expireInMillis = globalConfigProperties.getExpireInMillis();
        this.cacheNullValue = globalConfigProperties.getCacheNullValue();
        this.nullValueExpireInMillis = globalConfigProperties.getNullValueExpireInMillis();
        this.prefix = globalConfigProperties.getPrefix();
        this.isHyggeCacheActive=globalConfigProperties.getHyggeCacheActive();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public SerializerPolicyEnum getSerializerPolicy() {
        return serializerPolicy;
    }

    public void setSerializerPolicy(SerializerPolicyEnum serializerPolicy) {
        this.serializerPolicy = serializerPolicy;
    }

    public String getSerializerName() {
        return serializerName;
    }

    public void setSerializerName(String serializerName) {
        this.serializerName = serializerName;
    }

    public Boolean getHotKeyCheck() {
        return hotKeyCheck;
    }

    public void setHotKeyCheck(Boolean hotKeyCheck) {
        this.hotKeyCheck = hotKeyCheck;
    }

    public Boolean getHyggeCacheActive() {
        return isHyggeCacheActive;
    }

    public void setHyggeCacheActive(Boolean hyggeCacheActive) {
        isHyggeCacheActive = hyggeCacheActive;
    }
}
