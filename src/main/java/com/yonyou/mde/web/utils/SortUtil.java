package com.yonyou.mde.web.utils;

import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.entity.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortUtil {
    /*
     * 1.1.2 1.2.3
     * 高效排序
     */
    public static <T extends Position> List<T> sort(List<T> data) {
        return data.stream().sorted(c2).collect(Collectors.toList());
    }

    public static List<Member> sortMember(List<Member> data) {
        return data.stream().sorted(c1).collect(Collectors.toList());
    }

    private static final int ToRight = 1;
    private static final int ToLeft = -1;

    private static Comparator<Member> c1 = (t1, t2) -> {
        String[] str1Split = t1.getUnipos().split(",");
        String[] str2Split = t2.getUnipos().split(",");
        int minSplitLength = str1Split.length > str2Split.length ? str2Split.length : str1Split.length;
        // 用长度小的数组长度作为遍历边界，防止数组越界
        for (int i = 0; i < minSplitLength; i++) {
            // 将字符串转换成数字解决 一位数字(eg: 2) 和 两位数字(eg: 10) 的大小比较问题
            String str1 = str1Split[i];
            String str2 = str2Split[i];
            int is1 = str1.length();
            int is2 = str2.length();
            if (is1 == is2) {
                int compareResult = str1.compareTo(str2);
                if (compareResult == 0) {
                    continue;
                } else if (compareResult > 0) {
                    return ToRight;
                } else {
                    return ToLeft;
                }
            } else if (is1 > is2) {
                return ToRight;
            } else {
                return ToLeft;
            }


        }

        // 若程序进行到这里，说明在循环里没有得出比较结果。
        // 此时应该是数组长度长的字符串（1.10.1）排在后面，数组长度短的字符串（1.10）排在前面
        if (str1Split.length == str2Split.length) {
            return 0;
        } else if (minSplitLength == str1Split.length) {
            return ToLeft;
        } else {
            return ToRight;
        }
    };

    private static Comparator<Position> c2 = (t1, t2) -> {
        String[] str1Split = t1.getUnipos().split(",");
        String[] str2Split = t2.getUnipos().split(",");
        int minSplitLength = str1Split.length > str2Split.length ? str2Split.length : str1Split.length;
        // 用长度小的数组长度作为遍历边界，防止数组越界
        for (int i = 0; i < minSplitLength; i++) {
            // 将字符串转换成数字解决 一位数字(eg: 2) 和 两位数字(eg: 10) 的大小比较问题
            String str1 = str1Split[i];
            String str2 = str2Split[i];
            int is1 = str1.length();
            int is2 = str2.length();
            if (is1 == is2) {
                int compareResult = str1.compareTo(str2);
                if (compareResult == 0) {
                    continue;
                } else if (compareResult > 0) {
                    return ToRight;
                } else {
                    return ToLeft;
                }
            } else if (is1 > is2) {
                return ToRight;
            } else {
                return ToLeft;
            }


        }

        // 若程序进行到这里，说明在循环里没有得出比较结果。
        // 此时应该是数组长度长的字符串（1.10.1）排在后面，数组长度短的字符串（1.10）排在前面
        if (str1Split.length == str2Split.length) {
            return 0;
        } else if (minSplitLength == str1Split.length) {
            return ToLeft;
        } else {
            return ToRight;
        }
    };


    public static void main(String[] args) {
        String[] dataSource = new String[]{"1,1,", "1,1,100,211,1,6,1000,21", "1,3,100,211,1,6,1000,21", "1,9,100,211,1,6,1000,21", "1,7", "1,10,1", "1,10",
                "1,2,1,6,1000,21", "1,4,1,6,1000,21,1,6,1000,21", "1,5,1,6,1000,21", "1,8,1,6,1000,21", "1,6,1000,21", "1,11,100,211", "2,4,100,211,1,6,1000,21", "2,10,100,211", "1,1,10,100,211", "1,1,8",
                "1,1,3,1,6,1000,21", "2,1,5,1,6,1000,21", "2,1,10,1,6,1000,21", "2,1,6,1000,21"};

        List<String> data1 = new ArrayList<>();
        int i = 1000000;
        for (int i1 = 0; i1 < i; i1++) {
            data1.add(dataSource[i1 % 20]);
        }
    }


}
