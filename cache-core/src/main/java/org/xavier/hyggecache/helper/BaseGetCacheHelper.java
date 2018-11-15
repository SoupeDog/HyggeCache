package org.xavier.hyggecache.helper;

import org.xavier.hyggecache.enums.CacheHelperType;
import org.xavier.hyggecache.enums.ImplementsType;

import java.util.Optional;
import java.util.function.Function;

/**
 * 描述信息：<br/>
 * 查询缓存执行对象基类
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public abstract class BaseGetCacheHelper<K> extends BaseCacheHelper {

    public BaseGetCacheHelper(ImplementsType implementsType) {
        this.implementsType = implementsType;
        cacheHelperType = CacheHelperType.GET;
    }

    /**
     * 查询缓存
     *
     * @param cacheKey 缓存唯一标识
     * @return 缓存对象的字节数据
     */
    public abstract Optional<byte[]> get(K cacheKey);

    /**
     * 查询缓存
     *
     * @param cacheKey         缓存唯一标识
     * @param getDefaultResult 缓存为空时的默认值返回方法
     * @return 序列化完成后的对象，可能为 null
     */
    public abstract Object get(K cacheKey, Function getDefaultResult);
}
