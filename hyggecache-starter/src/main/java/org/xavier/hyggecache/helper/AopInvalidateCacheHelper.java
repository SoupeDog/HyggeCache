package org.xavier.hyggecache.helper;

import org.xavier.hyggecache.config.CacheOperatorConfig;
import org.xavier.hyggecache.enums.ImplementsType;

import java.lang.reflect.Method;

/**
 * 描述信息：<br/>
 * Aop 使用方式缓存 invalidate 操作工具
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class AopInvalidateCacheHelper<K> extends BaseInvalidateCacheHelper<K> {
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
     * 实际返回结果为空的查询是否在缓存中标记无返回结果 <br/> true 开启
     */
    private Boolean cacheNullValue;

    public AopInvalidateCacheHelper(ImplementsType implementsType) {
        super(implementsType);
    }

    @Override
    public void invalidate(K cacheKey) {
        if (cacheNullValue) {
            cacheOperator.putNullValue(cacheOperatorConfig, cacheKey);
        } else {
            cacheOperator.remove(cacheOperatorConfig, cacheKey);
        }
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

    public void setCacheOperatorConfig(CacheOperatorConfig cacheOperatorConfig) {
        this.cacheOperatorConfig = cacheOperatorConfig;
    }

    public Boolean getCacheNullValue() {
        return cacheNullValue;
    }

    public void setCacheNullValue(Boolean cacheNullValue) {
        this.cacheNullValue = cacheNullValue;
    }
}
