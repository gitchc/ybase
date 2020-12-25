package com.yonyou.mde.web.start;

import com.yonyou.mde.web.service.CubeService;
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

    @PostConstruct
    public void initLoadCube() {//加载自动装载的cube
        cubeService.loadAutoCube();
    }
}
