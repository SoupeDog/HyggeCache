package org.xavier.hyggecache.evaluator;

import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 描述信息：<br/>
 * 缓存表达式解析器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class ExpressionEvaluator extends CachedExpressionEvaluator {
    public static final String RESULT_VARIABLE = "result";
    private final Map<ExpressionKey, Expression> keyCache = new ConcurrentHashMap(64);

    public EvaluationContext createEvaluationContext(Method method, Object[] args) {
        // 该对象理应在 org.springframework.cache.interceptor 包下，因该项目本身就是缓存中间件，不能去依赖 SpringCache 故不引入
        // 似乎 rootObject 传入 null 也未对我们的预期目标产生实质影响，有待观察
        // CacheExpressionRootObject rootObject = new CacheExpressionRootObject(method, args, target, targetClass);
        MethodBasedEvaluationContext context = new MethodBasedEvaluationContext(
                null, method, args, getParameterNameDiscoverer());
        return context;
    }

    public Object key(String keyExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        return getExpression(this.keyCache, methodKey, keyExpression).getValue(evalContext);
    }
}
