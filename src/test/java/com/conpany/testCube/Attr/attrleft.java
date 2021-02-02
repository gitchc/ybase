package com.conpany.testCube.Attr;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Dimension;
import com.yonyou.mde.bigCube.main.Member;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Row;

public class attrleft extends Tester {
    @Test
    public void attrTest() throws MdeException {
        Cube cube = Server.getServer().getCube("测试属性");
        Dimension dimension = cube.getDimension("QIJIAN");
        SliceResult sliceResult = cube.find("ATTR(期间,所属季度,1)");
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属季度");
            String value1 = member.getAttrValue("所属月");
            if (value.equals("1") && value1.equals("1")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 1);
            } else {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 100);
            }
        }
    }

    @Test
    public void copy()  throws MdeException {
        Cube cube = Server.getCube("测试属性");
        SliceResult sliceResult;
        cube.setData("YEAR.2020年#QIJIAN.1月#VERSION.第1版",1);
        cube.exp("(YEAR.2020年#QIJIAN.1月#VERSION.第1版)->if(attrValue(QIJIAN,~,所属季度)==2,QIJIAN.2月=attrValue(QIJIAN,~,所属季度)+10,QIJIAN.2月=attrValue(QIJIAN,~,所属季度)+1)");
        sliceResult = cube.find("YEAR.2020年#QIJIAN.2月#VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("属性赋值不正确!", row.getDouble("value") == 2);
        }
        cube.exp("YEAR.2020年#VERSION.第1版#QIJIAN.[1月,2月]=attrValue(QIJIAN,~,所属月)");
        sliceResult = cube.find("VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            if (row.getText("qijian").equals("2月")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 2);
            } else if (row.getText("qijian").equals("1月")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 1);
            }

        }
    }
}
