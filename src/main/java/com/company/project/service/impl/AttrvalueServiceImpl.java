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
        Attr attr = attrMapper.getIdByDimidAndAttrName(attrValueVO.getDimid(), attrValueVO.getAttrname());
        if (attr != null) {
            Attrvalue nValue = attrvalueMapper.getAttrValue(attr.getId(), attrValueVO.getCode());//查询属性值是否存在
            Attrvalue attrvalue = new Attrvalue();
            attrvalue.setAttrid(attr.getId());
            attrvalue.setMembercode(attrValueVO.getCode());
            attrvalue.setAttrvalue(attrValueVO.getAttrvalue());
            if (nValue != null) {
                attrvalue.setId(nValue.getId());
                update(attrvalue);
            } else {
                attrvalue.setId(SnowID.nextID());
                insert(attrvalue);
            }

        } else {
            throw new ServiceException(StrUtil.format("属性:[{}]不存在!", attrValueVO.getAttrname()));
        }
    }

    @Override
    public void deleteByDimIdAndName(AttrValueVO attrValueVO) {
        attrvalueMapper.deleteByDimIdAndName(attrValueVO.getAttrname(),attrValueVO.getDimid());
        attrMapper.deleteByDimIdAndName(attrValueVO.getAttrname(),attrValueVO.getDimid());
    }
}
