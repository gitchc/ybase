package com.yonyou.mde.web.utils.classloder;

public class HotSwapClassLoader extends ClassLoader{
    //使用指定的父类加载器创建一个新的类加载器进行委派
    public HotSwapClassLoader(){
        super(HotSwapClassLoader.class.getClassLoader());
    }
    public Class loadByte(byte[] classByte){
        return defineClass(null,classByte,0,classByte.length);
    }
}