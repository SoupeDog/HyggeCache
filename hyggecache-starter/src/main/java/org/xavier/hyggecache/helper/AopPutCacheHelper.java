package org.xavier.hyggecache.helper;

import org.xavier.hyggecache.config.CacheOperatorConfig;
import org.xavier.hyggecache.config.SerializerConfig;
import org.xavier.hyggecache.enums.ImplementsType;

import java.lang.reflect.Method;

/**
 * 描述信息：<br/>
 * Aop 使用方式缓存 put 操作工具
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class AopPutCacheHelper<K> extends BasePutCacheHelper<K> implements AopCacheHelper {
    /**
     * 被执行缓存 AOP 方法的具体实现类
     */
    private Method methodImpl;

    /**
     * key 获取表达式
     */
    private String keyExpression;

    /**
     * 缓存操作类必须包含的配置项
     */
    private CacheOperatorConfig cacheOperatorConfig;

    /**
     * 序列化操作类必须包含的配置项
     */
    private SerializerConfig serializerConfig;

    /**
     * 实际返回结果为空的查询是否在缓存中标记无返回结果 <br/> true 开启
     */
    private Boolean cacheNullValue;

    public AopPutCacheHelper(ImplementsType implementsType) {
        super(implementsType);
    }

    @Override
    public void put(K cacheKey, Object latestObj) {
        cacheOperator.put(cacheOperatorConfig, cacheKey, serializer.serialize(latestObj));
    }

    public Method getMethodImpl() {
        return methodImpl;
    }

    public void setMethodImpl(Method methodImpl) {
        this.methodImpl = methodImpl;
    }

    public String getKeyExpression() {
        return keyExpression;
    }

    public void setKeyExpression(String keyExpression) {
        this.keyExpression = keyExpression;
    }

    public CacheOperatorConfig getCacheOperatorConfig() {
        return cacheOperatorConfig;
    }

    @Override
    public void setCacheOperatorConfig(CacheOperatorConfig cacheOperatorConfig) {
        this.cacheOperatorConfig = cacheOperatorConfig;
    }

    public SerializerConfig getSerializerConfig() {
        return serializerConfig;
    }

    public void setSerializerConfig(SerializerConfig serializerConfig) {
        this.serializerConfig = serializerConfig;
    }

    public Boolean getCacheNullValue() {
        return cacheNullValue;
    }

    public void setCacheNullValue(Boolean cacheNullValue) {
        this.cacheNullValue = cacheNullValue;
    }
}
