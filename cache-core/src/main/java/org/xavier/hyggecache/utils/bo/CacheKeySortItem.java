package org.xavier.hyggecache.utils.bo;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.30
 * @since Jdk 1.8
 */
public class CacheKeySortItem<K> extends BaseSortItem<K> {
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

    @Override
    public String toString() {
        return count + "";
    }
}
