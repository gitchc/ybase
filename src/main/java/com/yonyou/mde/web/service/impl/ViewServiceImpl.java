package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.CubeMapper;
import com.yonyou.mde.web.dao.ViewLayoutMapper;
import com.yonyou.mde.web.dao.ViewMapper;
import com.yonyou.mde.web.model.*;
import com.yonyou.mde.web.model.entity.LayoutDim;
import com.yonyou.mde.web.model.entity.LayoutMember;
import com.yonyou.mde.web.model.types.LayoutType;
import com.yonyou.mde.web.model.vos.ViewTree;
import com.yonyou.mde.web.model.vos.ViewVO;
import com.yonyou.mde.web.service.*;
import com.yonyou.mde.web.utils.MemberUtil;
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
    @Resource
    private CubeMapper cubeMapper;
    @Resource
    private DimensionService dimensionService;
    @Resource
    private MemberService memberService;

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

    @Override
    public ViewVO getView(String cubeid, String viewid) {
        Cube cube = cubeMapper.selectByPrimaryKey(cubeid);
        ViewVO viewVO = new ViewVO();
        viewVO.setCubeid(cubeid);
        viewVO.setViewid(viewid);
        if (cube == null) {
            throw new ServiceException("Cube不存在~");
        }
        String dimids = cube.getDimids();
        List<Dimension> dims = dimensionService.getDimensionByIds(dimids);
        Map<String, Dimension> dimensionMap = new HashMap<>();
        if (StringUtils.isNotBlank(viewid)) {
            for (Dimension dim : dims) {
                dimensionMap.put(dim.getId(), dim);
            }
            List<ViewLayout> layouts = viewLayoutMapper.getLayoutsByViewid(viewid);//获取view的布局结构
            for (ViewLayout layout : layouts) {
                String dimid = layout.getDimid();
                Dimension dimension = dimensionMap.get(dimid);
                String scope = layout.getScope();//成员表达式
                String layouttype = layout.getLayouttype();//判断是行,列,页面维
                if (LayoutType.PAGE.equals(layouttype)) {//页面维
                    viewVO.getPage().add(getDimLayout(dimension, scope));
                } else if (LayoutType.ROW.equals(layouttype)) {//行维度处理
                    viewVO.getRow().add(getDimLayout(dimension, scope));
                } else if (LayoutType.COL.equals(layouttype)) {//列维度处理
                    viewVO.getCol().add(getDimLayout(dimension, scope));
                }
            }
        } else {//视图不存在,则全部设置成页面维
            for (Dimension dim : dims) {
                viewVO.getPage().add(getDimLayout(dim));//全部加到页面维Page
            }
        }
        return viewVO;
    }

    /**
     * @description: 获取多维layout布局
     * @param: pageDims
     * @param: dim
     * @author chenghch
     */
    private LayoutDim getDimLayout(Dimension dim) {
        return getDimLayout(dim, null);
    }

    /**
     * @description: 获取多维layout布局
     * @param: pageDims
     * @param: dim
     * @param: scope 维度范围
     * @author chenghch
     */
    private LayoutDim getDimLayout(Dimension dimension, String scope) {
        List<Member> list;
        String dimid = dimension.getId();
        if (StringUtils.isBlank(scope)) {//范围为空,去全部维度集合
            list = memberService.getMembersByDimid(dimid);
        } else {
            list = memberService.getMembersByScope(dimid, scope);
        }
        List<LayoutMember> res = new ArrayList<>(list.size());
        for (Member member : list) {//重构页面元素
            LayoutMember vo = new LayoutMember();
            vo.setId(member.getId());
            vo.setCode(member.getCode());
            vo.setGeneration(member.getGeneration());
            vo.setDimCode(dimension.getCode());
            vo.setDisplayName(MemberUtil.getDisplayName(member));
            res.add(vo);
        }
        LayoutDim layoutdim = new LayoutDim();
        layoutdim.setDimId(dimid);
        layoutdim.setDimName(dimension.getName());
        layoutdim.setDimCode(dimension.getCode());
        layoutdim.setSelected(res.get(0).getCode());
        layoutdim.setOptions(res);
        return layoutdim;
    }

}
