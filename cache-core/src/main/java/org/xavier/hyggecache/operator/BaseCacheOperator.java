package org.xavier.hyggecache.operator;

import org.xavier.hyggecache.config.CacheOperatorConfig;
import org.xavier.hyggecache.config.HotKeyConfig;
import org.xavier.hyggecache.keeper.KeyKeeper;

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
     * 热点 Key 配置
     */
    protected HotKeyConfig hotKeyConfig;
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
     * 查询目标 key 对应的剩余过期时间
     *
     * @param cacheKeyByteArrayVal 缓存唯一标识
     */
    public abstract Long getTTL(byte[] cacheKeyByteArrayVal);

    /**
     * 重置 key 对应的剩余过期时间
     *
     * @param cacheKeyByteArrayVal 缓存唯一标识
     * @param newExpire            新的过期时间，单位由 Operator 自行确定
     */
    public abstract Long resetTTL(byte[] cacheKeyByteArrayVal, Number newExpire);

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

    /**
     * 热点 key 检测机制初始化
     */
    public abstract void initHotKeyCheck();

    /**
     * 热点 key 检测机制停止
     */
    public abstract void stopHotKeyCheck();

    public HotKeyConfig getHotKeyConfig() {
        return hotKeyConfig;
    }

    public void setHotKeyConfig(HotKeyConfig hotKeyConfig) {
        this.hotKeyConfig = hotKeyConfig;
    }
}
