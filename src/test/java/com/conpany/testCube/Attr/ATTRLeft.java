package com.conpany.testCube.Attr;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Dimension;
import com.yonyou.mde.bigCube.main.Member;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.Test;
import tech.tablesaw.api.Row;

public class ATTRLeft extends Tester {
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
}
