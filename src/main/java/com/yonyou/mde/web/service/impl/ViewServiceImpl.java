package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.dao.ViewDetailMapper;
import com.yonyou.mde.web.dao.ViewMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.View;
import com.yonyou.mde.web.model.ViewDetail;
import com.yonyou.mde.web.model.entity.PageDim;
import com.yonyou.mde.web.model.entity.ViewLayout;
import com.yonyou.mde.web.model.vos.ViewTree;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.ViewDetailService;
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
    private ViewDetailMapper viewDetailMapper;
    @Resource
    private CubeService cubeService;
    @Resource
    private ViewDetailService viewDetailService;

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

    /**
     * @description: 插入视图
     * @param: viewLayout
     * @author chenghch
     */
    private String insertViewLayout(ViewLayout viewLayout) {
        View view = new View();
        String viewid = SnowID.nextID();
        view.setId(viewid);
        view.setCubeid(viewLayout.getCubeid());
        view.setName(viewLayout.getViewname());
        view.setPosition(viewLayout.getPosition());
        viewMapper.insert(view);//更新视图
        viewLayout.setViewid(viewid);
        insertLayout("page", viewLayout);
        insertLayout("row", viewLayout);
        insertLayout("col", viewLayout);
        return viewid;
    }

    /**
     * @description: 插入视图布局
     * @param: type
     * @param: viewLayout
     * @author chenghch
     */
    private void insertLayout(String type, ViewLayout viewLayout) {
        int i = 0;
        List<PageDim> layout = new ArrayList<>();
        if ("page".equals(type)) {
            layout = viewLayout.getPage();
        } else if ("row".equals(type)) {
            layout = viewLayout.getRow();
        } else if ("col".equals(type)) {
            layout = viewLayout.getCol();
        }
        for (PageDim pageDim : layout) {
            ViewDetail viewDetail = new ViewDetail();
            viewDetail.setId(SnowID.nextID());
            viewDetail.setViewid(viewDetail.getViewid());
            viewDetail.setLayouttype(type);
            viewDetail.setDimid(pageDim.getDimId());
            viewDetail.setPosition(i++);
            viewDetailService.insert(viewDetail);
        }
    }

    /**
     * @description: 更新视图
     * @param: viewLayout
     * @author chenghch
     */
    private void updateViewLayout(ViewLayout viewLayout) {
        String viewid = viewLayout.getViewid();
        View view = viewMapper.selectByPrimaryKey(viewid);
        view.setName(viewLayout.getViewname());
        view.setCubeid(viewLayout.getCubeid());
        viewMapper.updateByPrimaryKey(view);//更新视图名称
        viewDetailMapper.disabled(viewid);//更新老视图布局为软删除
        insertLayout("page", viewLayout);
        insertLayout("row", viewLayout);
        insertLayout("col", viewLayout);
        viewDetailMapper.deleteDisabledView(viewid);//真删除布局
    }

    @Override
    public String saveview(ViewLayout viewLayout) {
        if (StringUtils.isBlank(viewLayout.getViewid())) {//插入
            Integer maxPosition = viewMapper.getMaxPosition(viewLayout.getCubeid());
            viewLayout.setPosition(maxPosition + 1);
            return insertViewLayout(viewLayout);
        } else {//更新
            updateViewLayout(viewLayout);
            return viewLayout.getViewid();
        }
    }


}
