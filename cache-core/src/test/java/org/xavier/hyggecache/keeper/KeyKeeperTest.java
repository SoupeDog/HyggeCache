package org.xavier.hyggecache.keeper;

import org.junit.Test;
import org.xavier.hyggecache.config.HotKeyConfig;
import org.xavier.hyggecache.utils.bo.CacheKeySortItem;

import java.io.*;
import java.util.*;

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
        keyConfig.setHotKeyMinQPS(9999);
        keyConfig.setHotKeyCheckActive(false);
        KeyKeeper keyKeeper = new KeyKeeper(keyConfig, null);
        Random random = new Random();
        Long start = System.currentTimeMillis();
        System.out.println("准备数据");
        for (int i = 0; i < 100000; i++) {
            Integer count = random.nextInt(1000) + 1;
            for (int j = 0; j < count; j++) {
                keyKeeper.count(new String("Key" + i).getBytes());
            }
        }
        System.out.println("准备耗时：" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ArrayList list = keyKeeper.snapshot();
        ArrayList list2 = (ArrayList) deepCopyList(list);

        System.out.println(System.currentTimeMillis() - start + "  (ms) 快照耗时");
        start = System.currentTimeMillis();
        List result = keyKeeper.getTopK(false, 1000, list);
        System.out.println(System.currentTimeMillis() - start + "  (ms) 排序耗时");
        Comparator comparator = (Comparator<CacheKeySortItem>) (o1, o2) -> o2.getCount() - o1.getCount();
        start = System.currentTimeMillis();
        list2.sort(comparator);
        System.out.println((System.currentTimeMillis() - start) + " (ms) 默认方法全排序");
        System.out.println(list2);
        System.out.println(result);
    }

    public static <T> List<T> deepCopyList(List<T> src) {
        List<T> dest = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            dest = (List<T>) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return dest;
    }
}