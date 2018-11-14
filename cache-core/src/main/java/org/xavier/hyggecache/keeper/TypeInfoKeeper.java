package org.xavier.hyggecache.keeper;

import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;
import org.xavier.hyggecache.serializer.TypeInfo;

import java.util.HashMap;

/**
 * 描述信息：<br/>
 * 复杂序列化类型存储对象
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public class TypeInfoKeeper<T> {
    /**
     * Jacson 自动构造的默认 BeanName
     */
    public static final String JACSON_DEFAULT_NAME = "JacksonTypeInfo_Default";
    /**
     * Jacson 用户覆盖的默认 BeanName
     */
    public static final String JACSON_DEFAULT_NAME_CUSTOM = "JacksonTypeInfo_Default_Custom";
    /**
     * Fastjson 自动构造的默认 BeanName
     */
    public static final String FASTJSON_DEFAULT_NAME = "FastJsonTypeInfo_Default";
    /**
     * Fastjson 用户覆盖的默认 BeanName
     */
    public static final String FASTJSON_DEFAULT_NAME_CUSTOM = "FastJsonTypeInfo_Default_Custom";

    private final SerializerPolicyEnum type;
    /**
     * 只初始化一次应该不存在并发问题 Key: TypeInfo 的唯一标识
     */
    private final HashMap<String, T> typeInfoMap = new HashMap();

    public TypeInfoKeeper(SerializerPolicyEnum type) {
        this.type = type;
    }

    /**
     * 根据 TypeReferenceName 获取 TypeReference
     */
    public T queryTypeReferenceByName(String typeReferenceName) {
        if (typeInfoMap.containsKey(typeReferenceName)) {
            return typeInfoMap.get(typeReferenceName);
        } else {
            return null;
        }
    }

    public void saveTypeReference(String name, TypeInfo<?> typeInfo) {
        switch (type) {
            case JACKSON:
                typeInfoMap.put(name, (T) typeInfo.jackson().reference());
                break;
            case FASTJSON:
                typeInfoMap.put(name, (T) typeInfo.fastjson().reference());
                break;
            default:
                throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "Current type: " + type.getDescription() + ", you should use TypeInfoKeeper.saveCustomerType().");
        }
    }

    public void saveCustomerType(String name, T temp) {
        if (type == SerializerPolicyEnum.CUSTOM) {
            typeInfoMap.put(name, temp);
        } else {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "Current type: " + type.getDescription() + ", you should use TypeInfoKeeper.saveTypeReference().");
        }
    }
}
