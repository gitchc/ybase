package com.conpany.project;


import com.yonyou.mde.web.Application;
import com.yonyou.mde.web.script.BaseScript;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.MemberService;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 单元测试继承该类即可
 */
//@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
/*@Transactional
@Rollback*/
public abstract class Tester {
    @Resource
    protected MemberService memberService;
    @Resource
    protected CubeService cubeService;
    @Resource
    BaseScript baseScript;
    String dababase = "DD";
}



