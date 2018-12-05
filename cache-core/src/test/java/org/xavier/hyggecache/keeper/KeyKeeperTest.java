package org.xavier.hyggecache.keeper;

import org.junit.Test;
import org.xavier.hyggecache.config.HotKeyConfig;
import org.xavier.hyggecache.utils.bo.CacheKeySortItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.12.04
 * @since Jdk 1.8
 */
public class KeyKeeperTest {

    @Test
    public void 基准测试_Key快照And排序效率() {
        HotKeyConfig keyConfig = new HotKeyConfig();
        keyConfig.setDefaultSize(100000);
        keyConfig.setLoadFactor(0.75F);
        keyConfig.setHotKeyRescueDeltaInMillis(5000L);
        keyConfig.setHotKeyCheckActive(true);
        KeyKeeper keyKeeper = new KeyKeeper(keyConfig, null);
        Random random = new Random();
        for (int i = 0; i < 100000; i++) {
            Integer count = random.nextInt(1001);
            for (int j = 0; j < count; j++) {
                keyKeeper.count(Integer.valueOf(i).toString().getBytes());
            }
        }
        Long start = System.currentTimeMillis();
        ArrayList linkedList = keyKeeper.snapshot();
        System.out.println(System.currentTimeMillis() - start + "  (ms) 快照耗时");
        start = System.currentTimeMillis();
        List result = keyKeeper.getTopK(false, 1000, linkedList);
        System.out.println(System.currentTimeMillis() - start + "  (ms) 排序耗时");
        Comparator comparator = (Comparator<CacheKeySortItem>) (o1, o2) -> o2.getCount() - o1.getCount();
        start = System.currentTimeMillis();
        linkedList.sort(comparator);
        System.out.println((System.currentTimeMillis() - start)+" (ms) 默认方法全排序");
        System.out.println(linkedList);
        System.out.println(result);
    }

}