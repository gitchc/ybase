package com.conpany.testCube.Attr;

import com.conpany.project.Tester;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.bigCube.main.Cube;
import com.yonyou.mde.web.bigCube.main.Server;
import org.junit.Test;
import tech.tablesaw.api.Table;

public class ATTR extends Tester {

    private void printAll(SliceResult se) {
        Table resultTable = se.toTable();
        System.out.println(String.format("Result Size: %d\n%s", resultTable.rowCount(), resultTable.printAll()));
    }

    @Test
    public void exp() throws MdeException {
        Cube cube = Server.getServer().getCube("测试属性");
        printAll(cube.exp("YEAR.2019#QIJIAN.2月=if(attrValue(QIJIAN,~,所属季度)==1,1,-1)"));
        printAll(cube.find("YEAR.2019#QIJIAN.2月"));
        SliceResult exp = cube.calc("YEAR.2019=if(attrValue(QIJIAN,~,所属月)==2,2,2000)");
        printAll(exp);
    }
}
