package com.company.project.service.impl;

import cn.hutool.core.util.StrUtil;
import com.company.project.core.AbstractService;
import com.company.project.core.ServiceException;
import com.company.project.dao.AttrMapper;
import com.company.project.dao.AttrvalueMapper;
import com.company.project.model.Attr;
import com.company.project.model.AttrValueVO;
import com.company.project.model.Attrvalue;
import com.company.project.service.AttrvalueService;
import com.company.project.utils.MemberUtil;
import com.company.project.utils.SnowID;
import com.company.project.utils.SortUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class AttrvalueServiceImpl extends AbstractService<Attrvalue> implements AttrvalueService {
    @Resource
    private AttrvalueMapper attrvalueMapper;
    @Resource
    private AttrMapper attrMapper;

    @Override
    public List<Map<String, String>> getAllAttrValues(String dimid) {
        List<AttrValueVO> allAttrValues = attrvalueMapper.getAllAttrValues(dimid); //获取所有属性
        allAttrValues = SortUtils.sort(allAttrValues);
        Map<String, Map<String, String>> finalmap = new HashMap<>();
        List<Map<String, String>> finalattrs = new ArrayList<>();
        for (AttrValueVO item : allAttrValues) { //合并
            String code = item.getCode();
            Map<String, String> attrsMap = finalmap.get(code);
            if (attrsMap == null) {
                attrsMap = new HashMap<>();
                finalmap.put(code, attrsMap);
                attrsMap.put("code", item.getCode());
                attrsMap.put("name", item.getName());
                finalattrs.add(attrsMap);
            }
            attrsMap.put(item.getAttrname(), MemberUtil.toString(item.getAttrvalue()));
        }

        return finalattrs;
    }

    @Override
    public void updateValue(AttrValueVO attrValueVO) {
        String attrid = attrValueVO.getAttrid();
        if (StringUtils.isBlank(attrid)) {
            Attr attr = attrMapper.getIdByDimidAndAttrName(attrValueVO.getDimid(), attrValueVO.getAttrname());
            if (attr == null) {
                throw new ServiceException(StrUtil.format("属性:[{}]不存在!", attrValueVO.getAttrname()));
            }
            attrid = attr.getId();
        }

        Attrvalue nValue = attrvalueMapper.getAttrValue(attrid, attrValueVO.getCode());//查询属性值是否存在
        if (nValue != null) {//有就更改
            nValue.setAttrvalue(attrValueVO.getAttrvalue());
            update(nValue);
        } else {//没有就新增
            nValue = new Attrvalue();
            nValue.setAttrid(attrid);
            nValue.setMembercode(attrValueVO.getCode());
            nValue.setAttrvalue(attrValueVO.getAttrvalue());
            nValue.setId(SnowID.nextID());
            insert(nValue);
        }

    }

    @Override
    public void deleteByDimIdAndName(AttrValueVO attrValueVO) {
        String attrid = attrValueVO.getAttrid();
        if (StringUtils.isNotBlank(attrid)) {
            attrvalueMapper.deleteByAttrid(attrid);
            attrMapper.deleteByPrimaryKey(attrid);
        } else {
            attrvalueMapper.deleteByDimIdAndName(attrValueVO.getAttrname(), attrValueVO.getDimid());
            attrMapper.deleteByDimIdAndName(attrValueVO.getAttrname(), attrValueVO.getDimid());
        }
    }
}
