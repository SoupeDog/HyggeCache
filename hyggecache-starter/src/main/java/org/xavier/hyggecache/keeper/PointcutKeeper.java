package org.xavier.hyggecache.keeper;

import org.xavier.hyggecache.helpper.BaseCacheHelpper;

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
    private ConcurrentMap<PointcutMarker, BaseCacheHelpper> handlers = new ConcurrentHashMap(64);

    /**
     * 查询 BaseCacheHelpper 缓存
     */
    public BaseCacheHelpper queryHandler(Method invocationMethod, Class<?> targetClass) {
        PointcutMarker currentPointcutMarker = new PointcutMarker(invocationMethod, targetClass);
        if (handlers.containsKey(currentPointcutMarker)) {
            return handlers.get(currentPointcutMarker);
        } else {
            return null;
        }
    }

    /**
     * 尝试缓存 BaseCacheHelpper
     *
     * @return 上一个 key 相同的 BaseCacheHelpper(如果为 null 说明之前不存在，未发生覆盖)
     */
    public BaseCacheHelpper putHandlerIfAbsent(Method invocationMethod, Class<?> targetClass, BaseCacheHelpper BaseCacheHelpper) {
        PointcutMarker currentPointcutMarker = new PointcutMarker(invocationMethod, targetClass);
        BaseCacheHelpper preBaseCacheHandler = handlers.putIfAbsent(currentPointcutMarker, BaseCacheHelpper);
        return preBaseCacheHandler;
    }

    /**
     * 尝试缓存 BaseCacheHelpper
     *
     * @return 上一个 key 相同的 BaseCacheHelpper(如果为 null 说明之前不存在，未发生覆盖)
     */
    public BaseCacheHelpper putHandlerIfAbsent(PointcutMarker currentPointcutMarker, BaseCacheHelpper BaseCacheHelpper) {
        BaseCacheHelpper preBaseCacheHandler = handlers.putIfAbsent(currentPointcutMarker, BaseCacheHelpper);
        return preBaseCacheHandler;
    }
}
