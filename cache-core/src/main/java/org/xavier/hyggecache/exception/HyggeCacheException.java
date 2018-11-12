package org.xavier.hyggecache.exception;

import org.xavier.hyggecache.enums.HyggeCacheExceptionEnum;

/**
 * 描述信息：<br/>
 * 中间件异常
 *
 * @author Xavier
 * @version 1.0
 * @date 2018.11.12
 * @since Jdk 1.8
 */
public class HyggeCacheException extends Exception {
    /**
     * 错误详细类型
     */
    private final HyggeCacheExceptionEnum type;

    public HyggeCacheException(HyggeCacheExceptionEnum type, String msg) {
        super(msg);
        this.type = type;
    }

    public HyggeCacheException(HyggeCacheExceptionEnum type, String msg, Throwable cause) {
        super(msg, cause);
        this.type = type;
    }
}