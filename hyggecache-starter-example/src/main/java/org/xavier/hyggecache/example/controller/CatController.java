package org.xavier.hyggecache.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.xavier.hyggecache.example.model.Cat;
import org.xavier.hyggecache.example.service.CatService;

import java.util.HashMap;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.16
 * @since Jdk 1.8
 */
@RestController
public class CatController {
    @Autowired
    CatService catService;

    @GetMapping("cat/{catId}")
    public Object getCatByCatId(@PathVariable("catId") String catId) {
        return catService.getCatById(catId);
    }
    @GetMapping("cat/customType/{size}")
    public Object getCatCustomType(@PathVariable("size") Integer size) {
        return catService.customType(size);
    }

    @GetMapping("cat/update/{catId}")
    public Object updateCat(@PathVariable("catId") String catId) {
        catService.updateCatById(catId, new HashMap());
        return "已修改";
    }

    @GetMapping("cat/remove/{catId}")
    public Object removeCat(@PathVariable("catId") String catId) {
        Cat cat = new Cat();
        cat.setCatId(catId);
        catService.saveCat(cat);
        return "已移除";
    }
}
