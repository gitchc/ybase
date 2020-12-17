package com.yonyou.mde.web.Script.Utils;

import cn.hutool.core.util.StrUtil;
import com.yonyou.mde.web.Script.IScript;
import com.yonyou.mde.web.model.Completer;
import sun.plugin.javascript.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
/*
* 脚本的关键字
* */
public class KeyWord {
    private static List<Completer> keywords;

    public static List<Completer> getKeyWords() {
        if (keywords == null) {
            keywords = new ArrayList<>();
        }else {
            return keywords;
        }
        Method[] jScriptMethods = ReflectUtil.getJScriptMethods(IScript.class);
        for (Method jScriptMethod : jScriptMethods) {
            String caption = jScriptMethod.getName();
            Completer completer = new Completer("", "");
            String value = caption + "(";
            Class<?>[] parameterTypes = jScriptMethod.getParameterTypes();
            Parameter[] parameters = jScriptMethod.getParameters();
            int max = parameterTypes.length;
            for (int i = 0; i < max; i++) {
                if (i > 0) {
                    value += ", ";
                }
                String[] splits = parameterTypes[i].getName().split("\\.");
                value += splits[splits.length - 1];
                value += " ";
                value += parameters[i].getName();
            }
            value += ")";
            keywords.add(new Completer(caption, value));
        }
        return keywords;
    }

    public static void main(String[] args) {
        for (Completer keyWord : getKeyWords()) {
            System.out.println(keyWord.getValue());
        }
    }

}
