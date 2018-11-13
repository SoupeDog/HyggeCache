package org.xavier.hyggecache.serializer;

import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;

import java.util.concurrent.ConcurrentHashMap;

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
    private final SerializerPolicyEnum type;
    private final ConcurrentHashMap<String, T> typeInfoMap = new ConcurrentHashMap();

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
