package org.xavier.hyggecache.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.xavier.hyggecache.helper.BaseCacheHelper;
import org.xavier.hyggecache.keeper.PointcutKeeper;

import java.lang.reflect.Method;

/**
 * 描述信息：<br/>
 * 缓存方法拦截器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class CacheInterceptor implements MethodInterceptor {
    private PointcutKeeper pointcutKeeper;

    public CacheInterceptor(PointcutKeeper pointcutKeeper) {
        this.pointcutKeeper = pointcutKeeper;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) {
        Method method = methodInvocation.getMethod();
        BaseCacheHelper helper = pointcutKeeper.queryHandler(method, methodInvocation.getThis().getClass());
        switch (helper.getCacheHelperType()) {
            case GET:
                break;
            case PUT:
                break;
            case INVALIDATE:
                break;
            default:
        }
        return null;
    }
}
