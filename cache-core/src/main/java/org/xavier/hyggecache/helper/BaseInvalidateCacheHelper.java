package org.xavier.hyggecache.helper;

import org.xavier.hyggecache.enums.CacheHelperType;
import org.xavier.hyggecache.enums.ImplementsType;

/**
 * 描述信息：<br/>
 * 清除缓存执行对象基类
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public abstract class BaseInvalidateCacheHelper extends BaseCacheHelper {
    public BaseInvalidateCacheHelper(ImplementsType implementsType) {
        this.implementsType = implementsType;
        cacheHelperType = CacheHelperType.INVALIDATE;
    }

    /**
     * 清除缓存
     */
    public abstract void invalidate(String cacheKey);
}
