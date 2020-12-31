package com.conpany.project;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.db.Db;
import com.yonyou.mde.web.script.BaseScript;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
public class MockDataByCode extends Tester {
    Db db;

    @BeforeAll
    public void init() {


    }

    @Test
    public void mockData() throws Exception {
        BaseScript baseScript = new BaseScript();
//        ThreadUtil.execute(() -> {
//            baseScript.MockData("onemillion", 1000000);
//        });
//        ThreadUtil.execute(() -> {
//            baseScript.MockData("tenmillion", 10000000);
//        });

//        ThreadUtil.waitForDie();
//        baseScript.MockData("tenmillion", 10000000);
            baseScript.MockData("hundredmillion", 100000000-10700000-19850000-20020000);
//        ThreadUtil.waitForDie();
        /*  baseScript.MockData("onebillion", 1000000000);*/

    }
}
