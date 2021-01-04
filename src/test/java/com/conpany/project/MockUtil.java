package com.conpany.project;

import cn.hutool.extra.pinyin.PinyinUtil;

public class MockUtil {
    private static String regExp = "[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，\\- ／_、？\"]";
    private static final String yy = "yl";
    public static String getCode(String name) {
        name = name.replaceAll(yy, "用友");
        name = name.replaceAll(regExp, "II");
        return PinyinUtil.getPinyin(name, "");
    }

    public static String getName(String name) {
        name = name.replaceAll(yy, "用友");
        return name;
    }

    public static void main(String[] args) {
        System.out.println(getCode("集团"));
    }
}
