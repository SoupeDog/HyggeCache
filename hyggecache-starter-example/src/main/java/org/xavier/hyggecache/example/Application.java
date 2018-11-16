package org.xavier.hyggecache.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.xavier.hyggecache.annotation.EnableHyggeCache;
import org.xavier.hyggecache.enums.SerializerPolicySwitchEnum;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
@SpringBootApplication
@EnableHyggeCache(serializerAutoInit = SerializerPolicySwitchEnum.JACKSON, basePackages = {"org.xavier.hyggecache.example"})
public class Application {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
    }
}
