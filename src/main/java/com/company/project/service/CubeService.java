package com.company.project.service;
import com.company.project.model.Cube;
import com.company.project.core.Service;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface CubeService extends Service<Cube> {

    void insertCube(Cube cube);

    void deleteCubeById(String id);

    void saveCube(Cube cube);

    void reloadData(String id);
}
