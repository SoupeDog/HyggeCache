package org.xavier.hyggecache.example.config;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.example.model.Cat;
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
        typeInfoKeeper.saveTypeReference("customType", new TypeInfo<ArrayList<HashMap<String, Cat>>>() {
        });
        return typeInfoKeeper;
    }

    @Bean("another")
    public TypeInfoKeeper typeInfoKeeper2() {
        TypeInfoKeeper<TypeReference> typeInfoKeeper = new TypeInfoKeeper(SerializerPolicyEnum.JACKSON);
        typeInfoKeeper.saveTypeReference("anotherCustomType", new TypeInfo<ArrayList<HashMap<Integer, ArrayList<Integer>>>>() {
        });
        return typeInfoKeeper;
    }

    @Bean
    public RedisCacheOperator redisCacheOperator(JedisConnectionFactory jedisConnectionFactory) {
        RedisCacheOperator result = new RedisCacheOperator(jedisConnectionFactory);
        return result;
    }
}