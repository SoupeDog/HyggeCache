package org.xavier.hyggecache.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xavier.hyggecache.operator.redis.RedisCacheOperator;

/**
 * 描述信息：<br/>
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.12.05
 * @since Jdk 1.8
 */
@RestController
public class StatisticsController {
    @Autowired
    RedisCacheOperator redisCacheOperator;

    @GetMapping("/")
    public Object statistics() {
        return redisCacheOperator.getKeyKeeper().getHotKeyStatistics();
    }

}
