package org.xavier.hyggecache.builder;

import org.springframework.context.ApplicationContext;
import org.xavier.hyggecache.config.GlobalConfig;
import org.xavier.hyggecache.helper.AopGetCacheHelper;
import org.xavier.hyggecache.keeper.PointcutKeeper;
import org.xavier.hyggecache.keeper.SerializerKeeper;

/**
 * 描述信息：<br/>
 * AOP 方式的缓存工具构造器
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.15
 * @since Jdk 1.8
 */
public class AopCacheHelperBuilder {
    private ApplicationContext applicationContext;
    private SerializerKeeper serializerKeeper;
    private PointcutKeeper pointcutKeeper;
    private GlobalConfig globalConfig;

    public AopCacheHelperBuilder(ApplicationContext applicationContext, SerializerKeeper serializerKeeper, PointcutKeeper pointcutKeeper, GlobalConfig globalConfig) {
        this.applicationContext = applicationContext;
        this.serializerKeeper = serializerKeeper;
        this.pointcutKeeper = pointcutKeeper;
        this.globalConfig = globalConfig;
    }

    public AopGetCacheHelper createGet(){

    }
}