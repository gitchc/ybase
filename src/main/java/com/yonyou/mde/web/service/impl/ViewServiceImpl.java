package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.dao.ViewMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.View;
import com.yonyou.mde.web.model.vos.ViewTree;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.ViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author chenghch
 * @Date 2021-01-27
 */
@Service
@Transactional
public class ViewServiceImpl extends AbstractService<View> implements ViewService {
    @Resource
    private ViewMapper viewMapper;
    @Resource
    private CubeService cubeService;

    @Override
    public List<ViewTree> findAllViews() {
        List<ViewTree> trees = new ArrayList<>();
        List<Cube> cubes = cubeService.getAll();//获取所有模型
        List<View> views = viewMapper.getAll();//所有视图
        Map<String, ViewTree> cubeMap = new HashMap<>();
        for (Cube cube : cubes) {
            ViewTree viewTree = new ViewTree();
            String id = cube.getId();
            viewTree.setId(id);
            viewTree.setCubeid(id);
            viewTree.setTitle("&#10066; " + cube.getCubename());
            cubeMap.put(id, viewTree);
            trees.add(viewTree);
        }
        for (View view : views) {
            ViewTree cubeView = cubeMap.get(view.getCubeid());
            ViewTree viewTree = new ViewTree();
            viewTree.setId(view.getId());
            viewTree.setCubeid(view.getCubeid());
            viewTree.setTitle(view.getName());
            cubeView.add(viewTree);
        }
        return trees;
    }
}
