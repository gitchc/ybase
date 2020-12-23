package com.conpany.project;

import cn.hutool.core.io.file.FileReader;
import com.applix.tm1.TM1Server;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.utils.SnowID;
import com.yuanian.dac.tabase.entity.TabaseConnectionInfo;
import com.yuanian.dac.tabase.interfaces.IDatabase;
import com.yuanian.dac.tabase.main.TabaseCommonConnect;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
public class MockData extends Tester {
    private TM1Server server;

    @BeforeAll
    public void init() {


    }

    @Test
    public void mockData() throws Exception {
        TabaseConnectionInfo cofig = new TabaseConnectionInfo();
        cofig.setHost("localhost");
        cofig.setPort("5495");
        cofig.setUsername("admin");
        cofig.setPassword("apple");
        cofig.setDatabase(dababase);
        TabaseCommonConnect commonConnect = new TabaseCommonConnect(cofig);//用户做连接缓存，允许同一个用户并发可以考虑对象的hash值
        IDatabase database = commonConnect.getDataBase();//封装对象，封装对象里面可以通过.getNativeObj()方法获取到TM1原生对象
        server = database.getNativeObj();
        String cubestrs = "" +
                "F020001 铺位基础信息表（商场）中转\n" +
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
                "F14007 费用-分部门";
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
                "F14003 \n" +
                "F14006\n" +
                "F14007";
        Map<String, String> keys = new HashMap<>();
        String[] cubeCodes = cubeCode.split("\\n");
        List<String> mm = new ArrayList<>();
        List<Member> members = memberService.findAllDim();
        for (Member member : members) {
            keys.put(member.getName(), member.getCode());
        }
        int maxsize = 10000;
        int i = 0;
        for (String cubename : cubestrs.split("\\n")) {
            if (cubename.startsWith("*")) {
                i++;
                continue;
            }
            int total = 0;
            int maxi = 0;
            String dubcode = cubeCodes[i];
            memberService.execute("truncate  " + dubcode);
            FileReader fileReader = new FileReader("D:\\mock\\" + cubename + ".cma");
            List<String> dimcodes = new ArrayList<>();
            for (String dimensionName : database.getCubeByName(cubename).getDimensionNames()) {
                dimcodes.add(keys.get(dimensionName));
            }
            String cols = "id," + StringUtils.join(dimcodes, ",") + ",value";
            int oldlength = -1;
            StringBuilder sqls = new StringBuilder();
            for (String readLine : fileReader.readLines()) {
                String[] splits = readLine.split("\",");
                int length = splits.length;
                if (oldlength == -1) {
                    oldlength = length;
                }
                if (oldlength != length) {
                    System.out.println(readLine);
                    continue;
                }
                total++;
                if (sqls.length() == 0) {
                    sqls.append("insert into " + dubcode + " (" + cols + ") values (");
                } else {
                    sqls.append(",(");
                }
                sqls.append("'" + SnowID.nextID() + "'");
                for (int i1 = 1; i1 < length; i1++) {
                    String split = splits[i1];
                    sqls.append(",");
                    if (i1 == length - 1) {
                        if (!NumberUtils.isNumber(split)) {
                            sqls.append(0);
                        } else {
                            sqls.append(new BigDecimal(split).add(new BigDecimal(0.4)).multiply(new BigDecimal(0.85)).setScale(2, RoundingMode.HALF_UP).doubleValue());
                        }
                    } else {
                        sqls.append("'");
                        splits[i1] = splits[i1].replaceFirst("\"", "");
                        sqls.append(MockUtil.getCode(splits[i1]));
                        sqls.append("'");
                    }
                }

                sqls.append(")");
                maxi++;
                if (maxi == maxsize) {
                    try {
                        memberService.execute(sqls.toString());
                    } catch (Exception throwables) {
                        throwables.printStackTrace();
                        System.out.println(sqls.toString());
                        throw new Exception("xxx");
                    }
                    log.info(cubename + "--" + dubcode + ":已提交:{}提交数据", total);
                    sqls.setLength(0);
                    maxi = 0;
                }

            }
            if (sqls.length() > 0) {
                memberService.execute(sqls.toString());
                sqls.setLength(0);
                log.info(cubename + "--" + dubcode + ":最后提交:{}提交数据", total);
            }
            mm.add(cubename + "--" + dubcode + "--数据量:+" + total);
            i++;
        }
        for (String s : mm) {
            System.out.println(s);
        }
    }
}
