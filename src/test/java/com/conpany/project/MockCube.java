/*
package com.conpany.project;

import com.applix.tm1.TM1Server;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.Dimension;
import com.yuanian.dac.tabase.entity.TabaseConnectionInfo;
import com.yuanian.dac.tabase.interfaces.IDatabase;
import com.yuanian.dac.tabase.interfaces.IDimension;
import com.yuanian.dac.tabase.main.TabaseCommonConnect;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockCube extends Tester {
    private TM1Server server;

    public MockCube() {

    }

    @BeforeAll
    public void init() {

    }

    @Test
    public void mockCube() throws Exception {
        TabaseConnectionInfo cofig = new TabaseConnectionInfo();
        cofig.setHost("localhost");
        cofig.setPort("5495");
        cofig.setUsername("admin");
        cofig.setPassword("apple");
        cofig.setDatabase(dababase);
        TabaseCommonConnect commonConnect = new TabaseCommonConnect(cofig);//用户做连接缓存，允许同一个用户并发可以考虑对象的hash值
        IDatabase database = commonConnect.getDataBase();//封装对象，封装对象里面可以通过.getNativeObj()方法获取到TM1原生对象
        server = database.getNativeObj();
        String cubestrs = "F020001 铺位基础信息表（商场）中转\n" +
                "F02001 铺位基础信息表（商场）\n" +
                "F02001 铺位基础信息补录表（商场）\n" +
                "F02002 商场租户租金调整表\n" +
                "F02004 写字楼租金收入调整和汇总表\n" +
                "F02006 仓库租金收入调整和汇总表\n" +
                "F03001 推广广告位收入\n" +
                "F03002 推广场地收入\n" +
                "F03004 推广费用明细\n" +
                "F04001 铺位销售额预估表-销售额填报\n" +
                "F04001 铺位业态销售额\n" +
                "F04001业态坪效分月\n" +
                "F05002 人力薪酬预算\n" +
                "F05004 行政办公费\n" +
                "F05007 IT收支\n" +
                "F06001 招待费及市内交通费\n" +
                "F06002 差旅费及会议费-明细表\n" +
                "F06004 办公费\n" +
                "F08002 其他物业收入\n" +
                "F08009 其他物业成本\n" +
                "F08012 工具设备及物料表1\n" +
                "F11016 上报融资利率表和融资费用展示表平台公司\n" +
                "F13002 2019年经营预算一\n" +
                "F13002 2019年经营预算一(部门)_预算控制查询\n" +
                "F13002 2019年经营预算一(项目)\n" +
                "F14001 铺位预估预测\n" +
                "F14003 仓库预估预测\n" +
                "F14006 收入及税金科目级别预测-财务\n" +
                "F14007 费用-分部门\n" +
                "onemillion\n" +
                "tenmillion\n" +
                "hundredmillion\n" +
                "onebillion";

        String cubeCode = "F020001\n" +
                "F020011\n" +
                "F020012\n" +
                "F02002\n" +
                "F02004\n" +
                "F02006\n" +
                "F03001\n" +
                "F03002\n" +
                "F03004\n" +
                "F040011\n" +
                "F040012\n" +
                "F040013\n" +
                "F05002\n" +
                "F05004\n" +
                "F05007\n" +
                "F06001\n" +
                "F06002\n" +
                "F06004\n" +
                "F08002\n" +
                "F08009\n" +
                "F08012\n" +
                "F11016\n" +
                "F130021\n" +
                "F130022\n" +
                "F130023\n" +
                "F14001\n" +
                "F14003\n" +
                "F14006\n" +
                "F14007\n" +
                "onemillion\n" +
                "tenmillion\n" +
                "hundredmillion\n" +
                "onebillion";
        String[] cubeCodes = cubeCode.split("\\n");
        Map<String, String> keys = new HashMap<>();
        List<Dimension> dims = dimensionService.getAllDims();
        for (Dimension dim : dims) {
            keys.put(dim.getName(), dim.getId());
        }
        int i = 0;
        for (String cubename : cubestrs.split("\\n")) {
            List<String> idss = new ArrayList<>();
            for (IDimension dimension : database.getCubeByName(cubename).getDimensions()) {
                idss.add(keys.get(dimension.getName()));
            }
            Cube cube = new Cube();
            cube.setAutoload(0);
            cube.setAutosql(1);
            cube.setDimids(StringUtils.join(idss, ","));
            cube.setCubecode(cubeCodes[i]);
            if ("onemillion".equals(cubename)) {
                cubename = "100W模型";
            }
            if ("tenmillion".equals(cubename)) {
                cubename = "1000W模型";
            }
            if ("hundredmillion".equals(cubename)) {
                cubename = "1亿模型";
            }
            if ("onebillion".equals(cubename)) {
                cubename = "10亿模型";
            }
            cube.setCubename(cubename);
            cubeService.insertCube(cube);
            i++;
        }
    }


}
*/
