package org.xavier.hyggecache.keeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述信息：<br/>
 * 缓存 Key 的管理对象
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.28
 * @since Jdk 1.8
 */
public class KeyKeeper<K> {
    private Integer defaultSize;
    private volatile ConcurrentHashMap<K, AtomicInteger> keyMap;

    public KeyKeeper(Integer defaultSize, Float loadFactor) {
        this.defaultSize = defaultSize;
        keyMap = new ConcurrentHashMap(this.defaultSize, loadFactor);
    }

    public void count(K key) {
        AtomicInteger counter = keyMap.get(key);
        if (counter != null) {
            counter.addAndGet(1);
        } else {
            counter = new AtomicInteger(1);
            counter = keyMap.putIfAbsent(key, counter);
            if (counter != null) {
                counter.addAndGet(1);
            }
        }
    }

    /**
     * 返回无序集合中的最大的前 k 个数组成的集合
     *
     * @param k 前多少个最大数
     * @return 无序集合中的最大的前 k 个数组成的集合
     */
    public ArrayList<K> getOrderedTopK(Integer k) {

        ConcurrentHashMap<K, AtomicInteger> currentKeyMap=keyMap.toa;

        ArrayList<K> result = new ArrayList(this.defaultSize);
        return result;
    }
}
