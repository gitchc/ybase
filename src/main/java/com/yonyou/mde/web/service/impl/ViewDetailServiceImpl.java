package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.dao.ViewDetailMapper;
import com.yonyou.mde.web.model.ViewDetail;
import com.yonyou.mde.web.service.ViewDetailService;
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
public class ViewDetailServiceImpl extends AbstractService<ViewDetail> implements ViewDetailService {
    @Resource
    private ViewDetailMapper view_detailMapper;

}
