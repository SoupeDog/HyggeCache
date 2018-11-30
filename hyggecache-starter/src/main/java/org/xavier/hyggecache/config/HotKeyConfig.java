package org.xavier.hyggecache.config;

/**
 * 描述信息：<br/>
 * 热点 Key 配置文件
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.30
 * @since Jdk 1.8
 */
public class HotKeyConfig {
    /**
     * 热点 key 最小 QPS
     */
    private Integer hotKeyMinQPS;
    /**
     * 热点 key 刷新检测间隔
     */
    private Long hotKeyRescueDelta;
    /**
     * 单次检测热点 key 最大刷新数量
     */
    private Integer hotKeyRescueMaxSize;

    public Integer getHotKeyMinQPS() {
        return hotKeyMinQPS;
    }

    public void setHotKeyMinQPS(Integer hotKeyMinQPS) {
        this.hotKeyMinQPS = hotKeyMinQPS;
    }

    public Long getHotKeyRescueDelta() {
        return hotKeyRescueDelta;
    }

    public void setHotKeyRescueDelta(Long hotKeyRescueDelta) {
        this.hotKeyRescueDelta = hotKeyRescueDelta;
    }

    public Integer getHotKeyRescueMaxSize() {
        return hotKeyRescueMaxSize;
    }

    public void setHotKeyRescueMaxSize(Integer hotKeyRescueMaxSize) {
        this.hotKeyRescueMaxSize = hotKeyRescueMaxSize;
    }
}
