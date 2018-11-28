package org.xavier.hyggecache.keeper;

import org.xavier.hyggecache.helper.BaseCacheHelper;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 描述信息：<br/>
 * 切入点 Handler 缓存管理对象<br/>
 * 缓存切入点与注解的绑定关系以及切入点对应的 Handler
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class PointcutKeeper {
    private volatile ConcurrentMap<PointcutMarker, BaseCacheHelper> handlers = new ConcurrentHashMap(64);

    /**
     * 查询 BaseCacheHelper 缓存
     */
    public BaseCacheHelper queryHandler(Method invocationMethod, Class<?> targetClass) {
        PointcutMarker currentPointcutMarker = new PointcutMarker(invocationMethod, targetClass);
        if (handlers.containsKey(currentPointcutMarker)) {
            return handlers.get(currentPointcutMarker);
        } else {
            return null;
        }
    }

    /**
     * 尝试缓存 BaseCacheHelper
     *
     * @return 上一个 key 相同的 BaseCacheHelper(如果为 null 说明之前不存在，未发生覆盖)
     */
    public BaseCacheHelper putHandlerIfAbsent(Method invocationMethod, Class<?> targetClass, BaseCacheHelper BaseCacheHelper) {
        PointcutMarker currentPointcutMarker = new PointcutMarker(invocationMethod, targetClass);
        BaseCacheHelper preBaseCacheHandler = handlers.putIfAbsent(currentPointcutMarker, BaseCacheHelper);
        return preBaseCacheHandler;
    }

    /**
     * 尝试缓存 BaseCacheHelper
     *
     * @return 上一个 key 相同的 BaseCacheHelper(如果为 null 说明之前不存在，未发生覆盖)
     */
    public BaseCacheHelper putHandlerIfAbsent(PointcutMarker currentPointcutMarker, BaseCacheHelper BaseCacheHelper) {
        BaseCacheHelper preBaseCacheHandler = handlers.putIfAbsent(currentPointcutMarker, BaseCacheHelper);
        return preBaseCacheHandler;
    }
}
