package com.conpany.testCube.Attr;

import com.conpany.project.Tester;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.bigCube.main.Cube;
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
        SliceResult sliceResult;
        cube.exp("YEAR.2020年#QIJIAN.2月=if(attrValue(QIJIAN,~,所属季度)==2,10,-10)");
        sliceResult = cube.find("YEAR.2020年#QIJIAN.2月");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("属性复值不正确!", row.getDouble("value") == -10);
        }
        cube.exp("VERSION.第1版=attrValue(QIJIAN,~,所属月)");
        sliceResult = cube.find("VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            if (row.getText("qijian").equals("2月")) {
                Assert.assrt("属性复值不正确!", row.getDouble("value") == 2);
            } else if (row.getText("qijian").equals("1月")) {
                Assert.assrt("属性复值不正确!", row.getDouble("value") == 1);
            }

        }
    }
}
