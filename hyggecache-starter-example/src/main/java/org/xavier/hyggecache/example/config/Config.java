package org.xavier.hyggecache.example.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.keeper.TypeInfoKeeper;
import org.xavier.hyggecache.operator.redis.RedisCacheOperator;
import org.xavier.hyggecache.serializer.TypeInfo;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        typeInfoKeeper.saveTypeReference("customType", new TypeInfo<ArrayList<HashMap<String, ArrayList<Integer>>>>() {
        });
        return typeInfoKeeper;
    }

    @Bean("another")
    public TypeInfoKeeper typeInfoKeeper2() {
        TypeInfoKeeper<TypeReference> typeInfoKeeper = new TypeInfoKeeper(SerializerPolicyEnum.JACKSON);
        typeInfoKeeper.saveTypeReference("anotherCustomType", new TypeInfo<ArrayList<HashMap<String, ArrayList<Integer>>>>() {
        });
        return typeInfoKeeper;
    }

    @Bean
    public RedisCacheOperator redisCacheOperator(ApplicationContext applicationContext) {
        Map<String, JedisConnectionFactory> map = applicationContext.getBeansOfType(JedisConnectionFactory.class);
        System.out.println("JedisConnectionFactory:" + map.size());
        //        RedisCacheOperator result = new RedisCacheOperator((Jedis) jedisConnectionFactory.getConnection().getNativeConnection());
        RedisCacheOperator result = null;
        return result;
    }
}
