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

public class attr extends Tester {

    @Test
    public void exp() throws MdeException {
        Cube cube = Server.getServer().getCube("测试属性");
        Dimension dimension = cube.getDimension("QIJIAN");
        testAnd(cube, dimension);//测试if and
        testOr(cube, dimension);//测试if or
        insert(cube);//新值赋值,if
        testContinue(cube, dimension); //测试if continue*/

//        qiantao(cube, dimension);//嵌套,if 暂时不支持


    }

    private void insert(Cube cube) throws MdeException{
        cube.exp("(YEAR.2021年)->if (true,YEAR.2020年=YEAR.2021年-1,continue) ");
        cube.exp("(YEAR.2021年)->if (true,YEAR.2022年=YEAR.2021年+1,continue) ");
        SliceResult sliceResult = cube.exp("YEAR.2023年=YEAR.2022年-YEAR.2020年");
            printAll(sliceResult);
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("值不正确!", row.getDouble("value") == 2);
        }
    }

    private void qiantao(Cube cube, Dimension dimension) throws MdeException {
        SliceResult sliceResult;
        cube.exp("VERSION.第1版=if(attrValue(QIJIAN,~,所属季度)==1,if(attrValue(QIJIAN,~,所属月)==1,1,100),100)");
        sliceResult = cube.find("VERSION.第1版");
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

    private void testContinue(Cube cube, Dimension dimension) throws MdeException {
        SliceResult sliceResult;
        cube.exp("(VERSION.第1版)->if(attrValue(QIJIAN,~,所属月)==2,continue,VERSION.第1版=100+attrValue(QIJIAN,~,所属月))");
        cube.exp("(VERSION.第1版)->if(attrValue(QIJIAN,~,所属月)==2,VERSION.第1版=attrValue(QIJIAN,~,所属月),continue)");
        sliceResult = cube.find("VERSION.第1版");
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属月");
            if (!value.equals("2")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 100+Double.parseDouble(value));
            }else {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == Double.parseDouble(value));
            }
        }
    }

    private void testOr(Cube cube, Dimension dimension) throws MdeException {
        SliceResult sliceResult;
        cube.exp("(VERSION.第1版)->if((attrValue(QIJIAN,~,所属季度)==1 || attrValue(QIJIAN,~,所属月)==1),VERSION.第1版=attrValue(QIJIAN,~,所属季度),VERSION.第1版=100+attrValue(QIJIAN,~,所属月))");
        sliceResult = cube.find("VERSION.第1版");
        printAll(sliceResult);
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属季度");
            String value1 = member.getAttrValue("所属月");
            if (value.equals("1") || value1.equals("1")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == Double.parseDouble(value));
            } else {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == (100+Double.parseDouble(value1)));
            }
        }
    }

    private void testAnd(Cube cube, Dimension dimension) throws MdeException {
        SliceResult sliceResult;
        cube.exp("(VERSION.第1版)->if((attrValue(QIJIAN,~,所属季度)==1 && attrValue(QIJIAN,~,所属月)==1),VERSION.第1版=attrValue(QIJIAN,~,所属季度),VERSION.第1版=attrValue(QIJIAN,~,所属月)+100)");
        sliceResult = cube.find("VERSION.第1版");
        printAll(sliceResult);
        for (Row row : sliceResult.toTable()) {
            Member member = dimension.getMember(row.getText("qijian"));
            String value = member.getAttrValue("所属季度");
            String value1 = member.getAttrValue("所属月");
            if (value.equals("1") && value1.equals("1")) {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == 1);
            } else {
                Assert.assrt("属性赋值不正确!", row.getDouble("value") == (100+Double.parseDouble(value1)));
            }
        }
    }
}
