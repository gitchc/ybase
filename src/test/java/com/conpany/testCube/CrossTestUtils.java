package com.conpany.testCube;

import cn.hutool.core.date.StopWatch;
import cn.hutool.core.util.ArrayUtil;
import com.yonyou.mde.web.utils.MuiltCross;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrossTestUtils {
    public static void main(String[] args) {
        List<String[]> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            List<String> abc = new ArrayList<>();
            for (int i1 = 0; i1 < 1000; i1++) {
                abc.add(i1+"");
            }
            list.add(ArrayUtil.toArray(abc, String.class));
        }
        StopWatch watch = new StopWatch();
        watch.start("MuiltCross");
        MuiltCross cross = new MuiltCross(list);
        System.out.println(cross.size());
        int start = 8000000;
        int end = start + 5000;
        List<String[]> re = new ArrayList<>();
        for (int i = 0; i < end; i++) {
            if (i < start) {
                cross.next();
            } else {
                re.add((String[]) cross.next());
            }
        }
        watch.stop();
        System.out.println(Arrays.toString(re.get(0)));
        System.out.println(watch.getLastTaskTimeMillis());

        watch.start("MuiltCross1");
        MuiltCross cross2 = new MuiltCross(list);
        List<String[]> re2 = cross2.get(start, end);
        watch.stop();
        System.out.println(Arrays.toString(re2.get(0)));
        System.out.println(watch.getLastTaskTimeMillis());
    }
}
