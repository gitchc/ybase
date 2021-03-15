package com.conpany.testCube;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Row;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2021/3/11
 */
public class Math extends Tester {
    @Test
    public void ABS() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",-1.5);
        SliceResult sliceResult = cube.calc("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=ABS(ACCOUNT.电费)");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 1.5);
        }
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费",-1.6);
        SliceResult sliceResult1 = cube.calc("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=ABS(-1.6)");
        for (Row row : sliceResult1.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 1.6);
        }
    }

    @Test
    public void INT() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",-1.5);
        SliceResult sliceResult = cube.calc("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=INT(ACCOUNT.电费)");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == -1);
        }
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费",-1.6);
        SliceResult sliceResult1 = cube.calc("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=INT(-1.6)");
        for (Row row : sliceResult1.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == -1);
        }
    }

    @Test
    public void INTABS() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",-1.5);
        SliceResult sliceResult = cube.calc("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=INT(ABS(ACCOUNT.电费))");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 1);
        }
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费",-1.6);
        SliceResult sliceResult1 = cube.calc("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=INT(ABS(-1.6))");
        for (Row row : sliceResult1.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 1);
        }
    }

    @Test
    public void sum() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",1);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",1);
        SliceResult sliceResult = cube.calc("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=SUM(Leaves(Area,海外城市))");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 2);
        }
    }
    @Test
    public void min() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",1);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",2);
        SliceResult sliceResult = cube.calc("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=MIN(Leaves(Area,海外城市))");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 1);
        }
    }
    @Test
    public void max() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",1);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",2);
        SliceResult sliceResult = cube.calc("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=MAX(Leaves(Area,海外城市))");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 2);
        }
    }
    @Test
    public void AVG() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",4);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",2);
        SliceResult sliceResult = cube.calc("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=AVG(Leaves(Area,海外城市))");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 3);
        }
    }

    @Test
    public void Round() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.电费",2.7);
        SliceResult sliceResult = cube.calc("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.标准电费=Round(Area.芝加哥,0)");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("累计值不正确", row.getDouble("VALUE") == 3);
        }
    }
}
