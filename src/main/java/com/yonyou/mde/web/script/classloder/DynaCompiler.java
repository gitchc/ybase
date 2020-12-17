package com.yonyou.mde.web.script.classloder;

import javax.tools.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义编译模块
 */
public class DynaCompiler {
    private static Map<String, JavaFileObject> fileObjectMap = new ConcurrentHashMap<>();

    public static Object compile(String className, String source) throws IllegalAccessException, InstantiationException {
        DiagnosticCollector<JavaFileObject> compileCollector = new DiagnosticCollector<JavaFileObject>();
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaFileManager j = compiler.getStandardFileManager(compileCollector, null, null);
        JavaFileManager javaFileManager = new MyJavaFileManager(j);
        JavaFileObject sourceJavaFileObject = new MyJavaFileObject(className, source);
        Boolean result = compiler.getTask(null, javaFileManager, compileCollector, null, null, Arrays.asList(sourceJavaFileObject)).call();
        JavaFileObject byteJavaFileObject = fileObjectMap.get(className);
        if (result) {
            byte[] compiledBytes = ((MyJavaFileObject) byteJavaFileObject).getCompiledBytes();
            HotSwapClassLoader classLoader = new HotSwapClassLoader();
            Class cls = classLoader.loadByte(compiledBytes);
            return cls.newInstance();
        } else {
            return getError(compileCollector);
        }
    }

    private static String getError(DiagnosticCollector<JavaFileObject> diagnostics) {
        List<Diagnostic<? extends JavaFileObject>> diagnostics1 = diagnostics.getDiagnostics();
        Diagnostic diagnostic = diagnostics1.get(diagnostics1.size() - 1);
        long line = diagnostic.getLineNumber() - JavaClassUtils.importClass.length - 4;
        line = line > 0 ? line : 1;
        StringBuffer res = new StringBuffer();
        res.append("脚本行号:[" + (line) + "]\n<br>");
        res.append("错误原因:[" + diagnostic.getMessage(null) + "]\n<br>");
        return res.toString();
    }

    /**
     * 用于管理JavaFileObject
     */
    public static class MyJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {
        public MyJavaFileManager(JavaFileManager javaFileManager) {
            super(javaFileManager);

        }

        /**
         * 这个方法的作用是从指定位置读取Java程序并生成JavaFIleObject对象供编译器使用，本项目中用处不大
         */
        @Override
        public JavaFileObject getJavaFileForInput(Location location, String className, JavaFileObject.Kind kind) throws IOException {
            JavaFileObject javaFileObject = fileObjectMap.get(className);
            if (javaFileObject == null) {
                return super.getJavaFileForInput(location, className, kind);
            }
            return javaFileObject;
        }

        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            JavaFileObject javaFileObject = new MyJavaFileObject(className, kind);
            fileObjectMap.put(className, javaFileObject);
            return javaFileObject;
        }

    }

    /**
     * 用于封装表示源码与字节码的对象
     */
    public static class MyJavaFileObject extends SimpleJavaFileObject {
        private String source;
        private ByteArrayOutputStream byteArrayOutputStream;

        /**
         * 构造用于存放源代码的对象
         */
        public MyJavaFileObject(String name, String source) {
            super(URI.create("String:///" + name + Kind.SOURCE.extension), Kind.SOURCE);
            this.source = source;
        }

        /**
         * 构建用于存放字节码的JavaFileObject
         */
        public MyJavaFileObject(String name, Kind kind) {
            super(URI.create("String:///" + name + Kind.SOURCE.extension), kind);
        }

        /**
         * 获取源代码字符序列
         */
        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            if (source == null)
                throw new IllegalArgumentException("source == null");
            return source;
        }

        /**
         * 得到JavaFileObject中用于存放字节码的输出流
         */
        @Override
        public OutputStream openOutputStream() throws IOException {
            byteArrayOutputStream = new ByteArrayOutputStream();
            return byteArrayOutputStream;
        }

        /**
         * 将输出流的内容转化为byte数组
         */
        public byte[] getCompiledBytes() {
            return byteArrayOutputStream.toByteArray();
        }

    }
}