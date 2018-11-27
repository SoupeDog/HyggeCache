package org.xavier.hyggecache.operator;

import org.xavier.hyggecache.config.CacheOperatorConfig;

import java.util.ArrayList;
import java.util.Arrays;
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
public abstract class BaseCacheOperator<K> {
    public static final String CACHE_OPERATOR_BEAN_NAME = "default_CacheOperator";

    /**
     * 缓存 null 对象标识的实际值
     */
    public static final byte[] NULL_VALUE = "$N".getBytes();

    /**
     * 根据唯一标识查询缓存(失败应抛出异常)
     *
     * @param config   缓存配置
     * @param cacheKey 缓存的唯一标识
     * @return 查询结果的字节数组
     */
    public abstract Optional<byte[]> get(CacheOperatorConfig config, K cacheKey);


    /**
     * 更新缓存(失败应抛出异常)
     *
     * @param config   缓存配置
     * @param cacheKey 缓存唯一标识
     * @param value    缓存目标字节数组
     */
    public abstract void put(CacheOperatorConfig config, K cacheKey, byte[] value);

    /**
     * 清除缓存(失败应抛出异常)
     *
     * @param config   缓存配置
     * @param cacheKey 缓存唯一标识
     */
    public abstract Boolean remove(CacheOperatorConfig config, K cacheKey);

    /**
     * 为对应的 Key 缓存 NULL_VALUE 标识
     *
     * @param config   缓存配置
     * @param cacheKey 缓存唯一标识
     */
    public abstract void putNullValue(CacheOperatorConfig config, K cacheKey);

    /**
     * 获取远程缓存连接对象，该方法内部应实断开连接自检并自动重连，最终返回连接状态的连接对象
     *
     * @return 可用的连接对象
     */
    public abstract Object getConnection();


    /**
     * 检测是否为 缓存 null 标识 <br/> true : null 标识
     */
    public Boolean isNullFlag(byte[] checkTarget) {
        Boolean result = Arrays.equals(NULL_VALUE, checkTarget);
        return result;
    }
}
