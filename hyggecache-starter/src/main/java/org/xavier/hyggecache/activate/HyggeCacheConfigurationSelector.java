package org.xavier.hyggecache.activate;

import org.springframework.context.annotation.*;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.xavier.hyggecache.annotation.EnableHyggeCache;
import org.xavier.hyggecache.enums.SerializerPolicySwitchEnum;

/**
 * 描述信息：<br/>
 * HyggeCache 配置激活选择器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class HyggeCacheConfigurationSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes enableHyggeCacheAttributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableHyggeCache.class.getName(), false));
        String[] result = new String[0];
        if (enableHyggeCacheAttributes != null) {
            SerializerPolicySwitchEnum serializerPolicySwitchEnum = enableHyggeCacheAttributes.getEnum("serializerAutoInit");
            switch (serializerPolicySwitchEnum) {
                case JACKSON:
                    result = new String[]{AutoInitJacson.class.getName()};
                    break;
                default:
                    System.err.println("Unsupported SerializerPolicySwitchEnum: " + serializerPolicySwitchEnum.getDescription());
            }
        }
        return result;
    }
}
