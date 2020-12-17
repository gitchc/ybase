package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.CubeMapper;
import com.yonyou.mde.web.dao.MemberMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.service.AttrvalueService;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DataService.CubeManager;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.utils.MockDataUtils;
import com.yonyou.mde.web.utils.SnowID;
import com.yonyou.mde.web.utils.SortUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
@Service
@Transactional
public class CubeServiceImpl extends AbstractService<Cube> implements CubeService {
    @Resource
    private CubeMapper CubeMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberService memberService;
    @Resource
    DataSource dataSource;
    @Resource
    private DataSourceConfig dataSourceConfig;
    @Resource
    private AttrvalueService attrvalueService;

    @Override
    public void insertCube(Cube cube) {
        String id = SnowID.nextID();
        Cube oldcube = CubeMapper.getCubeByCode(cube.getCubecode());
        if (oldcube != null) {
            throw new ServiceException("模型编码不能重复!");
        } else {
            cube.setId(id);
            Integer maxpos = CubeMapper.getMaxPosition();
            cube.setPosition(maxpos == null ? 0 : maxpos + 1);
            insert(cube);
        }
        CheckTable(cube);//检查SQL表是否需要创建或者更新
        ReloadModeAndData(cube, false);//检查是否需要重构cube
    }

    //检查是否已需要重新load数据和模型
    public void ReloadModeAndData(Cube cube, boolean foceReload) {
        String tableName = "TB" + cube.getId();
        String loadSql = cube.getLoadsql();
        if (cube.getAutosql() == 1) {
            loadSql = "";
        } else {
            tableName = "";
        }
        if (cube.getAutoload() == 1 || foceReload) {
            List<Member> dims = memberMapper.selectByIds(cube.getDimids());
            Map<String, List<DimColumn>> allmembers = getMembers(dims);
            try {
                CubeManager.loadCubeData(dataSourceConfig, cube.getCubecode(), tableName, loadSql, dims, allmembers);
            } catch (MdeException e) {
                e.printStackTrace();
            }
        }
    }

    private Map<String, List<DimColumn>> getMembers(List<Member> dims) {
        Map<String, List<DimColumn>> result = new HashMap<>();
        for (Member dim : dims) {
            String dimId = dim.getId();
            List<DimColumn> dimColumns = result.get(dimId);
            if (dimColumns == null) {
                dimColumns = new ArrayList<>();
                result.put(dimId, dimColumns);
            }
            List<Member> members = memberService.getMembersBydimid(dimId);
            members = SortUtils.sortMember(members);
            Map<String, Map<String, Object>> attrValues = attrvalueService.getAttrValues(dimId);
            Map<String, String> parentMap = new HashMap<>();
            parentMap.put(dimId, dim.getCode());
            for (Member member : members) {
                String code = member.getCode();
                String id = member.getId();
                String pid = member.getPid();
                String pkParent = parentMap.get(pid);
                parentMap.put(id, code);
                DimColumn dimColumn = new DimColumn();//创建模型需要的数据结构
                dimColumn.setPk(code);
                dimColumn.setCode(code);
                dimColumn.setPkParent(pkParent);
                dimColumn.setCodeParent(pkParent);
                dimColumn.setExtraParam(attrValues.get(id)); //设置属性
                dimColumns.add(dimColumn);
            }
        }
        return result;
    }

    @Override
    public void deleteCubeById(String id) {
        Cube cube = findById(id);
        deleteById(id);
        CubeMapper.dropTable("TB" + cube.getId());
        CubeManager.removeData(cube.getCubecode());
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

    //仅仅load数据
    @Override
    public void reloadData(String id) {
        Cube cube = findById(id);
        ReloadModeAndData(cube, true);//检查是否需要重构cube,暂时用一个开关控制
    }

    @Override
    public List<Cube> getAutoLoadCubes() {
        return CubeMapper.getAutoLoadCues();
    }
    //加载启动需要加载的cube
    @Override
    public void loadAutoCube() {
        for (Cube autoLoadCube : getAutoLoadCubes()) {
            ReloadModeAndData(autoLoadCube, false);
        }
    }

    private void CheckTable(Cube cube) {
        String tableName = "TB" + cube.getId();
        String dimids = cube.getDimids();
        List<Member> dims = memberMapper.selectByIds(dimids);
        if (cube.getAutosql() == 1) {
            try {
                MockDataUtils.createTable(dataSource, tableName, dims);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCube(Cube cube) {
        Cube oldcube = findById(cube.getId());
        update(cube);
        if (!oldcube.getDimids().equals(cube.getDimids())) {
            CheckTable(cube);//重建事实表
            ReloadModeAndData(cube, false);//重新load模型和数据
        }

    }
}
