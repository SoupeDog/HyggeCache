package org.xavier.hyggecache.annotation;

import java.lang.annotation.*;

/**
 * 描述信息：<br/>
 * 缓存清除注解
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CacheInvalidate {
    String prefix() default "";

    String key();

    String cacheNullValue() default "";

    String nullValueExpireInMillis() default "";
}
