package org.xavier.hyggecache.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述信息：<br/>
 * AopGetCacheHelper 通知器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    @Override
    public Pointcut getPointcut() {
        return null;
    }
}
