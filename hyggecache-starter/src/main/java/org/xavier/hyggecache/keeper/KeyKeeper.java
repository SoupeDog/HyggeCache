package org.xavier.hyggecache.keeper;

import org.xavier.hyggecache.utils.SortHelper;
import org.xavier.hyggecache.utils.bo.BaseSortItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
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
    private SortHelper<CacheKeySortItem<K>> sortHelper = new SortHelper();
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
        LinkedList<CacheKeySortItem<K>> sortList = snapshot();
        Integer topKIndex = sortHelper.getIndexOfTopK(sortList, k);
        ArrayList<K> result = new ArrayList(k);
        for (int i = 0; i <= topKIndex; i++) {
            result.add(sortList.get(i).getTargetObj());
        }
        return result;
    }


    /**
     * 快照瞬时 key 计数器
     *
     * @return keyMap 等待排序的 key 集合
     */
    private LinkedList<CacheKeySortItem<K>> snapshot() {
        LinkedList<CacheKeySortItem<K>> sortTemp = new LinkedList();
        for (Map.Entry<K, AtomicInteger> entry : keyMap.entrySet()) {
            sortTemp.add(new CacheKeySortItem(entry.getKey(), entry.getValue().get()));
        }
        return sortTemp;
    }

    private class CacheKeySortItem<K> extends BaseSortItem<K> {
        private int count;

        public CacheKeySortItem(K key, int count) {
            this.count = count;
            this.targetObj = key;
        }

        @Override
        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
