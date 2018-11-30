package org.xavier.hyggecache.keeper;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
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
        List<SortItem> sortList = snapshot();
        Integer topKIndex = getTopKIndex(sortList, 0, sortList.size() - 1, k);
        ArrayList<K> result = new ArrayList(k);
        for (int i = 0; i <= topKIndex; i++) {
            result.add(sortList.get(i).getKey());
        }
        return result;
    }


    /**
     * 快照瞬时 key 计数器
     *
     * @return keyMap 等待排序的 key
     */
    private LinkedList<SortItem> snapshot() {
        LinkedList<SortItem> sortTemp = new LinkedList();
        for (Map.Entry<K, AtomicInteger> entry : keyMap.entrySet()) {
            sortTemp.add(new SortItem(entry.getKey(), entry.getValue().get()));
        }
        return sortTemp;
    }

    private Integer getTopKIndex(List<SortItem> target, Integer low, Integer high, Integer k) {
        if (low.equals(high)) {
            return low;
        }
        Integer partitionIndex = partition(target, low, high);
        // 集合前半部分元素个数
        Integer headPartSize = partitionIndex - low;
        if (headPartSize >= k) {
            //求前半部分第k大
            return getTopKIndex(target, low, partitionIndex - 1, k);
        } else {
            //求后半部分第k-i大
            return getTopKIndex(target, partitionIndex, high, k - headPartSize);
        }
    }

    private Integer partition(List<SortItem> target, Integer low, Integer high) {
        Integer partitionCount = target.get(low).getCurrentCount();
        SortItem temp = target.get(low);
        Integer rowPartitionCountIndex = low;
        low++;
        while (high > low) {
            System.out.println(target);
            // 右侧往左寻找一个比 partitionCount 大的
            while (target.get(high).getCurrentCount() <= partitionCount && high > low) {
                high--;
            }
            // 左侧往右寻找一个比 partitionCount 小的
            while (target.get(low).getCurrentCount() >= partitionCount && high > low) {
                low++;
            }
            if (!low.equals(high)) {
                Collections.swap(target, low, high);
            }
        }
        target.remove((int) rowPartitionCountIndex);
        // rowPartitionCountIndex 一定会在 low 的左边，所以 rowPartitionCountIndex 对应的元素移除，右侧所有元素序号-1
        if (temp.getCurrentCount() > target.get(low - 1).getCurrentCount()) {
            target.add(low - 1, temp);
        } else {
            target.add(low, temp);
        }
        return low;
    }

    private class SortItem {
        private K key;
        private Integer currentCount;

        public SortItem(K key, Integer currentCount) {
            this.key = key;
            this.currentCount = currentCount;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public Integer getCurrentCount() {
            return currentCount;
        }

        public void setCurrentCount(Integer currentCount) {
            this.currentCount = currentCount;
        }

        @Override
        public String toString() {
            return currentCount.toString();
        }
    }

    public static void main(String[] args) {
        KeyKeeper<String> keyKeeper = new KeyKeeper(16, 0.75F);
        for (int i = 0; i < 888; i++) {
            keyKeeper.count("888");
        }
        for (int i = 0; i < 999; i++) {
            keyKeeper.count("999");
        }
        for (int i = 0; i < 99; i++) {
            keyKeeper.count("99");
        }
        for (int i = 0; i < 144; i++) {
            keyKeeper.count("144");
        }
        for (int i = 0; i < 61; i++) {
            keyKeeper.count("61");
        }
        for (int i = 0; i < 777; i++) {
            keyKeeper.count("777");
        }
        for (int i = 0; i < 9; i++) {
            keyKeeper.count("9");
        }
        for (int i = 0; i < 166; i++) {
            keyKeeper.count("166");
        }
        for (int i = 0; i < 38; i++) {
            keyKeeper.count("38");
        }

        ArrayList<String> r = keyKeeper.getOrderedTopK(4);
        System.out.println(r);

    }
}
