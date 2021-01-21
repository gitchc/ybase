package com.conpany.project;


import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.Application;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DimensionService;
import com.yonyou.mde.web.service.MemberService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.tablesaw.api.Table;

import javax.annotation.Resource;

/**
 * 单元测试继承该类即可
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
/*@Transactional
@Rollback*/
public abstract class Tester {
    @Resource
    protected MemberService memberService;
    @Resource
    protected DimensionService dimensionService;
    @Resource
    protected CubeService cubeService;
    String dababase = "EPM";

    protected void printAll(SliceResult se) {
        Table resultTable = se.toTable();
        System.out.println(String.format("Result Size: %d\n%s", resultTable.rowCount(), resultTable.printAll()));
    }

}



