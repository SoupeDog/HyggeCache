package org.xavier.hyggecache.keeper;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.xavier.hyggecache.config.HotKeyConfig;
import org.xavier.hyggecache.keeper.bo.HotKeyStatistics;
import org.xavier.hyggecache.operator.BaseCacheOperator;
import org.xavier.hyggecache.utils.SortHelper;
import org.xavier.hyggecache.utils.bo.CacheKeySortItem;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 描述信息：<br/>
 * 缓存 Key 的管理对象,同时也负责热点 key 的维护
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.28
 * @since Jdk 1.8
 */
public class KeyKeeper<K> implements Runnable {
    /**
     * 最后一次检测开始时间戳 UTC 毫秒级
     */
    private Long lastCheckStartTs;
    /**
     * 热点 key 检测间隔(秒)
     */
    private Integer rescueDelta_Second;
    /**
     * 64 倍热点 Key 检测间隔(秒)
     */
    private Integer rescueDeltaX64_Second;
    /**
     * 32 倍热点 Key 检测间隔(秒)
     */
    private Integer rescueDeltaX32;
    /**
     * 缓存落地操作对象
     */
    private BaseCacheOperator operator;

    /**
     * 热点 key 配置信息
     */
    private HotKeyConfig hotKeyConfig;

    /**
     * 用于给外部展现数据的缓存(循环覆盖，仅保留最后一次的统计)
     */
    private HotKeyStatistics<K> hotKeyStatistics;

    private Random random = new Random();
    private SortHelper<CacheKeySortItem<K>> sortHelper = new SortHelper();
    private volatile ConcurrentHashMap<K, AtomicInteger> keyMap;

    /**
     * 热点 key 拯救线程池
     */
    private ScheduledThreadPoolExecutor scheduledExecutorService;

    public KeyKeeper(HotKeyConfig hotKeyConfig, BaseCacheOperator operator) {
        lastCheckStartTs = System.currentTimeMillis();
        this.hotKeyConfig = hotKeyConfig;
        this.operator = operator;
        keyMap = new ConcurrentHashMap(hotKeyConfig.getDefaultSize(), hotKeyConfig.getLoadFactor());
        rescueDelta_Second = Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(hotKeyConfig.getHotKeyRescueDeltaInMillis())).intValue();
        rescueDeltaX64_Second = rescueDelta_Second << 10;
        rescueDeltaX32 = rescueDelta_Second << 5;

