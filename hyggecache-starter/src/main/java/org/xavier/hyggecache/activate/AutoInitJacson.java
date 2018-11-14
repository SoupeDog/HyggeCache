package org.xavier.hyggecache.activate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.xavier.hyggecache.annotation.EnableHyggeCache;
import org.xavier.hyggecache.config.GlobalConfig;
import org.xavier.hyggecache.config.GlobalConfigProperties;
import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.keeper.SerializerKeeper;
import org.xavier.hyggecache.keeper.TypeInfoKeeper;
import org.xavier.hyggecache.keeper.TypeInfoKeeperLv2;
import org.xavier.hyggecache.serializer.BaseSerializer;
import org.xavier.hyggecache.serializer.JacksonSerializer;

import java.util.Map;

/**
 * 描述信息：<br/>
 * 初始化默认的 Jacson 序列化工具
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
@Configuration
@Import(GlobalConfigProperties.class)
public class AutoInitJacson implements ImportAware, ApplicationContextAware {
    private ApplicationContext applicationContext;
    private String[] basePackages;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importMetadata.getAnnotationAttributes(EnableHyggeCache.class.getName(), false));
        if (annotationAttributes == null) {
            throw new IllegalArgumentException(
                    "@EnableHyggeCache is not present on importing class " + importMetadata.getClassName());
        }
        basePackages = annotationAttributes.getStringArray("basePackages");
    }

    @Bean
    public GlobalConfig globalConfig(GlobalConfigProperties globalConfigProperties) {
        GlobalConfig result = new GlobalConfig(globalConfigProperties);
        return result;
    }

    @Bean(TypeInfoKeeper.JACSON_DEFAULT_NAME)
    public TypeInfoKeeper<TypeReference> typeInfoKeeper_Jackson() {
        TypeInfoKeeper<TypeReference> result = null;
        if (applicationContext.containsBeanDefinition(TypeInfoKeeper.JACSON_DEFAULT_NAME_CUSTOM)) {
            System.out.println("覆盖默认的 TypeInfoKeeper(Jacson)");
            result = applicationContext.getBean(TypeInfoKeeper.JACSON_DEFAULT_NAME_CUSTOM, TypeInfoKeeper.class);
        }
        if (result == null) {
            result = new TypeInfoKeeper(SerializerPolicyEnum.JACKSON);
        }
        return result;
    }

    @Bean
    public TypeInfoKeeperLv2 typeInfoKeeperLv2() {
        TypeInfoKeeperLv2 result = new TypeInfoKeeperLv2();
        Map<String, TypeInfoKeeper> typeInfoKeeperMap = applicationContext.getBeansOfType(TypeInfoKeeper.class);
        for (Map.Entry<String, TypeInfoKeeper> entry : typeInfoKeeperMap.entrySet()) {
            result.saveTypeInfoKeeper(entry.getKey(), entry.getValue());
        }
        System.out.println("TypeInfoKeeperLv2 size: " + result.size());
        return result;
    }

    @Bean(JacksonSerializer.DEFAULT_NAME)
    public JacksonSerializer jacksonSerializer(TypeInfoKeeperLv2 typeInfoKeeperLv2) {
        ObjectMapper mapper = new ObjectMapper();
        //反序列化出现多余属性时,选择忽略不抛出异常
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 开启允许数字以 0 开头
        mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        JacksonSerializer result = new JacksonSerializer(mapper);
        result.setTypeInfoKeeper(typeInfoKeeperLv2.queryTypeInfoKeeperByBeanName(TypeInfoKeeper.JACSON_DEFAULT_NAME));
        return result;
    }

    @Bean
    public SerializerKeeper serializerKeeper() {
        SerializerKeeper result = new SerializerKeeper();
        Map<String, BaseSerializer> baseValueSerializerMap = applicationContext.getBeansOfType(BaseSerializer.class);
        for (Map.Entry<String, BaseSerializer> entry : baseValueSerializerMap.entrySet()) {
            result.saveSerializer(entry.getKey(), entry.getValue());
        }
        System.out.println("SerializerKeeper size: " + result.size());
        return result;
    }
}