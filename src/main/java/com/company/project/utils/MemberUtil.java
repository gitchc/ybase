package com.company.project.utils;

import com.company.project.model.Member;

public class MemberUtil {
    public static String getCodeDetail(Member member) {
        int g = member.getGeneration();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < g; i++) {
            sb.append("&nbsp;&nbsp;&nbsp;");
        }
        sb.append(member.getCode());
        return sb.toString();
    }
}
