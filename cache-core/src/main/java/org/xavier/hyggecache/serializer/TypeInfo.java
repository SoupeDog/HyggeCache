package org.xavier.hyggecache.serializer;

import com.fasterxml.jackson.core.type.TypeReference;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 描述信息：<br/>
 * 框架内部通用 TypeReference
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public abstract class TypeInfo<T> {
    private final Type type;
    private final SerializerPolicyEnum serializerPolicyEnum;

    public TypeInfo(SerializerPolicyEnum serializerPolicyEnum) {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class<?>) { // sanity check, should never happen
            throw new IllegalArgumentException("Internal error: TypeReference constructed without actual type information");
        }
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        this.serializerPolicyEnum = serializerPolicyEnum;
    }

    public <T> JacksonTypeReference<T> jackson() {
        return new JacksonTypeReference(type);
    }

    public <T> FastjsonTypeReference<T> fastjson() {
        return new FastjsonTypeReference(type);
    }

    public static class JacksonTypeReference<T> {
        private static Field TYPE_FIELD;
        private static ConcurrentMap<Type, TypeReference> cachedTypes = new ConcurrentHashMap<>();
        private final com.fasterxml.jackson.core.type.TypeReference<T> reference;

        public JacksonTypeReference(Type type) {
            com.fasterxml.jackson.core.type.TypeReference<T> result = cachedTypes.get(type);
            if (result == null) {
                result = new com.fasterxml.jackson.core.type.TypeReference<T>() {
                };
                try {
                    TYPE_FIELD.set(result, type);
                } catch (Exception e) {
                    throw new RuntimeException("fail to create jackson TypeReference", e);
                }
                cachedTypes.putIfAbsent(type, result);
            }
            reference = result;
        }

        static {
            try {
                TYPE_FIELD = com.fasterxml.jackson.core.type.TypeReference.class.getDeclaredField("_type");
                TYPE_FIELD.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException("fail to create jackson TypeReference", e);
            }
        }

        public com.fasterxml.jackson.core.type.TypeReference<T> reference() {
            return reference;
        }
    }

    public static class FastjsonTypeReference<T> {
        private static Field TYPE_FIELD;
        private static ConcurrentMap<Type, com.alibaba.fastjson.TypeReference> cachedTypes = new ConcurrentHashMap<>();
        private final com.alibaba.fastjson.TypeReference<T> reference;

        static {
            try {
                TYPE_FIELD = com.alibaba.fastjson.TypeReference.class.getDeclaredField("type");
                TYPE_FIELD.setAccessible(true);
            } catch (Exception e) {
                throw new RuntimeException("fail to create fastjson TypeReference", e);
            }
        }

        public FastjsonTypeReference(Type type) {
            com.alibaba.fastjson.TypeReference<T> result = cachedTypes.get(type);
            if (result == null) {
                result = new com.alibaba.fastjson.TypeReference<T>() {
                };
                try {
                    TYPE_FIELD.set(result, type);
                } catch (Exception e) {
                    throw new RuntimeException("fail to create fastjson TypeReference", e);
                }
                cachedTypes.putIfAbsent(type, result);
            }
            reference = result;
        }

        public com.alibaba.fastjson.TypeReference<T> reference() {
            return reference;
        }
    }
}
