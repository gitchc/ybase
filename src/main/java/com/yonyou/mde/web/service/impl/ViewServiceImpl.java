package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.dao.ViewLayoutMapper;
import com.yonyou.mde.web.dao.ViewMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.View;
import com.yonyou.mde.web.model.ViewLayout;
import com.yonyou.mde.web.model.entity.LayoutDim;
import com.yonyou.mde.web.model.types.LayoutType;
import com.yonyou.mde.web.model.vos.ViewTree;
import com.yonyou.mde.web.model.vos.ViewVO;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.ViewLayoutService;
import com.yonyou.mde.web.service.ViewService;
import com.yonyou.mde.web.utils.SnowID;
import org.apache.commons.lang3.StringUtils;
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
    private ViewLayoutMapper viewLayoutMapper;
    @Resource
    private CubeService cubeService;
    @Resource
    private ViewLayoutService viewLayoutService;

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
            viewTree.setViewid(view.getId());
            viewTree.setCubeid(view.getCubeid());
            viewTree.setTitle(view.getName());
            cubeView.add(viewTree);
        }
        return trees;
    }

    /**
     * @description: 插入视图
     * @param: viewLayout
     * @author chenghch
     */
    private String insertViewLayout(ViewVO viewLayout) {
        View view = new View();
        String viewid = SnowID.nextID();
        view.setId(viewid);
        view.setCubeid(viewLayout.getCubeid());
        view.setName(viewLayout.getViewname());
        view.setPosition(viewLayout.getPosition());
        viewMapper.insert(view);//更新视图
        viewLayout.setViewid(viewid);
        insertPageLayout(viewid, viewLayout.getPage());//页面维处理
        insertRowLayout(viewid, viewLayout.getRow());//行布局处理
        insertColLayout(viewid, viewLayout.getCol());//列布局处理
        return viewid;
    }

    /**
     * @description: 插入视图布局
     * @param: type
     * @param: viewVO
     * @author chenghch
     */
    private void insertLayout(String type, String viewid, List<LayoutDim> layouts) {
        int i = 0;
        for (LayoutDim pageDim : layouts) {
            ViewLayout vlayout = new ViewLayout();
            vlayout.setId(SnowID.nextID());
            vlayout.setViewid(viewid);
            vlayout.setLayouttype(type);
            vlayout.setDimid(pageDim.getDimId());
            vlayout.setPosition(i++);
            viewLayoutService.insert(vlayout);
        }
    }

    /**
     * @description: 更新视图
     * @param: viewLayout
     * @author chenghch
     */
    private void updateViewLayout(ViewVO viewLayout) {
        String viewid = viewLayout.getViewid();
        View view = viewMapper.selectByPrimaryKey(viewid);
        view.setName(viewLayout.getViewname());
        view.setCubeid(viewLayout.getCubeid());
        viewMapper.updateByPrimaryKey(view);//更新视图名称
        viewLayoutMapper.disabled(viewid);//更新老视图布局为软删除
        insertPageLayout(viewid, viewLayout.getPage());//页面维处理
        insertRowLayout(viewid, viewLayout.getRow());//行布局处理
        insertColLayout(viewid, viewLayout.getCol());//列布局处理
        viewLayoutMapper.deleteDisabledView(viewid);//真删除布局
    }

    /**
     * @description: 处理列布局
     * @param: viewid
     * @param: viewLayout
     * @author chenghch
     */
    private void insertColLayout(String viewid, List<LayoutDim> viewLayout) {
        insertLayout(LayoutType.COL, viewid, viewLayout);
    }

    /**
     * @description: 处理行布局
     * @param: viewid
     * @param: viewLayout
     * @author chenghch
     */
    private void insertRowLayout(String viewid, List<LayoutDim> viewLayout) {
        insertLayout(LayoutType.ROW, viewid, viewLayout);
    }

    /**
     * @description: 处理页面维布局
     * @param: viewid
     * @param: viewLayout
     * @author chenghch
     */
    private void insertPageLayout(String viewid, List<LayoutDim> viewLayout) {
        insertLayout(LayoutType.PAGE, viewid, viewLayout);
    }

    @Override
    public String saveview(ViewVO viewLayout) {
        if (StringUtils.isBlank(viewLayout.getViewid())) {//插入
            Integer maxPosition = viewMapper.getMaxPosition(viewLayout.getCubeid());
            viewLayout.setPosition(maxPosition == null ? 0 : maxPosition + 1);
            return insertViewLayout(viewLayout);
        } else {//更新
            updateViewLayout(viewLayout);
            return viewLayout.getViewid();
        }
    }

    @Override
    public void deleteViewByid(String viewid) {
        viewMapper.deleteByPrimaryKey(viewid);
        viewLayoutMapper.deleteViewLayout(viewid);
    }


}
