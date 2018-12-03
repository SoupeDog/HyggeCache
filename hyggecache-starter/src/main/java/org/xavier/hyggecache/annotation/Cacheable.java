package org.xavier.hyggecache.annotation;


import java.lang.annotation.*;

/**
 * 描述信息：<br/>
 * 缓存查询注解
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cacheable {
    String prefix() default "";

    String serializerPolicy() default "";

    String serializerName() default "";

    String serializeTypeInfoKey() default "";

    String key();

    String expireInMillis() default "";

    String cacheNullValue() default "";

    String nullValueExpireInMillis() default "";

    String hotKeyCheck() default "";
}
