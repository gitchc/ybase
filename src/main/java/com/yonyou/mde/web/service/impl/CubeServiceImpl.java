package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.dto.DimColumn;
import com.yonyou.mde.error.MdeException;
import com.yonyou.mde.web.configurer.DataSourceConfig;
import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.CubeMapper;
import com.yonyou.mde.web.dao.DimensionMapper;
import com.yonyou.mde.web.dao.ViewLayoutMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.ViewLayout;
import com.yonyou.mde.web.model.entity.LayoutDim;
import com.yonyou.mde.web.model.entity.LayoutMember;
import com.yonyou.mde.web.model.types.DataType;
import com.yonyou.mde.web.model.types.LayoutType;
import com.yonyou.mde.web.model.vos.ViewVO;
import com.yonyou.mde.web.service.AttrvalueService;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DataService.CubeManager;
import com.yonyou.mde.web.service.DataService.MockDataManager;
import com.yonyou.mde.web.service.DimensionService;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.utils.MemberUtil;
import com.yonyou.mde.web.utils.SnowID;
import com.yonyou.mde.web.utils.SortUtil;
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
    private CubeMapper cubeMapper;
    @Resource
    private DimensionService dimensionService;
    @Resource
    private MemberService memberService;
    @Resource
    DataSource dataSource;
    @Resource
    private DataSourceConfig dataSourceConfig;
    @Resource
    private AttrvalueService attrvalueService;
    @Resource
    MockDataManager mockDataManager;

    @Override
    public void insertCube(Cube cube) {
        String id = SnowID.nextID();
        Cube oldcube = cubeMapper.getCubeByCode(cube.getCubecode());
        if (oldcube != null) {
            throw new ServiceException("模型编码不能重复!");
        } else {
            cube.setId(id);
            Integer maxpos = cubeMapper.getMaxPosition();
            cube.setPosition(maxpos == null ? 0 : maxpos + 1);
            insert(cube);
        }
        if (StringUtils.isNotBlank(cube.getDimids())) {
            CheckTable(cube);//检查SQL表是否需要创建或者更新
            ReloadModeAndData(cube, false);//检查是否需要重构cube
        }
    }

    //检查是否已需要重新load数据和模型
    private void ReloadModeAndData(Cube cube, boolean foceReload) {
        String tableName = cube.getCubecode();
        String loadSql = cube.getLoadsql();
        if (cube.getAutosql() == 1) {
            loadSql = "";
        } else {
            tableName = "";
        }
        if (cube.getAutoload() == 1 || foceReload) {
            List<Dimension> dims = dimensionService.getDimensionByIds(cube.getDimids());
            Map<String, List<DimColumn>> allmembers = getMembers(dims);
            try {
                CubeManager.loadCubeData(dataSourceConfig, cube.getCubecode(), tableName, loadSql, dims, allmembers);
            } catch (MdeException e) {
                e.printStackTrace();
            }
        }
    }

    //获取Cube的所有成员
    public Map<String, List<String>> getCubeMembers(String cubecode) {
        Map<String, List<String>> cubeMap = new HashMap<>();
        Cube cube = cubeMapper.getCubeByCode(cubecode);
        if (cube == null) {
            throw new ScriptException(cubecode + "不存在!");
        }
        List<Dimension> dims = dimensionService.getDimensionByIds(cube.getDimids());
        for (Dimension dim : dims) {
            List<String> membercodes = memberService.getMemberCodesByDimid(dim.getId());
            cubeMap.put(dim.getCode(), membercodes);
        }
        return cubeMap;
    }

    @Override
    public List<Cube> getAll() {
        return cubeMapper.getAll();
    }

    @Override
    public Cube getCubeById(String cubeid) {
        return cubeMapper.selectByPrimaryKey(cubeid);
    }

    /**
     * @description: 获取cube的名称和编码
     * @param:
     * @author chenghch
     */
    @Override
    public List<Cube> getAllCubeMeta() {
        return cubeMapper.getAllCubeCodes();
    }

    @Override
    public List<String> getDimCodes(String cubeCode) {
        List<String> codes = new ArrayList<>();
        Cube cube = cubeMapper.getCubeByCode(cubeCode);
        if (cube == null) {
            throw new ScriptException(cubeCode + "不存在!");
        }
        String dimids = cube.getDimids();
        List<Dimension> dims = dimensionService.getDimensionByIds(dimids);
        for (String dimid : dimids.split(",")) {
            for (Dimension dim : dims) {
                if (dim.getId().equals(dimid)) {
                    codes.add(dim.getCode());
                    break;
                }
            }
        }
        return codes;
    }

    @Override
    public Map<String, String> getDimMap(String cubeid) {
        Map<String, String> codesMap = new HashMap<>();
        Cube cube = cubeMapper.selectByPrimaryKey(cubeid);
        if (cube == null) {
            throw new ScriptException(cubeid + "不存在!");
        }
        List<Dimension> dims = dimensionService.getDimensionByIds(cube.getDimids());
        for (Dimension dim : dims) {
            codesMap.put(dim.getId(), dim.getCode());
        }
        return codesMap;
    }

    /**
     * @description: 组织多维模型需要的数据
     * @param: dims
     * @author chenghch
     */

    private Map<String, List<DimColumn>> getMembers(List<Dimension> dims) {
        Map<String, List<DimColumn>> result = new HashMap<>();
        for (Dimension dim : dims) {
            String dimId = dim.getId();
            String dimcode = dim.getCode();
            List<DimColumn> dimColumns = result.get(dimcode);
            if (dimColumns == null) {
                dimColumns = new ArrayList<>();
                result.put(dimcode, dimColumns);
            }
            List<Member> members = memberService.getMembersByDimid(dimId);
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
                dimColumn.setParentPk(pkParent);
                dimColumn.setParentCode(pkParent);
                boolean isrollup = getRowUp(member);
                dimColumn.setRollUp(isrollup);//todo 设置节点的自动卷积属性
                dimColumn.setWeight(member.getWeight());
                dimColumn.setExtraParam(attrValues.get(code)); //设置属性
                dimColumns.add(dimColumn);
            }
        }
        return result;
    }

    //判断节点类型
    private boolean getRowUp(Member member) {
        if (member.getDatatype() == DataType.MAROLLUP) {//非自动卷积类型
            return false;
        }
        return true;
    }

    @Override
    public void deleteCubeById(String id) {
        Cube cube = findById(id);
        deleteById(id);
        try {
            if (cube.getAutosql() == 1) {
                cubeMapper.dropTable(cube.getCubecode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        CubeManager.removeData(cube.getCubecode());
    }

    @Override
    public void saveCube(Cube cube) {
        cube.setCubecode(cube.getCubecode().trim());
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
        return cubeMapper.getAutoLoadCues();
    }

    //加载启动需要加载的cube
    @Override
    public void loadAutoCube() {
        for (Cube autoLoadCube : getAutoLoadCubes()) {
            ReloadModeAndData(autoLoadCube, false);
        }
    }


    private void CheckTable(Cube cube) {
        String tableName = cube.getCubecode();
        String dimids = cube.getDimids();
        List<Dimension> dims = dimensionService.getDimensionByIds(dimids);
        if (cube.getAutosql() == 1) {
            try {
                mockDataManager.createTable(dataSource, tableName, dims);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateCube(Cube cube) {
        Cube oldcube = findById(cube.getId());
        cube.setPosition(oldcube.getPosition());
        update(cube);
        if (!StringUtils.equals(oldcube.getDimids(), cube.getDimids())) {//维度不匹配,重构模型
            CheckTable(cube);//重建事实表
            ReloadModeAndData(cube, false);//重新load模型和数据
        }

    }
}
