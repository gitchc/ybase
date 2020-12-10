package com.company.project.service.impl;

import com.company.project.dao.ScriptMapper;
import com.company.project.model.Script;
import com.company.project.service.ScriptService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class ScriptServiceImpl extends AbstractService<Script> implements ScriptService {
    @Resource
    private ScriptMapper ScriptMapper;

}
