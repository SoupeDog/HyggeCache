package org.xavier.hyggecache.serializer;

import org.xavier.hyggecache.enums.SerializerPolicyEnum;
import org.xavier.hyggecache.keeper.TypeInfoKeeper;

/**
 * 描述信息：<br/>
 * 序列化工具基类
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public abstract class BaseSerializer<T> {
    public static String typeInfoKey;

    public BaseSerializer() {
        typeInfoKey = getTypeInfoKey();
    }

    /**
     * 用于初始化 typeInfoKey
     *
     * @return 该序列化对象对应的 TypeInfoKeeper name
     */
    public abstract String getTypeInfoKey();

    /**
     * 标识自身序列化类型
     */
    protected SerializerPolicyEnum type;

    /**
     * 序列化目标类型信息管理对象
     */
    protected TypeInfoKeeper<T> typeInfoKeeper;

    /**
     * 将目标对象序列化
     *
     * @param obj 目标对象
     * @return 序列化结果的字节数组形式
     */
    public abstract byte[] serialize(Object obj);

    /**
     * 复杂类型反序列化
     *
     * @param bytes       反序列化目标内容
     * @param typeInfoKey 反序列化目标特殊类型唯一标识
     * @return 反序列化结果
     */
    public abstract Object deserialize(byte[] bytes, String typeInfoKey);

    /**
     * 简单类型反序列化
     *
     * @param bytes             反序列化目标内容
     * @param methodReturnClass 反序列化目标方法返回值类型
     * @return 反序列化结果
     */
    public abstract Object deserialize(byte[] bytes, Class<?> methodReturnClass);

    public SerializerPolicyEnum getType() {
        return type;
    }

    public TypeInfoKeeper<T> getTypeInfoKeeper() {
        return typeInfoKeeper;
    }

    public void setTypeInfoKeeper(TypeInfoKeeper<T> typeInfoKeeper) {
        this.typeInfoKeeper = typeInfoKeeper;
    }

}
