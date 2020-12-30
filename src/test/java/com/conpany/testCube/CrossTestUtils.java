package com.conpany.testCube;

import cn.hutool.core.date.StopWatch;
import com.yonyou.mde.web.utils.MuiltCross;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrossTestUtils {
    public static void main(String[] args) {
        List<String[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new String[]{"1", "2", "3", "4", "5"});
        }
        StopWatch watch = new StopWatch();
        watch.start("MuiltCross");
        MuiltCross cross = new MuiltCross(list);
        int start = 1000000;
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
