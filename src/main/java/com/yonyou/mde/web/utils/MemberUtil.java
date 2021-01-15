package com.yonyou.mde.web.utils;

import cn.hutool.core.util.StrUtil;
import com.yonyou.mde.web.model.Member;

import java.util.HashMap;
import java.util.Map;

public class MemberUtil {
    public static String getLevelName(Member member) {//凭借空格字符串
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

    public static String getDisplayName(Member member) {//凭借空格字符串
        if (member.getDatatype() >= 10) {
            return "▼".concat(member.getName());
        }
        return member.getName();
    }

    public static String toString(Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String format(String text, Object... replaceTexts) {
        return StrUtil.format("[{}]不存在", replaceTexts);
    }

    public static void main(String[] args) {
        System.out.println(format("[{}],不存在", "你好"));
        Map<String, String> values = new HashMap<>();
        values.put("nihao", "你好");
        System.out.println(StrUtil.format("[{nihao}]不存在", values));
        System.out.println(StrUtil.indexedFormat("{1}不好{0}", "谁1", "谁2"));
    }
}