        if (hotKeyConfig.getHotKeyCheckActive()) {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = (thread, throwable) -> {
                // 进行自定义的异常捕获逻辑
                System.err.println(thread.getName() + " - " + throwable.getMessage());
                throwable.printStackTrace();
            };
            BasicThreadFactory threadFactory = new BasicThreadFactory.Builder().namingPattern("HotKeyCheck").uncaughtExceptionHandler(uncaughtExceptionHandler).daemon(true).build();
            scheduledExecutorService = new ScheduledThreadPoolExecutor(1, threadFactory);
            scheduledExecutorService.setMaximumPoolSize(1);
            scheduledExecutorService.scheduleWithFixedDelay(this, rescueDelta_Second, rescueDelta_Second, TimeUnit.SECONDS);
        }
    }

    public void count(byte[] key) {
        AtomicInteger counter = keyMap.get((K) hotKeyConfig.getKeyFormat().apply(key));
        if (counter != null) {
            counter.addAndGet(1);
        } else {
            counter = new AtomicInteger(1);
            counter = keyMap.putIfAbsent((K) hotKeyConfig.getKeyFormat().apply(key), counter);
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
    public ArrayList<CacheKeySortItem<K>> getTopK(Boolean isOrdered, Integer k) {
        LinkedList<CacheKeySortItem<K>> sortList = null;
        int topKIndex = sortHelper.getIndexOfTopK(sortList, k);
        ArrayList<CacheKeySortItem<K>> result = getCacheKeySortItems(k, sortList, topKIndex);
        if (isOrdered) {
            // TODO 需要将返回结果变为有序
        }
        return result;
    }

    /**
     * 返回无序集合中的最大的前 k 个数组成的集合
     *
     * @param isOrdered 返回的 top k 是否是有序的
     * @param k         前多少个最大数
     * @return 无序集合中的最大的前 k 个数组成的集合
     */
    public ArrayList<CacheKeySortItem<K>> getTopK(Boolean isOrdered, Integer k, List<CacheKeySortItem<K>> sortList) {
        int topKIndex = sortHelper.getIndexOfTopK(sortList, k);
        ArrayList<CacheKeySortItem<K>> result = getCacheKeySortItems(k, sortList, topKIndex);
        if (isOrdered) {
            // TODO 需要将返回结果变为有序
        }
        return result;
    }

    private ArrayList<CacheKeySortItem<K>> getCacheKeySortItems(Integer k, List<CacheKeySortItem<K>> sortList, int topKIndex) {
        ArrayList<CacheKeySortItem<K>> result;
        if (topKIndex > 0) {
            result = new ArrayList(k);
            for (int i = 0; i <= topKIndex; i++) {
                result.add(sortList.get(i));
            }
        } else {
            if (sortList != null) {
                result = new ArrayList(sortList);
            } else {
                result = new ArrayList();
            }
        }
        return result;
    }

    public void reset() {
        Integer realCheckDelta_Second = Double.valueOf((System.currentTimeMillis() - lastCheckStartTs) / 1000).intValue();
        List<CacheKeySortItem<K>> sortList = snapshot();
        keyMap = new ConcurrentHashMap(hotKeyConfig.getDefaultSize());
        lastCheckStartTs = System.currentTimeMillis();
        ArrayList<CacheKeySortItem<K>> rescueList = getTopK(false, hotKeyConfig.getHotKeyRescueMaxSize(), sortList);

        for (CacheKeySortItem<K> item : rescueList) {
            if (item.getCount() / realCheckDelta_Second > hotKeyConfig.getHotKeyMinQPS()) {
                // 热点 key 挽救
                resetTTL(item.getTargetObj(), operator);
            }
        }
        hotKeyStatistics = new HotKeyStatistics(lastCheckStartTs, System.currentTimeMillis(), rescueList, hotKeyConfig.getHotKeyRescueMaxSize());
    }

    private Long resetTTL(K cacheKeyWithPrefix, BaseCacheOperator operator) {
        byte[] keyByteArrayVal = (byte[]) hotKeyConfig.getKeyAntiFormat().apply(cacheKeyWithPrefix);
        Long ttl = operator.getTTL(keyByteArrayVal);
        // 默认 null 标示不参与热点key 拯救,延长 null 标示很可能造成数据不一致(假定的延长期间该查询结果有值了呢？)
        if (ttl < rescueDeltaX32 && ttl > hotKeyConfig.getNullValueExpireInMillis()) {
            // 产生一些随机值，将挽救峰值均摊到64~128倍挽救时间间隔内
            Integer newExpire = rescueDeltaX64_Second + random.nextInt(65) * rescueDelta_Second;
            return operator.resetTTL(keyByteArrayVal, newExpire);
        }
        return null;
    }

    /**
     * 快照瞬时 key 计数器
     *
     * @return keyMap 等待排序的 key 集合
     */
    public ArrayList<CacheKeySortItem<K>> snapshot() {
        ArrayList<CacheKeySortItem<K>> sortTemp = new ArrayList();
        for (Map.Entry<K, AtomicInteger> entry : keyMap.entrySet()) {
            sortTemp.add(new CacheKeySortItem(entry.getKey(), entry.getValue().get()));
        }
        return sortTemp;
    }

    @Override
    public void run() {
        try {
            reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HotKeyStatistics<K> getHotKeyStatistics() {
        return hotKeyStatistics;
    }
}
