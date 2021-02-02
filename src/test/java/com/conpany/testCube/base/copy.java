package com.conpany.testCube.base;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Row;

public class copy extends Tester {
    @Test
    public void copy() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月", 0);
        SliceResult sliceResult1 = cube.find("Area.直辖市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#account.电费");
        SliceResult sliceResult2 = cube.find("Area.华北#YEAR.2020年#VERSION.第1版#QIJIAN.1月#account.电费");
        cube.setData("Area.北京#YEAR.2020年#VERSION.第1版#account.电费#QIJIAN.1月", 1);
        SliceResult sliceResult11 = cube.find("Area.直辖市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#account.电费");
        SliceResult sliceResult21 = cube.find("Area.华北#YEAR.2020年#VERSION.第1版#QIJIAN.1月#account.电费");

        SliceResult subtract1 = (SliceResult) sliceResult11.subtract(sliceResult1);
        SliceResult subtract2 = (SliceResult) sliceResult21.subtract(sliceResult2);
        for (Row row : subtract1.toTable()) {
            Assert.assrt("副本值不正确", row.getDouble("VALUE") == 1);
        }
        for (Row row : subtract2.toTable()) {
            Assert.assrt("副本值不正确", row.getDouble("VALUE") == 1);
        }
    }

    @Test
    public void copy1() throws MdeException {
        Cube cube = Server.getCube("测试属性");
        cube.exp("YEAR.2020年#VERSION.第1版#QIJIAN.[1月,2月]=attrValue(QIJIAN,~,所属月)");
        SliceResult sliceResult;
        sliceResult = cube.find("YEAR.2020年#VERSION.第1版#QIJIAN.[1月,2月]");
        for (Row row : sliceResult.toTable()) {
            if (row.getText("qijian").equals("2月")) {
                Assert.assrt("属性2月赋值不正确!", row.getDouble("value") == 2);
            } else if (row.getText("qijian").equals("1月")) {
                Assert.assrt("属性1月赋值不正确!", row.getDouble("value") == 1);
            }

        }
    }
}