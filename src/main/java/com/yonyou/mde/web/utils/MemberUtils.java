package com.yonyou.mde.web.utils;

import cn.hutool.extra.pinyin.PinyinUtil;
import com.yonyou.mde.web.model.Member;

public class MemberUtils {
    public static String getCodeDetail(Member member) {//凭借空格字符串
        int g = member.getGeneration();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < g; i++) {
            sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        }
        if (member.getDatatype() >= 10) {
            sb.append("▼");
        }
        sb.append(member.getCode());
        return sb.toString();
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
