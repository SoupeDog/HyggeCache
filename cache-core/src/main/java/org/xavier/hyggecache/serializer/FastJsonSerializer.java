package org.xavier.hyggecache.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public class FastJsonSerializer extends BaseSerializer<TypeReference> {
    /**
     * 自动构造的默认 BeanName
     */
    public static final String DEFAULT_NAME = "FastJsonSerializer_Default";

    public FastJsonSerializer() {
        super();
        type = SerializerPolicyEnum.FASTJSON;
    }

    @Override
    public String getTypeInfoKey() {
        return "TypeInfoKeeper_FastJson";
    }

    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public Object deserialize(byte[] bytes, String typeInfoKey) {
        TypeReference typeReference = typeInfoKeeper.queryTypeReferenceByName(typeInfoKey);
        if (typeReference == null) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "TypeInfo that key=" + typeInfoKey + " was not found.");
        }
        return JSON.parseObject(new String(bytes), typeReference);
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> methodReturnClass) {
        return JSON.parseObject(bytes, methodReturnClass);
    }
}
