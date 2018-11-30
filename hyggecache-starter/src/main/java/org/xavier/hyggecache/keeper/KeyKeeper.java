package org.xavier.hyggecache.keeper;

import org.xavier.hyggecache.config.HotKeyConfig;
import org.xavier.hyggecache.utils.SortHelper;
import org.xavier.hyggecache.utils.bo.CacheKeySortItem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
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
    private HotKeyConfig hotKeyConfig;
    /**
     * 热点 key 检测间隔(秒)
     */
    private Integer rescueDelta_Second;
    private SortHelper<CacheKeySortItem<K>> sortHelper = new SortHelper();
    private Integer defaultSize;
    private volatile ConcurrentHashMap<K, AtomicInteger> keyMap;

    public KeyKeeper(HotKeyConfig hotKeyConfig, Integer defaultSize, Float loadFactor) {
        this.hotKeyConfig = hotKeyConfig;
        this.defaultSize = defaultSize;
        keyMap = new ConcurrentHashMap(this.defaultSize, loadFactor);
        rescueDelta_Second = Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(hotKeyConfig.getHotKeyRescueDelta())).intValue();
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
    public ArrayList<CacheKeySortItem<K>> getOrderedTopK(Integer k) {
        LinkedList<CacheKeySortItem<K>> sortList = snapshot();
        int topKIndex = sortHelper.getIndexOfTopK(sortList, k);
        ArrayList<CacheKeySortItem<K>> result = getCacheKeySortItems(k, sortList, topKIndex);
        return result;
    }

    /**
     * 返回无序集合中的最大的前 k 个数组成的集合
     *
     * @param k 前多少个最大数
     * @return 无序集合中的最大的前 k 个数组成的集合
     */
    public ArrayList<CacheKeySortItem<K>> getOrderedTopK(Integer k, LinkedList<CacheKeySortItem<K>> sortList) {
        int topKIndex = sortHelper.getIndexOfTopK(sortList, k);
        ArrayList<CacheKeySortItem<K>> result = getCacheKeySortItems(k, sortList, topKIndex);
        return result;
    }

    private ArrayList<CacheKeySortItem<K>> getCacheKeySortItems(Integer k, LinkedList<CacheKeySortItem<K>> sortList, int topKIndex) {
        ArrayList<CacheKeySortItem<K>> result;
        if (topKIndex > 0) {
            result = new ArrayList(k);
            for (int i = 0; i <= topKIndex; i++) {
                result.add(sortList.get(i));
            }
        } else {
            result = new ArrayList(sortList);
        }
        return result;
    }

    public void softReset() {
        LinkedList<CacheKeySortItem<K>> sortList = snapshot();
        ArrayList<CacheKeySortItem<K>> rescueList = getOrderedTopK(hotKeyConfig.getHotKeyRescueMaxSize(),sortList);
        for (CacheKeySortItem<K> item : rescueList) {
            if (item.getCount() / rescueDelta_Second > hotKeyConfig.getHotKeyMinQPS()) {
                // TODO 重置 key 有效期检测
            }
        }
        for(CacheKeySortItem<K> item : sortList){
           K key= item.getTargetObj();
           // 重置 QPS 计数器
           keyMap.get(key).set(0);
        }
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
}
