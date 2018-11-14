package org.xavier.hyggecache.keeper;

import org.xavier.hyggecache.serializer.BaseSerializer;

import java.util.HashMap;

/**
 * 描述信息：<br/>
 * 序列化工具存储对象
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class SerializerKeeper {
    /**
     * 只初始化一次应该不存在并发问题, Key：Serializer 的 BeanName
     */
    private final HashMap<String, BaseSerializer> serializerMap;

    public SerializerKeeper() {
        serializerMap = new HashMap();
    }

    public void saveSerializer(String serializerName, BaseSerializer baseSerializer) {
        if (serializerMap.containsKey(serializerName)) {
            System.err.println("SerializerKeeper fail to add serializer: duplicate key-[" + serializerName + "]");
        } else {
            serializerMap.put(serializerName, baseSerializer);
        }
    }

    public BaseSerializer querySerializerByBeanName(String beanName) {
        return serializerMap.get(beanName);
    }

    public Integer size() {
        return serializerMap.size();
    }
}