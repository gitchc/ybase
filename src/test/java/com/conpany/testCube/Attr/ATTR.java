package com.conpany.testCube.Attr;

import com.conpany.project.Tester;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.bigCube.main.Cube;
import com.yonyou.mde.web.bigCube.main.Dimension;
import com.yonyou.mde.web.bigCube.main.Member;
import com.yonyou.mde.web.bigCube.main.Server;
import com.yonyou.mde.web.script.Utils.Assert;
import org.junit.Test;
import tech.tablesaw.api.Row;
import tech.tablesaw.api.Table;

public class ATTR extends Tester {

    private void printAll(SliceResult se) {
        Table resultTable = se.toTable();
        System.out.println(String.format("Result Size: %d\n%s", resultTable.rowCount(), resultTable.printAll()));
    }

    @Test
    public void exp() throws MdeException {
        Cube cube = Server.getServer().getCube("测试属性");
        Dimension dimension = cube.getDimension("QIJIAN");
        SliceResult sliceResult;
        cube.exp("VERSION.第1版=if(attrValue(QIJIAN,~,所属季度)==1,1,100)");
        sliceResult = cube.find("VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属季度");
            if (value.equals("1")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 1);
            } else {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 100);
            }
        }
        cube.exp("VERSION.第1版=attrValue(QIJIAN,~,所属月)");
        sliceResult = cube.find("VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属月");
            Double dvalue = Double.parseDouble(value);
            Assert.assrt("属性赋值不正确!", dvalue == row.getDouble("value"));
        }
        cube.exp("VERSION.第1版=if(attrValue(QIJIAN,~,所属月)==2,continue,100)");
        sliceResult = cube.find("VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属月");
            if (!value.equals("2")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 100);
            } else {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 2);
            }
        }
    }
}
