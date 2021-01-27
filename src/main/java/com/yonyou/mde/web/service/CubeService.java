package com.yonyou.mde.web.service;

import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.model.vos.ViewVO;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface CubeService extends Service<Cube> {

    void insertCube(Cube cube);

    List<String> getDimCodes(String cubeCode);

    Map<String, String> getDimMap(String cubeid);

    void deleteCubeById(String id);

    void saveCube(Cube cube);

    void reloadData(String id);

    List<Cube> getAutoLoadCubes();

    void loadAutoCube();

    Map<String, List<String>> getCubeMembers(String cubecode);

    List<Cube> getAll();

    ViewVO getView(String id, String viewid);

    Cube getCubeById(String cubeid);

    List<Cube> getAllCubeMeta();
}
