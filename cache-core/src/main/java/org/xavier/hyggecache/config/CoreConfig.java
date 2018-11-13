package org.xavier.hyggecache.config;

/**
 * 描述信息：<br/>
 * 核心配置对象
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public class CoreConfig {
    /**
     * 缓存结果过期时间(毫秒)
     */
    private Long expireInMillis;

    /**
     * 实际返回结果为空的查询是否在缓存中标记无返回结果 <br/> true 开启
     */
    private Boolean cacheNullValue;

    /**
     * 查询结果为空标记过期时间(毫秒)
     */
    private Long nullValueExpireInMillis;

    /**
     * 是否自动创建默认的 Jackson 序列化工具 <br/> true 创建
     */
    private Boolean createDefaultSerializer;

    public CoreConfig() {
    }

    public Long getExpireInMillis() {
        return expireInMillis;
    }

    public void setExpireInMillis(Long expireInMillis) {
        this.expireInMillis = expireInMillis;
    }

    public Boolean getCacheNullValue() {
        return cacheNullValue;
    }

    public void setCacheNullValue(Boolean cacheNullValue) {
        this.cacheNullValue = cacheNullValue;
    }

    public Long getNullValueExpireInMillis() {
        return nullValueExpireInMillis;
    }

    public void setNullValueExpireInMillis(Long nullValueExpireInMillis) {
        this.nullValueExpireInMillis = nullValueExpireInMillis;
    }

    public Boolean getCreateDefaultSerializer() {
        return createDefaultSerializer;
    }

    public void setCreateDefaultSerializer(Boolean createDefaultSerializer) {
        this.createDefaultSerializer = createDefaultSerializer;
    }
}
