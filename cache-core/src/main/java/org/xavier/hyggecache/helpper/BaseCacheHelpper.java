package org.xavier.hyggecache.helpper;

import org.xavier.hyggecache.config.CoreConfig;
import org.xavier.hyggecache.enums.CacheHelperType;
import org.xavier.hyggecache.enums.ImplementsType;
import org.xavier.hyggecache.operator.CacheOperator;
import org.xavier.hyggecache.serializer.BaseSerializer;

/**
 * 描述信息：<br/>
 * 缓存逻辑执行核心对象基类
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public abstract class BaseCacheHelpper {
    /**
     * 缓存逻辑类型
     */
    protected CacheHelperType cacheHelperType;
    /**
     * 实现类型
     */
    protected ImplementsType implementsType;

    /**
     * 核心配置项
     */
    protected CoreConfig coreConfig;

    /**
     * 序列化工具
     */
    protected BaseSerializer serializer;

    /**
     * 缓存落地操作对象
     */
    protected CacheOperator cacheOperator;

    public CacheHelperType getCacheHelperType() {
        return cacheHelperType;
    }

    public void setCacheHelperType(CacheHelperType cacheHelperType) {
        this.cacheHelperType = cacheHelperType;
    }

    public ImplementsType getImplementsType() {
        return implementsType;
    }

    public void setImplementsType(ImplementsType implementsType) {
        this.implementsType = implementsType;
    }

    public CoreConfig getCoreConfig() {
        return coreConfig;
    }

    public void setCoreConfig(CoreConfig coreConfig) {
        this.coreConfig = coreConfig;
    }

    public BaseSerializer getSerializer() {
        return serializer;
    }

    public void setSerializer(BaseSerializer serializer) {
        this.serializer = serializer;
    }

    public CacheOperator getCacheOperator() {
        return cacheOperator;
    }

    public void setCacheOperator(CacheOperator cacheOperator) {
        this.cacheOperator = cacheOperator;
    }
}
