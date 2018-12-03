package org.xavier.hyggecache.helper;

import org.xavier.hyggecache.config.CacheOperatorConfig;

/**
 * 描述信息：<br/>
 * AopCacheHelper
 *
 * @author Xavier
 * @version 1.0
 * @date 2018/11/18
 * @since Jdk 1.8
 */
public interface AopCacheHelper {

    /**
     * 初始化 cacheOperatorConfig
     *
     * @param cacheOperatorConfig CacheOperator 正常运转所必须包含的参数
     */
    void setCacheOperatorConfig(CacheOperatorConfig cacheOperatorConfig);
}
