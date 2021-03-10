package com.conpany.testCube.base;

import com.conpany.project.Tester;
import com.yonyou.mde.bigCube.main.Cube;
import com.yonyou.mde.bigCube.main.Server;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.model.result.SliceResult;
import com.yonyou.mde.web.script.utils.Assert;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.Row;

import java.util.ArrayList;
import java.util.List;

//import com.yonyou.mde.model.result.MultiSliceResult;

public class MDXEXP extends Tester {
    @Test
    public void PreMember() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("PREMEMBER(Area,芝加哥)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("过滤范围不正确", row.getText("Area").equals("纽约"));
        }
    }

    @Test
    public void nextMember() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("NEXTMEMBER(Area,纽约)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        for (Row row : sliceResult.toTable()) {
            Assert.assrt("过滤范围不正确", row.getText("Area").equals("芝加哥"));
        }
    }


    @Test
    public void Siblings() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("SIBLINGS(Area,芝加哥)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        List<String> scope = new ArrayList<>();
        scope.add("纽约");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }

    @Test
    public void ISiblings() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("ISIBLINGS(Area,芝加哥)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        List<String> scope = new ArrayList<>();
        scope.add("芝加哥");
        scope.add("纽约");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }

    @Test
    public void child() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("CHILDREN(Area,海外城市)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        List<String> scope = new ArrayList<>();
        scope.add("芝加哥");
        scope.add("纽约");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }

    @Test
    public void ichild() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        cube.setDataInMemory("Area.芝加哥#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        cube.setDataInMemory("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("ICHILDREN(Area,海外城市)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        List<String> scope = new ArrayList<>();
        scope.add("芝加哥");
        scope.add("纽约");
        scope.add("海外城市");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }

    @Test
    public void Leaves() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        List<String> scope = new ArrayList<>();
        scope.add("深圳");
        scope.add("广州");
        scope.add("西安");
        scope.add("武汉");
        scope.add("纽约");
        scope.add("芝加哥");
        for (String mdx : scope) {
            cube.setDataInMemory("Area." + mdx + "#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        }
        SliceResult sliceResult = cube.find("LEAVES(Area,所有城市)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }

    @Test
    public void Ancestors() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("ANCESTORS(Area,纽约)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        List<String> scope = new ArrayList<>();
        scope.add("所有城市");
        scope.add("海外城市");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }

    @Test
    public void IAncestors() throws MdeException {
        Cube cube = Server.getCube("testmodel");
        cube.setDataInMemory("Area.海外城市#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        cube.setDataInMemory("Area.纽约#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价", 1);
        SliceResult sliceResult = cube.find("IANCESTORS(Area,纽约)#YEAR.2020年#VERSION.第1版#QIJIAN.1月#ACCOUNT.单价");
        List<String> scope = new ArrayList<>();
        scope.add("所有城市");
        scope.add("海外城市");
        scope.add("纽约");
        for (Row row : sliceResult.toTable()) {
            scope.remove(row.getText("Area"));
        }
        Assert.assrt("过滤范围不正确", scope.size() == 0);
    }


}