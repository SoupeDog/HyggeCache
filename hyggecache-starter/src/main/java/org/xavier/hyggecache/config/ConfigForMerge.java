package org.xavier.hyggecache.config;

import org.xavier.hyggecache.annotation.CacheInvalidate;
import org.xavier.hyggecache.annotation.CacheUpdate;
import org.xavier.hyggecache.annotation.Cacheable;
import org.xavier.hyggecache.annotation.CachedConfig;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;

import static org.xavier.hyggecache.utils.AnnotationPropertiesHelper.*;

/**
 * 描述信息：<br/>
 * 用于合并的配置项，囊括了所有配置
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
public class ConfigForMerge extends GlobalConfig {

    private String keyExpression;

    private String serializeTypeInfoKey;

    /**
     * 被拦截方法的返回值类型
     */
    private Class returnClass;

    public ConfigForMerge(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig, Class returnClass) {
        super();
        initPrefix(cacheAnnotationTemp, cachedConfig, globalConfig);
        initExpireInMillis(cacheAnnotationTemp, cachedConfig, globalConfig);
        initCacheNullValue(cacheAnnotationTemp, cachedConfig, globalConfig);
        initNullValueExpireInMillis(cacheAnnotationTemp, cachedConfig, globalConfig);
        initSerializerPolicy(cacheAnnotationTemp, cachedConfig, globalConfig);
        initSerializerName(cacheAnnotationTemp, cachedConfig, globalConfig);
        initSerializeTypeInfoKey(cacheAnnotationTemp, cachedConfig, globalConfig);
        initHotKeyCheck(cacheAnnotationTemp, cachedConfig, globalConfig);
        this.returnClass = returnClass;
    }

    public CacheOperatorConfig toCacheOperatorConfig() {
        CacheOperatorConfig result = new CacheOperatorConfig();
        result.setExpireInMillis(expireInMillis);
        result.setNullValueExpireInMillis(nullValueExpireInMillis);
        result.setPrefix(prefix);
        return result;
    }

    public SerializerConfig toSerializerConfig() {
        SerializerConfig result = new SerializerConfig();
        result.setReturnClass(returnClass);
        result.setTypeInfoName(serializeTypeInfoKey);
        return result;
    }

    private void initPrefix(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        String highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.prefix());
            this.keyExpression = cacheAnnotation.key();
            this.serializeTypeInfoKey = cacheAnnotation.serializeTypeInfoKey();
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.prefix());
            this.keyExpression = cacheAnnotation.key();
            this.serializeTypeInfoKey = cacheAnnotation.serializeTypeInfoKey();
        } else if (cacheAnnotationTemp instanceof CacheInvalidate) {
            CacheInvalidate cacheAnnotation = (CacheInvalidate) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.prefix());
            this.keyExpression = cacheAnnotation.key();
        } else {
            highestPriority = null;
        }
        secondPriority = getAsString(cachedConfig.prefix());
        thirdPriority = globalConfig.getPrefix();
        this.prefix = mergeAsString(highestPriority, secondPriority, thirdPriority);
    }

    private void initExpireInMillis(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        Long highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsLong(cacheAnnotation.expireInMillis(), null);
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsLong(cacheAnnotation.expireInMillis(), null);
        } else {
            highestPriority = null;
        }
        secondPriority = getAsLong(cachedConfig.expireInMillis(), null);
        thirdPriority = globalConfig.getExpireInMillis();
        this.expireInMillis = mergeAsLong(highestPriority, secondPriority, thirdPriority);
    }

    private void initCacheNullValue(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        Boolean highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsBoolean(cacheAnnotation.cacheNullValue(), null);
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsBoolean(cacheAnnotation.cacheNullValue(), null);
        } else if (cacheAnnotationTemp instanceof CacheInvalidate) {
            CacheInvalidate cacheAnnotation = (CacheInvalidate) cacheAnnotationTemp;
            highestPriority = getAsBoolean(cacheAnnotation.cacheNullValue(), null);
        } else {
            highestPriority = null;
        }
        secondPriority = getAsBoolean(cachedConfig.cacheNullValue(), null);
        thirdPriority = globalConfig.getCacheNullValue();
        this.cacheNullValue = mergeAsBoolean(highestPriority, secondPriority, thirdPriority);
    }

    private void initNullValueExpireInMillis(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        Long highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsLong(cacheAnnotation.nullValueExpireInMillis(), null);
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsLong(cacheAnnotation.nullValueExpireInMillis(), null);
        } else if (cacheAnnotationTemp instanceof CacheInvalidate) {
            CacheInvalidate cacheAnnotation = (CacheInvalidate) cacheAnnotationTemp;
            highestPriority = getAsLong(cacheAnnotation.nullValueExpireInMillis(), null);
        } else {
            highestPriority = null;
        }
        secondPriority = getAsLong(cachedConfig.nullValueExpireInMillis(), null);
        thirdPriority = globalConfig.getNullValueExpireInMillis();
        this.nullValueExpireInMillis = mergeAsLong(highestPriority, secondPriority, thirdPriority);
    }

    private void initSerializerPolicy(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        SerializerPolicyEnum highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsSerializerPolicyEnum(cacheAnnotation.serializerPolicy(), null);
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsSerializerPolicyEnum(cacheAnnotation.serializerPolicy(), null);
        } else {
            highestPriority = null;
        }
        secondPriority = getAsSerializerPolicyEnum(cachedConfig.serializerPolicy(), null);
        thirdPriority = globalConfig.getSerializerPolicy();
        this.serializerPolicy = mergeAsSerializerPolicyEnum(highestPriority, secondPriority, thirdPriority);
    }

    private void initSerializerName(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        String highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.serializerName());
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.serializerName());
        } else {
            highestPriority = null;
        }
        secondPriority = getAsString(cachedConfig.serializerName());
        thirdPriority = globalConfig.getSerializerName();
        this.serializerName = mergeAsString(highestPriority, secondPriority, thirdPriority);
    }

    private void initSerializeTypeInfoKey(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        String highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.serializeTypeInfoKey());
        } else if (cacheAnnotationTemp instanceof CacheUpdate) {
            CacheUpdate cacheAnnotation = (CacheUpdate) cacheAnnotationTemp;
            highestPriority = getAsString(cacheAnnotation.serializeTypeInfoKey());
        } else {
            highestPriority = null;
        }
        secondPriority = null;
        thirdPriority = null;
        this.serializeTypeInfoKey = mergeAsString(highestPriority, secondPriority, thirdPriority);
    }

    private void initHotKeyCheck(Object cacheAnnotationTemp, CachedConfig cachedConfig, GlobalConfig globalConfig) {
        Boolean highestPriority, secondPriority, thirdPriority;
        if (cacheAnnotationTemp instanceof Cacheable) {
            Cacheable cacheAnnotation = (Cacheable) cacheAnnotationTemp;
            highestPriority = getAsBoolean(cacheAnnotation.hotKeyCheck(), null);
        } else {
            highestPriority = null;
        }
        secondPriority = getAsBoolean(cachedConfig.hotKeyCheck(), null);
        thirdPriority = globalConfig.hotKeyCheck;
        this.hotKeyCheck = mergeAsBoolean(highestPriority, secondPriority, thirdPriority);
    }

    public String getKeyExpression() {
        return keyExpression;
    }

    public void setKeyExpression(String keyExpression) {
        this.keyExpression = keyExpression;
    }

    public String getSerializeTypeInfoKey() {
        return serializeTypeInfoKey;
    }

    public void setSerializeTypeInfoKey(String serializeTypeInfoKey) {
        this.serializeTypeInfoKey = serializeTypeInfoKey;
    }

    public Class getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class returnClass) {
        this.returnClass = returnClass;
    }
}
