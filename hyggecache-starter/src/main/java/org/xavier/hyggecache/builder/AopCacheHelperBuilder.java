package org.xavier.hyggecache.builder;

import org.springframework.context.ApplicationContext;
import org.xavier.hyggecache.annotation.CachedConfig;
import org.xavier.hyggecache.config.ConfigForMerge;
import org.xavier.hyggecache.config.GlobalConfig;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.enums.ImplementsType;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;
import org.xavier.hyggecache.helper.AopGetCacheHelper;
import org.xavier.hyggecache.helper.AopInvalidateCacheHelper;
import org.xavier.hyggecache.helper.AopPutCacheHelper;
import org.xavier.hyggecache.keeper.PointcutKeeper;
import org.xavier.hyggecache.keeper.SerializerKeeper;
import org.xavier.hyggecache.operator.BaseCacheOperator;
import org.xavier.hyggecache.serializer.BaseSerializer;
import org.xavier.hyggecache.serializer.FastJsonSerializer;
import org.xavier.hyggecache.serializer.JacksonSerializer;

import java.lang.reflect.Method;

/**
 * 描述信息：<br/>
 * AOP 方式的缓存工具构造器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class AopCacheHelperBuilder {
    private ApplicationContext applicationContext;
    private SerializerKeeper serializerKeeper;
    private PointcutKeeper pointcutKeeper;
    private GlobalConfig globalConfig;
    private BaseCacheOperator cacheOperator;

    public AopCacheHelperBuilder(ApplicationContext applicationContext, BaseCacheOperator cacheOperator, SerializerKeeper serializerKeeper, PointcutKeeper pointcutKeeper, GlobalConfig globalConfig) {
        this.applicationContext = applicationContext;
        this.serializerKeeper = serializerKeeper;
        this.pointcutKeeper = pointcutKeeper;
        this.globalConfig = globalConfig;
        this.cacheOperator = cacheOperator;
    }

    public AopGetCacheHelper createGet(Object cacheAnnotationTemp, CachedConfig cachedConfig, Method methodImpl) {
        AopGetCacheHelper result = new AopGetCacheHelper(ImplementsType.REDIS);
        ConfigForMerge configForMerge = new ConfigForMerge(cacheAnnotationTemp, cachedConfig, globalConfig);
        result.setMethodImpl(methodImpl);
        result.setKeyExpression(configForMerge.getKeyExpression());
        result.setCacheOperatorConfig(configForMerge.toCacheOperatorConfig());
        result.setSerializerConfig(configForMerge.toSerializerConfig());
        result.setCacheNullValue(configForMerge.getCacheNullValue());
        result.setCacheOperator(cacheOperator);
        BaseSerializer targetSerializer = getSerializer(configForMerge);
        result.setSerializer(targetSerializer);
        return result;
    }

    public AopPutCacheHelper createPut(Object cacheAnnotationTemp, CachedConfig cachedConfig, Method methodImpl) {
        AopPutCacheHelper result = new AopPutCacheHelper(ImplementsType.REDIS);
        ConfigForMerge configForMerge = new ConfigForMerge(cacheAnnotationTemp, cachedConfig, globalConfig);
        result.setMethodImpl(methodImpl);
        result.setKeyExpression(configForMerge.getKeyExpression());
        result.setCacheOperatorConfig(configForMerge.toCacheOperatorConfig());
        result.setSerializerConfig(configForMerge.toSerializerConfig());
        result.setCacheNullValue(configForMerge.getCacheNullValue());
        result.setCacheOperator(cacheOperator);
        BaseSerializer targetSerializer = getSerializer(configForMerge);
        result.setSerializer(targetSerializer);
        return result;
    }

    public AopInvalidateCacheHelper createInvalidate(Object cacheAnnotationTemp, CachedConfig cachedConfig, Method methodImpl) {
        AopInvalidateCacheHelper result = new AopInvalidateCacheHelper(ImplementsType.REDIS);
        ConfigForMerge configForMerge = new ConfigForMerge(cacheAnnotationTemp, cachedConfig, globalConfig);
        result.setMethodImpl(methodImpl);
        result.setKeyExpression(configForMerge.getKeyExpression());
        result.setCacheOperatorConfig(configForMerge.toCacheOperatorConfig());
        result.setCacheNullValue(configForMerge.getCacheNullValue());
        result.setCacheOperator(cacheOperator);
        return result;
    }

    private BaseSerializer getSerializer(ConfigForMerge configForMerge) {
        BaseSerializer targetSerializer;
        switch (configForMerge.getSerializerPolicy()) {
            case JACKSON:
                targetSerializer = serializerKeeper.querySerializerByBeanName(JacksonSerializer.DEFAULT_NAME);
                break;
            case FASTJSON:
                targetSerializer = serializerKeeper.querySerializerByBeanName(FastJsonSerializer.DEFAULT_NAME);
                break;
            default:
                String beanName = configForMerge.getSerializerName();
                if (beanName == null) {
                    throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "SerializerName can't be null when SerializerPolicy(" + configForMerge.getSerializerPolicy().name() + ").");
                }
                targetSerializer = serializerKeeper.querySerializerByBeanName(configForMerge.getSerializerName());
        }
        if (targetSerializer == null) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "Serializer was null. | SerializerInfo : " + configForMerge.getSerializerPolicy().name() + " | " + configForMerge.getSerializerName());
        }
        return targetSerializer;
    }

    public PointcutKeeper getPointcutKeeper() {
        return pointcutKeeper;
    }

    public void setPointcutKeeper(PointcutKeeper pointcutKeeper) {
        this.pointcutKeeper = pointcutKeeper;
    }
}