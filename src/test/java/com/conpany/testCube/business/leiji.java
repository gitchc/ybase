package com.conpany.testCube.business;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Dimension;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.BaseSliceResult;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.Test;
import tech.tablesaw.api.Row;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghch
 * @Date 2021/1/26 9:51
 */
public class leiji extends Tester {
    @Test
    public void exp() throws MdeException {

        Cube cube = Server.getCube("测试属性");
        SliceResult sliceResult = cube.find("VERSION.第1版#YEAR.2021年#QIJIAN.12月");
        SliceResult sliceResult1 = cube.exp("VERSION.第1版#YEAR.2021年#QIJIAN.12月=VERSION.第1版+1");
        SliceResult divide = (SliceResult)sliceResult1.subtract(sliceResult);
        for (Row row : divide.toTable()) {
            Assert.assrt("累计值不正确",row.getDouble("VALUE")==1);
        }
    }

}
