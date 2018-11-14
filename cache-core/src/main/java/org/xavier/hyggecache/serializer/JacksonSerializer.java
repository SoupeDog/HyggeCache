package org.xavier.hyggecache.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.exception.HyggeCacheRuntimeException;

import java.io.IOException;

/**
 * 描述信息：<br/>
 * Jackson 序列化工具
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.13
 * @since Jdk 1.8
 */
public class JacksonSerializer extends BaseSerializer<TypeReference> {
    /**
     * 自动构造的默认 BeanName
     */
    public static final String DEFAULT_NAME = "JacksonSerializer_Default";
    private ObjectMapper mapper;

    public JacksonSerializer(ObjectMapper mapper) {
        super();
        type = SerializerPolicyEnum.JACKSON;
        this.mapper = mapper;
    }

    @Override
    public String getTypeInfoKey() {
        return "TypeInfoKeeper_Jackson";
    }

    @Override
    public byte[] serialize(Object obj) {
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            if (obj != null) {
                throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "Fail to serialize " + obj.getClass(), e);
            } else {
                throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "Fail to serialize ,target object was null.", e);
            }
        }
    }

    @Override
    public Object deserialize(byte[] bytes, String typeInfoKey) {
        TypeReference typeReference = typeInfoKeeper.queryTypeReferenceByName(typeInfoKey);
        if (typeReference == null) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE, "TypeInfo that key=" + typeInfoKey + " was not found.");
        }
        try {
            return mapper.readValue(bytes, typeReference);
        } catch (IOException e) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE,
                    String.format("Fail to deserialize,typeInfo-key : [%s] .", typeInfoKey), e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> methodReturnClass) {
        try {
            return mapper.readValue(bytes, methodReturnClass);
        } catch (IOException e) {
            throw new HyggeCacheRuntimeException(HyggeCacheExceptionEnum.SERIALIZE,
                    String.format("Fail to deserialize,target class : [%s] .", methodReturnClass.getName()), e);
        }
    }

    public ObjectMapper getMapper() {
        return mapper;
    }

    public void setMapper(ObjectMapper mapper) {
        this.mapper = mapper;
    }
}
