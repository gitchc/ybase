package com.conpany.testCube.base;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Row;

public class rollup extends Tester {
    @Test
    public void rollup() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.海外城市#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月", 1);
        cube.setData("Area.纽约#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月", 20);

        SliceResult sliceResult1 = cube.find("Area.海外城市#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月");
        for (Row row : sliceResult1.toTable()) {
            Assert.assrt("父节点值不正确", row.getDouble("VALUE") == 1);
        }
    }

}