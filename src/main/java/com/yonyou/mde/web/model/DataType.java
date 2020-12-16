package com.yonyou.mde.web.model;

//数据类型
public class DataType {
    public static final int NUMBER = 0; //数据
    public static final int COIN = 1;//货币
    public static final int INT = 2;//整数
    public static final int DATE = 3;//日期戳
    public static final int TEXT = 4;//文本
    public static final int LIST = 5;//下拉列表
    public static final int AUTOROLLUP = 10;//手动上卷
    public static final int MAROLLUP = 11;//自动上卷

    public static final String[] typeStrs = new String[]{"数值", "货币", "整数", "时间戳", "文本", "下拉列表", "", "", "", "", "手动上卷", "自动上卷"};

    public static String getStr(int code) {
        return typeStrs[code];
    }

}
