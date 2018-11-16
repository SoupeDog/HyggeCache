package org.xavier.hyggecache.example.service;


import org.xavier.hyggecache.example.model.Cat;

import java.util.Map;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
public interface CatService {
    Object customType(Integer size);

    Cat getCatById(String catId);

    Boolean saveCat(Cat cat);

    Boolean updateCatById(String catId, Map data);
}
