package com.company.project.service.impl;

import com.company.project.core.AbstractService;
import com.company.project.core.ServiceException;
import com.company.project.dao.CubeMapper;
import com.company.project.model.Cube;
import com.company.project.service.CubeService;
import com.company.project.utils.SnowID;
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

    @Override
    public void insertCube(Cube cube) {
        Cube oldcube = CubeMapper.getCubeByCode(cube.getCubecode());
        if (oldcube != null) {
            throw new ServiceException("模型编码不能重复!");
        } else {
            cube.setId(SnowID.nextID());
            Integer maxpos = CubeMapper.getMaxPosition();
            cube.setPostion(maxpos == null ? 0 : maxpos + 1);
            insert(cube);
        }

    }
}
