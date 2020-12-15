package com.company.project.service.impl;

import com.company.project.dao.CubeMapper;
import com.company.project.model.Cube;
import com.company.project.service.CubeService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
@Service
@Transactional
public class CubeServiceImpl extends AbstractService<Cube> implements CubeService {
    @Resource
    private CubeMapper CubeMapper;

}
