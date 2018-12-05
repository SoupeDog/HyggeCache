package org.xavier.hyggecache.keeper.bo;

import java.util.List;

import org.xavier.hyggecache.utils.bo.CacheKeySortItem;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.12.05
 * @since Jdk 1.8
 */
public class HotKeyStatistics<K> {
    /**
     * 起始时间
     */
    private Long startTs;
    /**
     * 结束时间
     */
    private Long endTs;
    /**
     * 头部元素
     */
    private List<CacheKeySortItem<K>> topData;

    /**
     * 热点 key 最大保留挽救数量
     */
    private Integer hotKeyRescueMaxSize;

    public HotKeyStatistics(Long startTs, Long endTs, List<CacheKeySortItem<K>> topData, Integer hotKeyRescueMaxSize) {
        this.startTs = startTs;
        this.endTs = endTs;
        this.topData = topData;
        this.hotKeyRescueMaxSize = hotKeyRescueMaxSize;
    }

    public Long getStartTs() {
        return startTs;
    }

    public void setStartTs(Long startTs) {
        this.startTs = startTs;
    }

    public Long getEndTs() {
        return endTs;
    }

    public void setEndTs(Long endTs) {
        this.endTs = endTs;
    }

    public List<CacheKeySortItem<K>> getTopData() {
        return topData;
    }

    public void setTopData(List<CacheKeySortItem<K>> topData) {
        this.topData = topData;
    }

    public Integer getHotKeyRescueMaxSize() {
        return hotKeyRescueMaxSize;
    }

    public void setHotKeyRescueMaxSize(Integer hotKeyRescueMaxSize) {
        this.hotKeyRescueMaxSize = hotKeyRescueMaxSize;
    }
}
