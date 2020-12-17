package com.yonyou.mde.web.Script.Utils;

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

    public static void assrt(String msg, boolean val) {
        if (!val) {
            throw new ScriptException("assert '" + msg + "' failed");
        }
    }
}