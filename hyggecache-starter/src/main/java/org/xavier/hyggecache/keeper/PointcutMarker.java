package org.xavier.hyggecache.keeper;

import java.lang.reflect.Method;

/**
 * 描述信息：<br/>
 * PointcutKeeper 缓存信息所使用的 Key
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.14
 * @since Jdk 1.8
 */
public class PointcutMarker {
    /**
     * 反射得到的方法(可能来自于接口或接口实现类)
     */
    private final Method invokeMethod;

    /**
     * 被执行方法所在的实例对象
     */
    private final Class<?> targetClass;

    public PointcutMarker(Method invokeMethod, Class<?> targetClass) {
        this.invokeMethod = invokeMethod;
        this.targetClass = targetClass;
    }

    public Method getInvokeMethod() {
        return invokeMethod;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }


    /**
     * 这是个数学问题，此处算法优劣难观测到，因为影响的是碰撞率，而碰撞率不会导致程序无法运行，需要数学方法统计
     */
    @Override
    public int hashCode() {
        int seed = 31 * invokeMethod.hashCode();
        seed = 31 * (targetClass.hashCode() + seed);
        return seed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointcutMarker that = (PointcutMarker) o;
        return invokeMethod.equals(that.invokeMethod) && targetClass.equals(that.targetClass);
    }
}
