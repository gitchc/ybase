package com.yonyou.mde.web.script.utils;

import cn.hutool.core.util.ReflectUtil;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.web.model.types.KeywordType;
import com.yonyou.mde.web.model.vos.Completer;
import com.yonyou.mde.web.script.IScript;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/*
 * 脚本的关键字
 * */
public class KeyWordUtil {
    private static List<Completer> keywords;

    public static List<Completer> getKeyWords() {
        if (keywords == null) {
            keywords = new ArrayList<>();
        } else {
            return keywords;
        }
        getKeyWordByClass(keywords, IScript.class,777);
        getKeyWordByClass(keywords, Cube.class,778);
        getKeyWordByClass(keywords, Server.class,779);
        getMiultKeywords(keywords);
        return keywords;
    }

    //多维引擎接口关键字
    private static void getMiultKeywords(List<Completer> keywords) {
        keywords.add(new Completer("Cube", "Cube", KeywordType.MUILTMETHOD, 1000));
        keywords.add(new Completer("SliceResult", "SliceResult", KeywordType.MUILTMETHOD, 1000));
        keywords.add(new Completer("Server", "Server", KeywordType.MUILTMETHOD, 1000));
        keywords.add(new Completer("SliceResult", "SliceResult", KeywordType.MUILTMETHOD, 1000));
        keywords.add(new Completer("Row", "Row", KeywordType.MUILTMETHOD, 1000));
        keywords.add(new Completer("toTable", "toTable", KeywordType.MUILTMETHOD, 888));
        keywords.add(new Completer("getDouble", "getDouble", KeywordType.MUILTMETHOD, 888));
        keywords.add(new Completer("getText", "getText", KeywordType.MUILTMETHOD, 888));
        keywords.add(new Completer("Assert", "Assert.assrt(\"tips\",boolen);", KeywordType.MUILTMETHOD, 1000));

    }

    private static void getKeyWordByClass(List<Completer> keywords, Class clasts,int score) {
        Method[] jScriptMethods = ReflectUtil.getMethods(clasts);
        for (Method jScriptMethod : jScriptMethods) {
            String caption = jScriptMethod.getName();
            if (caption.equalsIgnoreCase("getClass")) {
                continue;
            }
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
            value += ");";
            keywords.add(new Completer(caption, value, KeywordType.METHOD, score));
        }
    }

    public static void main(String[] args) {
        for (Completer keyWord : getKeyWords()) {
            System.out.println(keyWord.getValue());
        }
    }

}
