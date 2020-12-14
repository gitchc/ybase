package com.company.project.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/*
 * 笛卡尔积
 * */
public class CrossUtils {

    /*
     * @param lists:{[a],[b1,b2]}
     * @param split 拼接符号,空格等
     * @result {a,b1;a,b2}
     *
     * */
    public static List<String> crossToString(List<List<String>> lists, String split) {
        List<String> tempList = new ArrayList<>();
        for (List<String> list : lists) {
            if (tempList.isEmpty()) {
                tempList = list;
            } else {
                //java8新特性，stream流
                tempList = tempList.stream().flatMap(item -> list.stream().map(item2 -> (item + split + item2))).collect(Collectors.toList());
            }
        }
        return tempList;
    }

    /*
     * @param lists:{[a],[b1,b2]}
     * @param split 拼接符号,空格等
     * @result {a,b1;a,b2}
     *
     * */
    public static <T> List<List<T>> crossToList(List<List<T>> lists) {
        List<List<T>> tempList = new ArrayList<>();
        for (List<T> list : lists) {
            if (tempList.isEmpty()) {
                for (T t : list) {
                    tempList.add(Arrays.asList(t));
                }
            } else {
                //java8新特性，stream流
                tempList = tempList.stream().flatMap(item -> list.stream().map(item2 -> (joinList(item, item2)))).collect(Collectors.toList());
            }
        }
        return tempList;
    }

    /*
     * @param lists:{[a],[b1,b2]}
     * @param split 拼接符号,空格等
     * @result {a,b1;a,b2}
     *
     * */
    public static <T> List<T[]> crossToArray(List<T[]> lists) {
        List<T[]> tempList = new ArrayList<>();
        for (T[] list : lists) {
            if (tempList.isEmpty()) {
                for (T t : list) {
                    tempList.add(newArrays(t));
                }
            } else {
                //java8新特性，stream流
                tempList = tempList.stream().flatMap(item -> Arrays.stream(list).map(item2 -> (joinT(item, item2)))).collect(Collectors.toList());
            }
        }
        return tempList;
    }

    private static <T> List<T> joinList(List<T> item, T item2) {
        ArrayList<T> ts = new ArrayList<>(item);
        ts.add(item2);
        return ts;
    }


    private static <T> T[] joinT(T[] src, T t) {
        return addAll(src, t);
    }


    private static <T> T[] newArrays(T t) {
        T[] ts = (T[]) Array.newInstance(t.getClass(), 1);
        ts[0] = t;
        return ts;
    }

    private static <T> T[] addAll(T[] array1, T... array2) {
        if (array1 == null) {
            return array2;
        } else if (array2 == null) {
            return array1;
        } else {
            Class<?> type1 = array1.getClass().getComponentType();
            T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
            System.arraycopy(array1, 0, joinedArray, 0, array1.length);

            try {
                System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
                return joinedArray;
            } catch (ArrayStoreException var6) {
                Class<?> type2 = array2.getClass().getComponentType();
                if (!type1.isAssignableFrom(type2)) {
                    throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), var6);
                } else {
                    throw var6;
                }
            }
        }
    }

    public static void main(String[] args) {
        List<String[]> list = new ArrayList<>();
        List<List<String>> list2 = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            String dimname = "dim" + i;
            String[] dim = new String[10];
            for (int i1 = 0; i1 < 10; i1++) {
                dim[i1] = dimname + "_" + i1;
            }
            list.add(dim);
            list2.add(Arrays.asList(dim));
        }


    }
}