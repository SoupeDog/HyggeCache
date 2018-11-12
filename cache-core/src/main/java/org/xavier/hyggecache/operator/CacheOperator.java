package org.xavier.hyggecache.operator;

import org.xavier.hyggecache.config.CacheOperatorConfig;

import java.util.Optional;


/**
 * 描述信息：<br/>
 * 缓存落地操作对象接口
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public interface CacheOperator {

    /**
     * 根据唯一标识查询缓存(失败应抛出异常)
     *
     * @param cacheKey 缓存的唯一标识
     * @return 查询结果的字节数组
     */
    Optional<byte[]> get(String cacheKey);


    /**
     * 更新缓存(失败应抛出异常)
     *
     * @param config   缓存配置
     * @param cacheKey 缓存唯一标识
     * @param value    缓存目标字节数组
     */
    void put(CacheOperatorConfig config, String cacheKey, byte[] value);

    /**
     * 清除缓存(失败应抛出异常)
     *
     * @param cacheKey 缓存唯一标识
     */
    void remove(String cacheKey);
}
