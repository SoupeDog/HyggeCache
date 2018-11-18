package org.xavier.hyggecache.aop;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.xavier.hyggecache.annotation.CacheInvalidate;
import org.xavier.hyggecache.annotation.CacheUpdate;
import org.xavier.hyggecache.annotation.Cacheable;
import org.xavier.hyggecache.annotation.CachedConfig;
import org.xavier.hyggecache.builder.AopCacheHelperBuilder;
import org.xavier.hyggecache.enums.CacheHelperType;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;
import org.xavier.hyggecache.helper.BaseCacheHelper;
import org.xavier.hyggecache.keeper.PointcutKeeper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
public class CachePointCut extends StaticMethodMatcherPointcut implements ClassFilter {
    /**
     * 用于过滤不必要的 AOP 切入点检测
     */
    private String[] basePackages;

    /**
     * 缓存工具构建器
     */
    private AopCacheHelperBuilder cacheHelperBuilder;

    /**
     * 切入点信息缓存
     */
    private PointcutKeeper pointcutKeeper;

    public CachePointCut(String[] basePackages, AopCacheHelperBuilder cacheHelperBuilder, PointcutKeeper pointcutKeeper) {
        this.basePackages = basePackages;
        this.cacheHelperBuilder = cacheHelperBuilder;
        this.pointcutKeeper = pointcutKeeper;
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return classCheck(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        boolean result = packageCheack(targetClass);
        BaseCacheHelper cacheHelper;
        if (result) {
            result = annotationCheack(method);
            if (result) {
                switch (getCacheHelperType(method)) {
                    case GET:
                        cacheHelper = cacheHelperBuilder.createGet(AnnotationUtils.getAnnotation(method, Cacheable.class), getCachedConfigOnClass(targetClass), getMethod_Impl(targetClass, method));
                        pointcutKeeper.putHandlerIfAbsent(method, targetClass, cacheHelper);
                        break;
                    case PUT:
                        cacheHelper = cacheHelperBuilder.createPut(AnnotationUtils.getAnnotation(method, CacheUpdate.class), getCachedConfigOnClass(targetClass), getMethod_Impl(targetClass, method));
                        pointcutKeeper.putHandlerIfAbsent(method, targetClass, cacheHelper);
                        break;
                    case INVALIDATE:
                        cacheHelper = cacheHelperBuilder.createInvalidate(AnnotationUtils.getAnnotation(method, CacheInvalidate.class), getCachedConfigOnClass(targetClass), getMethod_Impl(targetClass, method));
                        pointcutKeeper.putHandlerIfAbsent(method, targetClass, cacheHelper);
                        break;
                    default:
                }
            } else {
                System.err.println("AaA");
            }
        }
        return result;
    }

    public Boolean classCheck(Class<?> aClass) {
        String className = null;
        Boolean result = false;
        if (aClass != null) {
            className = aClass.getName();
            if (className.startsWith("java")) {
                result = true;
            } else if (className.startsWith("org.springframework")) {
                result = true;
            } else if (className.indexOf("$$EnhancerBySpringCGLIB$$") >= 0) {
                result = true;
            } else {
                result = false;
            }
        }
        return result;
    }

    public Boolean packageCheack(Class<?> targetClass) {
        Boolean result = false;
        if (basePackages != null) {
            for (String p : basePackages) {
                if (targetClass.getName().startsWith(p)) {
                    result = true;
                }
            }
        }
        return result;
    }

    public Boolean annotationCheack(Method method) {
        Boolean result = method.isAnnotationPresent(Cacheable.class) ||
                method.isAnnotationPresent(CacheUpdate.class) ||
                method.isAnnotationPresent(CacheInvalidate.class);
        return result;
    }

    private boolean methodMatch(String originalMethodName, Method method, Class<?>[] paramTypes) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        if (!originalMethodName.equals(method.getName())) {
            return false;
        }
        Class<?>[] ps = method.getParameterTypes();
        if (ps.length != paramTypes.length) {
            return false;
        }
        for (int i = 0; i < ps.length; i++) {
            if (!ps[i].equals(paramTypes[i])) {
                return false;
            }
        }
        return true;
    }

    private Method getMethod_Impl(Class<?> targetClass, Method method) {
        String originalMethodName = method.getName();
        Class<?>[] originalMethodParamTypes = method.getParameterTypes();
        Method[] methods = targetClass.getMethods();
        for (Method methodTemp : methods) {
            if (methodMatch(originalMethodName, methodTemp, originalMethodParamTypes)) {
                return methodTemp;
            }
        }
        throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.HELPER, "Implement of " + targetClass.getName() + " - " + method.getName() + " was not found.");
    }


    private CacheHelperType getCacheHelperType(Method methodWithAnnotation) {
        if (methodWithAnnotation.isAnnotationPresent(Cacheable.class)) {
            return CacheHelperType.GET;
        } else if (methodWithAnnotation.isAnnotationPresent(CacheUpdate.class)) {
            return CacheHelperType.PUT;
        } else if (methodWithAnnotation.isAnnotationPresent(CacheInvalidate.class)) {
            return CacheHelperType.INVALIDATE;
        } else {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.HELPER, "Annotation was not found on " + methodWithAnnotation.getName() + ".");
        }
    }

    private CachedConfig getCachedConfigOnClass(Class<?> targetClass) {
        CachedConfig cachedConfigAnnotation = AnnotationUtils.findAnnotation(targetClass, CachedConfig.class);
        if (cachedConfigAnnotation == null) {
            Class<?>[] interfaceClassArray = targetClass.getInterfaces();
            for (Class<?> interfaceClass : interfaceClassArray) {
                cachedConfigAnnotation = AnnotationUtils.findAnnotation(interfaceClass, CachedConfig.class);
                if (cachedConfigAnnotation != null) {
                    break;
                }
            }
        }
        if (cachedConfigAnnotation == null) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.HELPER, "CachedConfig of [" + targetClass.getName() + "] was not found.");
        }
        return cachedConfigAnnotation;
    }
}