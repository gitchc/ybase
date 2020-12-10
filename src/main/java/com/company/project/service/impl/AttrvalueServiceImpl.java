package com.company.project.service.impl;

import com.company.project.dao.AttrvalueMapper;
import com.company.project.model.Attrvalue;
import com.company.project.service.AttrvalueService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class AttrvalueServiceImpl extends AbstractService<Attrvalue> implements AttrvalueService {
    @Resource
    private AttrvalueMapper attrvalueMapper;

}
