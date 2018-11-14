package org.xavier.hyggecache.annotation;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.xavier.hyggecache.activate.AdviceModeConfigurationSelector;
import org.xavier.hyggecache.activate.HyggeCacheConfigurationSelector;
import org.xavier.hyggecache.enums.SerializerPolicySwitchEnum;

import java.lang.annotation.*;

/**
 * 描述信息：<br/>
 * 缓存激活注解
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({AdviceModeConfigurationSelector.class, HyggeCacheConfigurationSelector.class})
public @interface EnableHyggeCache {
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * 开启缓存注解的包路径
     */
    String[] basePackages();

    /**
     * 指定自动初始化的序列化代理
     */
    SerializerPolicySwitchEnum serializerAutoInit() default SerializerPolicySwitchEnum.JACKSON;
}
