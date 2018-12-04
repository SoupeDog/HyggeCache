package org.xavier.hyggecache.config;

import java.util.function.Function;

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
    public static final String HOTKEY_CONFIG_BEAN_NAME = "default_HotKeyConfig";
    public static final String HOTKEY_CONFIG_BEAN_NAME_CUSTOM = "default_HotKeyConfig_CUSTOM";

    /**
     * 默认的 key 存储数量，设置一个合理值减少重建 Hash 的次数
     */
    private Integer defaultSize;
    /**
     * key 存储的负载因子
     */
    private Float loadFactor;
    /**
     * 开启热点 key 检测的开关
     */
    private Boolean isHotKeyCheckActive;

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
    /**
     * key 格式化函数，转换成 Map 能做 key 的值
     */
    private Function keyFormat = (key) -> new String((byte[]) key);
    /**
     * key 格式化函数，转换成 Map 能做 key 的值的逆向操作
     */
    private Function keyAntiFormat = (keyString) -> ((String) keyString).getBytes();

    public Integer getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(Integer defaultSize) {
        this.defaultSize = defaultSize;
    }

    public Float getLoadFactor() {
        return loadFactor;
    }

    public void setLoadFactor(Float loadFactor) {
        this.loadFactor = loadFactor;
    }

    public Boolean getHotKeyCheckActive() {
        return isHotKeyCheckActive;
    }

    public void setHotKeyCheckActive(Boolean hotKeyCheckActive) {
        isHotKeyCheckActive = hotKeyCheckActive;
    }

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

    public Function getKeyFormat() {
        return keyFormat;
    }

    public void setKeyFormat(Function keyFormat) {
        this.keyFormat = keyFormat;
    }

    public Function getKeyAntiFormat() {
        return keyAntiFormat;
    }

    public void setKeyAntiFormat(Function keyAntiFormat) {
        this.keyAntiFormat = keyAntiFormat;
    }
}
