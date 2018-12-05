package org.xavier.hyggecache.utils.bo;


import java.io.Serializable;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.30
 * @since Jdk 1.8
 */
public class CacheKeySortItem<K> extends BaseSortItem<K> implements Serializable {
    private static final long SERIAL_VERSION_UID = -4359400292221783689L;
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
