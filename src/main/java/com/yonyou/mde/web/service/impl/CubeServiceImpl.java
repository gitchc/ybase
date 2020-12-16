package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.utils.SnowID;
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
    private com.yonyou.mde.web.dao.CubeMapper CubeMapper;

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

    @Override
    public void reloadData(String id) {
        Cube cube = findById(id);
        //todo reloadcube cube.getcode
    }

    private void updateCube(Cube cube) {
        update(cube);
        //todo reloadcube
    }
}
