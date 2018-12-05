package org.xavier.hyggecache.example.service;

import org.springframework.stereotype.Service;
import org.xavier.hyggecache.annotation.CacheInvalidate;
import org.xavier.hyggecache.annotation.CacheUpdate;
import org.xavier.hyggecache.annotation.Cacheable;
import org.xavier.hyggecache.annotation.CachedConfig;
import org.xavier.hyggecache.example.model.Cat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@Service("CatServiceImpl")
@CachedConfig(prefix = "cat:")
public class CatServiceImpl implements CatService {
    @Override
    @Cacheable(serializeTypeInfoKey = "customType", key = "#size+':customType'")
    public Object customType(Integer size) {
        System.out.println("真实调用");
        Map map = new HashMap();
        for (int i = 0; i < size; i++) {
            String catId = Math.ceil(Math.random() * 100) + "";
            Cat cat = new Cat();
            cat.setCatId(catId);
            cat.setCatName("Dog_" + catId);
            map.put(catId, cat);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(map);
        return arrayList;
    }

    @Override
    @Cacheable(key = "#catId")
    public Cat getCatById(String catId) {
        System.out.println("真实调用 CatServiceImpl");
        if (!catId.equals("1")) {
            Cat cat = new Cat();
            cat.setCatId(catId);
            cat.setCatName("Dog_" + catId);
            return cat;
        } else {
            return null;
        }
    }

    @Override
    @CacheInvalidate(key = "#cat.catId.toString()")
    public Boolean saveCat(Cat cat) {
        return true;
    }

    @Override
    @CacheUpdate(key = "#catId.toString()")
    public Boolean updateCatById(String catId, Map data) {
        return true;
    }
}
