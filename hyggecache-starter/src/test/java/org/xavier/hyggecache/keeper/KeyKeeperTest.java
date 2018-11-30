package org.xavier.hyggecache.keeper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Assert;
import org.junit.Test;
import org.xavier.hyggecache.utils.bo.CacheKeySortItem;

import java.util.ArrayList;

/**
 * 描述信息：<br/>
 * 简略测测排序算法
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.30
 * @since Jdk 1.8
 */
public class KeyKeeperTest {
    @Test
    public void sort_Test() throws JsonProcessingException {
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
        // 获取 top 3 的三个元素
        ArrayList<CacheKeySortItem<String>> rsult = keyKeeper.getOrderedTopK(3);
        ObjectMapper objectMapper = new ObjectMapper();
        // 开启缩进
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        System.out.println(objectMapper.writeValueAsString(rsult));
        // 实际上是无序的，这里是巧合
        Assert.assertEquals(999, rsult.get(0).getCount());
        Assert.assertEquals(888, rsult.get(1).getCount());
        Assert.assertEquals(777, rsult.get(2).getCount());
    }


}