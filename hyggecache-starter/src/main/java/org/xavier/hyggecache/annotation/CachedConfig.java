package org.xavier.hyggecache.annotation;

import java.lang.annotation.*;

/**
 * 描述信息：<br/>
 * 标注在类上的缓存配置注解
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CachedConfig {
    String prefix() default "";

    String serializerPolicy() default "";

    String serializerName() default "";

    String expireInMillis() default "";

    String cacheNullValue() default "";

    String nullValueExpireInMillis() default "";
}
