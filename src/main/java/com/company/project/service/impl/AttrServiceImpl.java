package com.company.project.service.impl;

import com.company.project.dao.AttrMapper;
import com.company.project.model.Attr;
import com.company.project.service.AttrService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class AttrServiceImpl extends AbstractService<Attr> implements AttrService {
    @Resource
    private AttrMapper attrMapper;

}
