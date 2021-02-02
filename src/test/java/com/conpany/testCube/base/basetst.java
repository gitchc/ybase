package com.conpany.testCube.base;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Row;

public class basetst extends Tester {
    @Test
    public void add() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.2月", 2);
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.3月", 1);
        SliceResult sliceResult = cube.exp("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月=QIJIAN.2月+QIJIAN.3月");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 3);
        }
    }
    @Test
    public void substract() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.2月", 2);
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.3月", 1);
        SliceResult sliceResult = cube.exp("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月=QIJIAN.2月-QIJIAN.3月");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 1);
        }
    }
    @Test
    public void multi() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.2月", 2);
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.3月", 1);
        SliceResult sliceResult = cube.exp("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月=QIJIAN.2月*QIJIAN.3月");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 2);
        }
    }
    @Test
    public void devid() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.2月", 2);
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.3月", 1);
        SliceResult sliceResult = cube.exp("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月=QIJIAN.2月/QIJIAN.3月");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 2);
        }
    }

    @Test
    public void rollup() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月", 1);
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.2月", 1);
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.3月", 1);
        SliceResult sliceResult = cube.find("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1季度");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 3);
        }
    }

}