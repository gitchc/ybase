package com.conpany.project;


import com.yonyou.mde.web.Application;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.MemberService;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;

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
    protected com.yonyou.mde.web.service.CubeService CubeService;
    @Resource
    DataSource dataSource;
}



