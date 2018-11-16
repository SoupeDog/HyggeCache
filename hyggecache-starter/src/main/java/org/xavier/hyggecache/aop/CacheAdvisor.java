package org.xavier.hyggecache.aop;

import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * 描述信息：<br/>
 * Cache 通知器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class CacheAdvisor extends AbstractBeanFactoryPointcutAdvisor {
    public static final String CACHE_ADVISOR_BEAN_NAME = "HyggeCacheAdvisor";
    private CachePointCut defaultPoinCut;

    public CacheAdvisor(CachePointCut defaultPoinCut) {
        this.defaultPoinCut = defaultPoinCut;
    }

    @Override
    public Pointcut getPointcut() {
        return this.defaultPoinCut;
    }
}
