package com.yonyou.mde.web.service;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface CubeService extends Service<Cube> {

    void insertCube(Cube cube);

    void deleteCubeById(String id);

    void saveCube(Cube cube);

    void reloadData(String id);

    List<Cube> getAutoLoadCubes();

    void loadAutoCube();
}
