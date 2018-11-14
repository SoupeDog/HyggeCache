package org.xavier.hyggecache.example.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.keeper.TypeInfoKeeper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
@Configuration
public class Config {

    @Bean(TypeInfoKeeper.JACSON_DEFAULT_NAME_CUSTOM)
    public TypeInfoKeeper typeInfoKeeper() {
        TypeInfoKeeper<TypeReference> typeInfoKeeper = new TypeInfoKeeper(SerializerPolicyEnum.JACKSON);
        typeInfoKeeper.saveTypeReference("customType", new org.xavier.hyggecache.serializer.TypeInfo<ArrayList<HashMap<String, ArrayList<Integer>>>>(SerializerPolicyEnum.JACKSON) {
        });
        return typeInfoKeeper;
    }

    @Bean("another")
    public TypeInfoKeeper typeInfoKeeper2() {
        TypeInfoKeeper<TypeReference> typeInfoKeeper = new TypeInfoKeeper(SerializerPolicyEnum.JACKSON);
        typeInfoKeeper.saveTypeReference("anotherCustomType", new org.xavier.hyggecache.serializer.TypeInfo<ArrayList<HashMap<String, ArrayList<Integer>>>>(SerializerPolicyEnum.JACKSON) {
        });
        return typeInfoKeeper;
    }
}
