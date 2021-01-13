package com.yonyou.mde.web.script.utils;

import com.yonyou.mde.web.core.ScriptException;

public class Assert {
    public Assert() {
    }

    public static void fail(Exception e) {
        fail(e.toString());
    }

    public static void fail(String msg) {
        throw new ScriptException("failure '" + msg + "'");
    }

    public static void assrt(boolean val) {
        if (!val) {
            throw new ScriptException("assert failed");
        }
    }

    /**
     * @description: 方法说明
     * @param: msg 提示信息
     * @param: val 判断逻辑
     * @return: void
     * @author Administrator
     *
     */
    public static void assrt(String msg, boolean val) {
        if (!val) {
            throw new ScriptException("assert '" + msg + "' failed");
        }
    }
}