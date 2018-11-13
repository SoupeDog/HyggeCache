package hyggecache.operator.redis;

import org.xavier.hyggecache.config.CacheOperatorConfig;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;
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
    private final Jedis jedis;

    public RedisCacheOperator(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public Optional<byte[]> get(CacheOperatorConfig config, K cacheKey) {
        byte[] result = jedis.get(buildKey(config, cacheKey));
        return result == null ? Optional.empty() : Optional.of(result);
    }

    @Override
    public void put(CacheOperatorConfig config, K cacheKey, byte[] value) {
        if (config.getExpireInMillis() == null) {
            jedis.set(buildKey(config, cacheKey), value);
        } else {
            jedis.psetex(buildKey(config, cacheKey), config.getExpireInMillis(), value);
        }
    }

    @Override
    public void remove(CacheOperatorConfig config, K cacheKey) {
        //TODO 未完工
    }

    @Override
    public void putNullValueCache(CacheOperatorConfig config, K cacheKey) {

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
