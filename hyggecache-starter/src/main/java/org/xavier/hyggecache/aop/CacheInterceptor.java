package org.xavier.hyggecache.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.evaluator.ExpressionEvaluator;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;
import org.xavier.hyggecache.helper.*;
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
    private static ExpressionEvaluator expressionEvaluator = new ExpressionEvaluator();

    public CacheInterceptor(PointcutKeeper pointcutKeeper) {
        this.pointcutKeeper = pointcutKeeper;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) {
        Object result = null;
        Method method = methodInvocation.getMethod();
        BaseCacheHelper helper = pointcutKeeper.queryHandler(method, methodInvocation.getThis().getClass());
        switch (helper.getCacheHelperType()) {
            case GET:
                result = doGet((BaseGetCacheHelper) helper, methodInvocation);
                break;
            case PUT:
                result = doPut((BasePutCacheHelper) helper, methodInvocation);
                break;
            case INVALIDATE:
                result = doInvalidate((BaseInvalidateCacheHelper) helper, methodInvocation);
                break;
            default:
                throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.INVOKE, "Fail to invoke,unsupported cache type : " + helper.getCacheHelperType().getDescription());
        }
        return result;
    }

    /**
     * 执行被缓存注解标注方法的实际逻辑
     */
    public Object actualInvoke(MethodInvocation methodInvocation) {
        Object result = null;
        try {
            result = methodInvocation.proceed();
        } catch (Throwable throwable) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.INVOKE, "Fail to actualInvoke.", throwable);
        }
        return result;
    }

    public Object doGet(BaseGetCacheHelper aopGetCacheHelper, MethodInvocation methodInvocation) {
        Object result = null;
        try {
            AopGetCacheHelper helper = (AopGetCacheHelper) aopGetCacheHelper;
            EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(helper.getMethodImpl(), methodInvocation.getArguments());
            AnnotatedElementKey annKey = new AnnotatedElementKey(helper.getMethodImpl(), methodInvocation.getThis().getClass());
            Object key = expressionEvaluator.key(helper.getKeyExpression(), annKey, evaluationContext);
            // 实际上并不需要入参
            result = helper.get(key, (nullVal) -> actualInvoke(methodInvocation));
        } catch (Throwable throwable) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_OPERATOR, "Fail to doGet.", throwable);
        }
        return result;
    }

    public Object doPut(BasePutCacheHelper aopPutCacheHelper, MethodInvocation methodInvocation) {
        Object result = null;
        try {
            result = this.actualInvoke(methodInvocation);
            AopPutCacheHelper helper = (AopPutCacheHelper) aopPutCacheHelper;
            EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(helper.getMethodImpl(), methodInvocation.getArguments());
            AnnotatedElementKey annKey = new AnnotatedElementKey(helper.getMethodImpl(), methodInvocation.getThis().getClass());
            evaluationContext.setVariable(ExpressionEvaluator.RESULT_VARIABLE, result);
            Object key = expressionEvaluator.key(helper.getKeyExpression(), annKey, evaluationContext);
            helper.put(key, result);
        } catch (Throwable throwable) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_OPERATOR, "Fail to doPut.", throwable);
        }
        return result;
    }

    public Object doInvalidate(BaseInvalidateCacheHelper aopInvalidateCacheHelper, MethodInvocation methodInvocation) {
        Object result = null;
        try {
            result = this.actualInvoke(methodInvocation);
            AopInvalidateCacheHelper helper = (AopInvalidateCacheHelper) aopInvalidateCacheHelper;
            EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(helper.getMethodImpl(), methodInvocation.getArguments());
            AnnotatedElementKey annKey = new AnnotatedElementKey(helper.getMethodImpl(), methodInvocation.getThis().getClass());
            evaluationContext.setVariable(ExpressionEvaluator.RESULT_VARIABLE, result);
            Object key = expressionEvaluator.key(helper.getKeyExpression(), annKey, evaluationContext);
            helper.invalidate(key);
        } catch (Throwable throwable) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.CACHE_OPERATOR, "Fail to doInvalidate.", throwable);
        }
        return result;
    }
}
