package org.xavier.hyggecache.operator.redis;

import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.xavier.hyggecache.config.CacheOperatorConfig;
import org.xavier.hyggecache.config.HotKeyConfig;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;
import org.xavier.hyggecache.keeper.KeyKeeper;
import org.xavier.hyggecache.operator.BaseCacheOperator;
import redis.clients.jedis.Jedis;

import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.function.Function;

/**
 * 描述信息：<br/>
 * 基于 redis 的缓存落地操作工具
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public class RedisCacheOperator<K> extends BaseCacheOperator<K> {
    private JedisConnectionFactory jedisConnectionFactory;
    private KeyKeeper<K> keyKeeper;

    public RedisCacheOperator(HotKeyConfig hotKeyConfig, JedisConnectionFactory jedisConnectionFactory) {
        this.jedisConnectionFactory = jedisConnectionFactory;
        keyKeeper = new KeyKeeper(hotKeyConfig, this);
    }

    @Override
    public Optional<byte[]> get(CacheOperatorConfig config, K cacheKey) {
        Jedis jedis = (Jedis) getConnection();
        byte[] bytes_Key = buildKey(config, cacheKey);
        keyKeeper.count(bytes_Key);
        byte[] result = jedis.get(bytes_Key);
        return result == null ? Optional.empty() : Optional.of(result);
    }

    @Override
    public void put(CacheOperatorConfig config, K cacheKey, byte[] value) {
        Jedis jedis = (Jedis) getConnection();
        byte[] bytes_Key = buildKey(config, cacheKey);
        if (config.getExpireInMillis() == null) {
            jedis.set(bytes_Key, value);
        } else {
            jedis.psetex(bytes_Key, config.getExpireInMillis(), value);
        }
    }

    @Override
    public Boolean remove(CacheOperatorConfig config, K cacheKey) {
        Jedis jedis = (Jedis) getConnection();
        byte[] bytes_Key = buildKey(config, cacheKey);
        return jedis.del(bytes_Key) == 1;
    }

    @Override
    public void putNullValue(CacheOperatorConfig config, K cacheKey) {
        Jedis jedis = (Jedis) getConnection();
        byte[] bytes_Key = buildKey(config, cacheKey);
        if (config.getNullValueExpireInMillis() != null) {
            jedis.psetex(bytes_Key, config.getNullValueExpireInMillis(), NULL_VALUE);
        } else {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_OPERATOR, "[NullValueExpireInMillis] was null.");
        }
    }

    @Override
    public Object getConnection() {
        Jedis result = (Jedis) jedisConnectionFactory.getConnection().getNativeConnection();
        return result;
    }

    @Override
    public Long getTTL(byte[] cacheKeyByteArrayVal) {
        Jedis jedis = (Jedis) getConnection();
        return jedis.ttl(cacheKeyByteArrayVal);
    }

    @Override
    public Long resetTTL(byte[] cacheKeyByteArrayVal, Number newExpire) {
        Jedis jedis = (Jedis) getConnection();
        return jedis.expire(cacheKeyByteArrayVal, newExpire.intValue());
    }

    protected byte[] buildKey(CacheOperatorConfig config, K key) {
        try {
            byte[] keyBytes;
            if (key instanceof byte[]) {
                keyBytes = (byte[]) key;
            } else if (key instanceof String) {
                keyBytes = ((String) key).getBytes("utf-8");
            } else {
                Object newKey = convertKey(key, config.getKeyConverter());
                if (newKey instanceof byte[]) {
                    keyBytes = (byte[]) newKey;
                } else if (newKey instanceof String) {
                    keyBytes = ((String) newKey).getBytes("utf-8");
                } else if (newKey instanceof Number) {
                    keyBytes = newKey.toString().getBytes("utf-8");
                } else {
                    throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_KEY, String.format("Unsupported type of converted key %s", newKey.getClass()));
                }
            }

            byte[] prefixBytes = config.getPrefix().getBytes("UTF-8");
            byte[] rt = new byte[prefixBytes.length + keyBytes.length];
            System.arraycopy(prefixBytes, 0, rt, 0, prefixBytes.length);
            System.arraycopy(keyBytes, 0, rt, prefixBytes.length, keyBytes.length);
            return rt;
        } catch (UnsupportedEncodingException e) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_KEY, "UTF-8 was Unsupported.", e);
        }
    }

    protected Object convertKey(K key, Function keyConverter) {
        try {
            return keyConverter.apply(key);
        } catch (Throwable e) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_KEY, "Key converting failed", e);
        }
    }
}
