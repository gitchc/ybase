package com.yonyou.mde.web.model;

//状态类型
public class StatusType {

    public static final int NORMAL = 0; //正常
    public static final int READONLY = 1;//只读
    public static final int DISABLED = 2;//冻结
    public static final String[] typeStrs = new String[]{"正常", "只读", "冻结"};

    public static String getStr(int code) {
        return typeStrs[code];
    }
}
