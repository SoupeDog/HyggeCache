package org.xavier.hyggecache.keeper;

import java.util.HashMap;

/**
 * 描述信息：<br/>
 * 复杂序列化类型存储对象 嵌套级别2
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class TypeInfoKeeperLv2 {
    /**
     * 只初始化一次应该不存在并发问题 Key：TypeInfoKeeper 的 BeanName
     */
    private final HashMap<String, TypeInfoKeeper> typeInfoKeeperMap = new HashMap();

    public void saveTypeInfoKeeper(String typeInfoKeeperName, TypeInfoKeeper typeInfoKeeper) {
        if (typeInfoKeeperMap.containsKey(typeInfoKeeperName)) {
            System.err.println("SerializerKeeper fail to add serializer: duplicate key-[" + typeInfoKeeperName + "]");
        } else {
            typeInfoKeeperMap.put(typeInfoKeeperName, typeInfoKeeper);
        }
    }

    public TypeInfoKeeper queryTypeInfoKeeperByBeanName(String typeInfoKeeperName) {
        return typeInfoKeeperMap.get(typeInfoKeeperName);
    }

    public Integer size() {
        return typeInfoKeeperMap.size();
    }
}
