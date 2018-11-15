package org.xavier.hyggecache.config;

/**
 * 描述信息：<br/>
 * 序列化操作类必须包含的配置项
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class SerializerConfig {
    /**
     * 复杂序列化类型的唯一标识
     */
    private String typeInfoName;

    /**
     * 方法的返回值类型
     */
    private Class returnClass;

    public String getTypeInfoName() {
        return typeInfoName;
    }

    public void setTypeInfoName(String typeInfoName) {
        this.typeInfoName = typeInfoName;
    }

    public Class getReturnClass() {
        return returnClass;
    }

    public void setReturnClass(Class returnClass) {
        this.returnClass = returnClass;
    }
}
