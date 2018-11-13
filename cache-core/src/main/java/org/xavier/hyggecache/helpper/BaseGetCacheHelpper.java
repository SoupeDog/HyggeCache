package org.xavier.hyggecache.helpper;

import org.xavier.hyggecache.enums.CacheHelperType;
import org.xavier.hyggecache.enums.ImplementsType;

/**
 * 描述信息：<br/>
 * 查询缓存执行对象基类
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public abstract class BaseGetCacheHelpper extends BaseCacheHelpper {

    public BaseGetCacheHelpper(ImplementsType implementsType) {
        this.implementsType = implementsType;
        cacheHelperType = CacheHelperType.GET;
    }
    /**
     * 查询缓存
     */
    public abstract void get(String cacheKey);
}
