package com.yonyou.mde.web.start;

import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DataService.MDEManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/*
 * 启动的时候初始化loadCube数据
 * */
@Component
public class LoadCube {
    @Resource
    private CubeService cubeService;
    @Resource
    private DataSourceConfig dataSourceConfig;
    @Resource
    private MDEManager mdeManager;

    @PostConstruct
    public void init() {//加载自动装载的cube
        mdeManager.initMde();//初始化MDE环境
//        cubeService.loadAllAutoCube();//load所有自动加载
        new BudgetMysqlModelLoader().load(dataSourceConfig);

    }
}
