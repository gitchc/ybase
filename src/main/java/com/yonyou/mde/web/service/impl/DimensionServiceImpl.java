package com.yonyou.mde.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.AttrMapper;
import com.yonyou.mde.web.dao.AttrvalueMapper;
import com.yonyou.mde.web.dao.DimensionMapper;
import com.yonyou.mde.web.dao.MemberMapper;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.types.DataType;
import com.yonyou.mde.web.model.types.MemberType;
import com.yonyou.mde.web.service.DimensionService;
import com.yonyou.mde.web.utils.SnowID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
public class DimensionServiceImpl extends AbstractService<Dimension> implements DimensionService {
    @Resource
    private DimensionMapper dimensionMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private AttrMapper attrMapper;
    @Resource
    private AttrvalueMapper attrvalueMapper;

    public String insertDim(Dimension dimension) throws ServiceException {
        List<Dimension> old = dimensionMapper.getDimsByCode(dimension.getCode());
        if (old.size() > 0) {
            throw new ServiceException(dimension.getCode()+"维度编码不能重复!");
        }
        dimension.setId(SnowID.nextID());
        dimension.setPid("-1");
        String pid = dimension.getPid();
        dimension.setMembertype(MemberType.DIM);
        dimension.setDatatype(DataType.MAROLLUP);//手动上卷
        dimension.setGeneration(-1);
        dimension.setDimid("-1");
        dimension.setUnicode(dimension.getCode());
        dimension.setUnipos("0");
        Integer maxPos = dimensionMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        dimension.setPosition(nextPos);
        Member newMember = new Member();
        BeanUtil.copyProperties(dimension, newMember);
        memberMapper.insert(newMember);
        return newMember.getId();
    }


    public List<Dimension> getAllDims() {
        return dimensionMapper.selectAllDim();
    }

    @Override
    @CacheEvict(cacheNames = "CubeDims")
    public void switchDim(Dimension dimension) {
        dimensionMapper.switchDim(dimension.getDimid(), dimension.getDatatype());
        Integer olddatatype;
        if (dimension.getDatatype() == DataType.MAROLLUP) {
            olddatatype = DataType.AUTOROLLUP;
        } else {
            olddatatype = DataType.MAROLLUP;
        }
        memberMapper.switchMember(dimension.getDimid(), olddatatype, dimension.getDatatype());
    }

    @Override
    public void delDim(String dimid) {
        attrvalueMapper.deleteByDim(dimid);//删除属性值
        attrMapper.deleteByDim(dimid);//删除属性
        dimensionMapper.delDim(dimid);//删除维度
    }

    @Override
    @CacheEvict(cacheNames = "CubeDims")
    public void updateDim(Dimension member) {
        dimensionMapper.updateDim(member.getId(), member.getName());
    }

    @Override
    public String getDimidByCode(String dimCode) {
        return dimensionMapper.getDimIdByCode(dimCode);
    }

    /**
     * @description: 启用缓存
     * @param: dimids
     * @author chenghch
     */
    @Override
    @Cacheable(cacheNames = "CubeDims")
    public List<Dimension> getDimensionByIds(String dimids) {
        return dimensionMapper.getDimensionByIds(dimids.split(","));
    }

    @Override
    public Dimension getDimensionById(String dimid) {
        return dimensionMapper.getDimById(dimid);
    }


}
