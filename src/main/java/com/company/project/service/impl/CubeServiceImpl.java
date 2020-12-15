package com.company.project.service.impl;

import com.company.project.core.AbstractService;
import com.company.project.core.ServiceException;
import com.company.project.dao.CubeMapper;
import com.company.project.model.Cube;
import com.company.project.service.CubeService;
import com.company.project.utils.SnowID;
import org.apache.commons.lang3.StringUtils;
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
            cube.setPosition(maxpos == null ? 0 : maxpos + 1);
            insert(cube);
        }
        if (cube.getAutoload() == 1) {
            //todo load数据
        }
    }

    @Override
    public void deleteCubeById(String id) {
        Cube cube = findById(id);
        deleteById(id);
        //todo 卸载cube内存 cube.getcode
    }

    @Override
    public void saveCube(Cube cube) {
        String cubeId = cube.getId();
        if (StringUtils.isNotBlank(cubeId)) {
            updateCube(cube);
        } else {
            insertCube(cube);
        }
    }

    private void updateCube(Cube cube) {
        update(cube);
        //todo reloadcube
    }
}
