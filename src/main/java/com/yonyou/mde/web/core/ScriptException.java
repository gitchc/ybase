package com.yonyou.mde.web.core;

/**
 * 脚本执行错误，该异常只做INFO级别的日志记录 @see WebMvcConfigurer
 */
public class ScriptException extends RuntimeException {
    public ScriptException() {
    }

    public ScriptException(String message) {
        super(message);
    }

    public ScriptException(String message, Throwable cause) {
        super(message, cause);
    }
}
