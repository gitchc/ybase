package com.yonyou.mde.web.script.classloder;

import groovy.lang.GroovyClassLoader;
import org.codehaus.groovy.control.CompilationFailedException;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghch
 * @Date 2021/1/25 10:13
 */
public class GroovyCompiler {
    public static Object compile(String code) throws IllegalAccessException, InstantiationException {
        GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
        Class<?> clazz = null;
        try {
            clazz = groovyClassLoader.parseClass(code);
        } catch (CompilationFailedException e) {
            String[] error = e.getMessage().split("\r");
            String errorDetail = "";
            String[] ellines = error[1].split(":");
            errorDetail += "错误行号:" + (Integer.parseInt(ellines[1].trim()) - JavaClassUtils.importClass.length - 4);
            errorDetail += "<br>错误原因:" + ellines[2];
            errorDetail += "<br>错误代码:" + error[2];
            return errorDetail;
        }
        Object obj = clazz.newInstance();
        return obj;
    }
}
