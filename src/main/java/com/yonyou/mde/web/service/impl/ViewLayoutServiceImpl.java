package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.dao.ViewLayoutMapper;
import com.yonyou.mde.web.model.ViewLayout;
import com.yonyou.mde.web.service.ViewLayoutService;
import com.yonyou.mde.web.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
* @Author chenghch
* @Date 2021-01-27
*/
@Service
@Transactional
public class ViewLayoutServiceImpl extends AbstractService<ViewLayout> implements ViewLayoutService {
    @Resource
    private ViewLayoutMapper viewLayoutMapper;

    @Override
    public void updateScope(String viewid, String dimid, String layouttype, String scope) {
        viewLayoutMapper.updateScope(viewid,dimid,layouttype,scope);
    }
}
