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
     * 默认的 key 存储数量，设置一个合理值减少重建 Hash 的次数
     */
    private Integer defaultSize;
    /**
     * key 存储的负载因子
     */
    private Float loadFactor;
    /**
     * 开启热点 key 检测的开关
     */
    private Boolean isHotKeyCheckActive;

    /**
     * 热点 key 最小 QPS
     */
    private Integer hotKeyMinQPS;
    /**
     * 热点 key 刷新检测间隔
     */
    private Long hotKeyRescueDelta;
    /**
     * 单次检测热点 key 最大刷新数量
     */
    private Integer hotKeyRescueMaxSize;

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

    public void setNullValueExpireInMillis(Long nullValueExpireInMillis) {
        this.nullValueExpireInMillis = nullValueExpireInMillis;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Long getNullValueExpireInMillis() {
        return nullValueExpireInMillis == null ? 5000L : nullValueExpireInMillis;
    }

    public Integer getDefaultSize() {
        return defaultSize == null ? 100000 : defaultSize;
    }

    public void setDefaultSize(Integer defaultSize) {
        this.defaultSize = defaultSize;
    }

    public Float getLoadFactor() {
        return loadFactor == null ? 0.75F : loadFactor;
    }

    public void setLoadFactor(Float loadFactor) {
        this.loadFactor = loadFactor;
    }

    public Boolean getHotKeyCheckActive() {
        return isHotKeyCheckActive == null ? false : isHotKeyCheckActive;
    }

    public void setHotKeyCheckActive(Boolean hotKeyCheckActive) {
        isHotKeyCheckActive = hotKeyCheckActive;
    }

    public Integer getHotKeyMinQPS() {
        return hotKeyMinQPS == null ? 100 : hotKeyMinQPS;
    }

    public void setHotKeyMinQPS(Integer hotKeyMinQPS) {
        this.hotKeyMinQPS = hotKeyMinQPS;
    }

    public Long getHotKeyRescueDelta() {
        return hotKeyRescueDelta == null ? 5000L : hotKeyRescueDelta;
    }

    public void setHotKeyRescueDelta(Long hotKeyRescueDelta) {
        this.hotKeyRescueDelta = hotKeyRescueDelta;
    }

    public Integer getHotKeyRescueMaxSize() {
        return hotKeyRescueMaxSize == null ? 100 : hotKeyRescueMaxSize;
    }

    public void setHotKeyRescueMaxSize(Integer hotKeyRescueMaxSize) {
        this.hotKeyRescueMaxSize = hotKeyRescueMaxSize;
    }
}
