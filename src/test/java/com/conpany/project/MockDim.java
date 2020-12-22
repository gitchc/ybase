package com.conpany.project;

import com.applix.tm1.*;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Member;
import com.yuanian.dac.tabase.entity.TabaseConnectionInfo;
import com.yuanian.dac.tabase.interfaces.IDatabase;
import com.yuanian.dac.tabase.main.TabaseCommonConnect;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MockDim extends Tester {
    private TM1Server server;

    public MockDim() {

    }

    @BeforeAll
    public void init() {

    }

    @Test
    public void mockDim() throws Exception {
        TabaseConnectionInfo cofig = new TabaseConnectionInfo();
        cofig.setHost("localhost");
        cofig.setPort("5495");
        cofig.setUsername("admin");
        cofig.setPassword("apple");
        cofig.setDatabase("IN-EPM");
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
                "F14007 费用-分部门";
        for (String cubename : cubestrs.split("\\n")) {
            for (String dimensionName : database.getCubeByName(cubename).getDimensionNames()) {
                TabaseDimToPLN(dimensionName);
            }
        }
    }

    private void TabaseDimToPLN(String dimName) {
        try {

            Member dim = new Member();
            dim.setCode(MockUtil.getCode(dimName));
            dim.setName(MockUtil.getName(dimName));
            dim.setDatatype(10);
            String dimid = null;
            try {
                dimid = memberService.insertDim(dim);
            } catch (ServiceException e) {
                System.out.println("维度已经存在跳过:" + dim.getName());
                return;
            }
            System.out.println("开始同步维度:" + dim.getName());
            Member pmember = memberService.findById(dimid);
            TreeVo vo = getDimTreeWithoutFilter(dimName, "", "");
            int i = 0;
            for (TreeVo child : vo.getChildren()) {//第一层成员直接创建成员,否则后面查父项=pid查不到
                i++;
                String memberName = child.getName();
                Member member = new Member();
                member.setName(MockUtil.getName(memberName));
                member.setCode(MockUtil.getCode(memberName));
                member.setDimid(dimid);
                if (child.getChildren() == null || child.getChildren().size() == 0) {
                    member.setDatatype(0);
                } else {
                    member.setDatatype(10);
                }
                member.setPid(dimid);
                member.setMembertype(1);
                member.setPosition(i);
                Member npmember = memberService.insertMember(member, pmember);
                System.out.println("成员:" + member.getName() + ",编码:" + member.getCode());
                createMeb(dimid, npmember, vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createMeb(String dimid, Member pmember, TreeVo vo) {
        int i = 0;
        for (TreeVo child : vo.getChildren()) {
            i++;
            String memberName = child.getName();
            Member member = new Member();
            member.setName(MockUtil.getName(memberName));
            member.setCode(MockUtil.getCode(memberName));
            member.setDimid(dimid);
            member.setDatatype(0);
            member.setPid(pmember.getId());
            member.setMembertype(1);
            member.setPosition(i);
            System.out.println("成员:" + member.getName() + ",编码:" + member.getCode());
            Member npmember = memberService.insertMember(member, pmember);
            if (child.getChildren().size() > 0) {
                createMeb(dimid, npmember, child);
            }
        }
    }

    private TreeVo getDimTreeWithoutFilter(String dimname, String subsetName, String aliasName) throws Exception {
        TM1Subset tm1Subset;
        TM1Dimension dimension = server.getDimension(dimname);
        if (StringUtils.isBlank(subsetName)) {
            tm1Subset = dimension.createSubset();
            TM1Val val = tm1Subset.getAll();
            if (!val.isError()) {
            }

        } else {
            TM1Subset subset = dimension.getSubset(subsetName);
            tm1Subset = subset.duplicate();
        }
        int elementCount = tm1Subset.getElementCount();
        for (int i = 1; i <= elementCount; i++) {
            TM1Element element = tm1Subset.getElement(i);
            if (element.getElementType() == TM1ObjectType.ElementConsolidated) {
                TM1Val display = tm1Subset.elementDisplay(i);
                if (tm1Subset.subsetElementDisplayPlus(display)) {
                    tm1Subset.selectByIndex(i, true);
                }
            }
        }
        tm1Subset.insertDescendentsOfSelection();
        TreeVo treeResult = new TreeVo();
        treeResult.setId("-1");
        treeResult.setText(dimname);
        treeResult.setName(dimname);
        TM1Attribute attribute = null;
        int maxlevel = dimension.getLevelsCount() + 10;
        TreeVo[] vos = new TreeVo[maxlevel];
        TM1Element[] elementLeves = new TM1Element[maxlevel];
        int elementCount1 = tm1Subset.getElementCount();
        for (int i = 1; i <= elementCount1; i++) {
            TM1Val displayelement = tm1Subset.elementDisplay(i);
            int level = tm1Subset.subsetElementDisplayLevel(displayelement);
            TM1Element element = tm1Subset.getElement(i);
            if (attribute == null && StringUtils.isNotBlank(aliasName)) {
                attribute = element.getAttribute(aliasName);
            }
            String elename = element.getName().getString();
            TreeVo treeData = new TreeVo(elename, elename);
            treeData.addAttribute("datatype", "1");
            if (level == 0) {
                treeResult.addChildren(treeData);
                treeData.addAttribute("weight", "1");
            } else {
                vos[level - 1].addChildren(treeData);
                TM1Val val = elementLeves[level - 1].getComponentWeight(element);
                treeData.addAttribute("weight", val.getString());
            }
            treeData.addAttribute("level", level + 2 + "");
            vos[level] = treeData;
            elementLeves[level] = element;
        }
        tm1Subset.destroy();
        return treeResult;
    }
}
