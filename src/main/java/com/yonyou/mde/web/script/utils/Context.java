package com.yonyou.mde.web.script.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by else @ 13-3-27 上午10:35
 */
public class Context {
    static ThreadLocal<Map<String, Object>> container = new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }
    };

    public static void setValue(String key, Object value) {
        container.get().put(key, value);
    }

    public static <T> T getValue(String key) {
        return (T) container.get().get(key);
    }

}
