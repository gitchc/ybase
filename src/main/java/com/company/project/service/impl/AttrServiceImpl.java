package com.company.project.service.impl;

import com.company.project.core.AbstractService;
import com.company.project.core.ServiceException;
import com.company.project.dao.AttrMapper;
import com.company.project.model.Attr;
import com.company.project.model.ColVo;
import com.company.project.service.AttrService;
import com.company.project.utils.SnowID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class AttrServiceImpl extends AbstractService<Attr> implements AttrService {
    @Resource
    private AttrMapper attrMapper;

    @Override
    public List<ColVo> getAttrByDimid(String dimid) {
        List<ColVo> cols = new ArrayList<>();
        List<Attr> attrs = attrMapper.getAttrByDimid(dimid);
        if (attrs.size() > 0) {
            cols.add(new ColVo("code", "编码", 150));
            cols.add(new ColVo("name", "名称", 150));
        }
        for (Attr attr : attrs) {
            ColVo colvo = new ColVo(attr.getAttrname());
            cols.add(colvo);
        }
        return cols;
    }

    @Override
    public void insertAttr(Attr attr) {
        Attr attr1 = attrMapper.getIdByDimidAndAttrName(attr.getDimid(), attr.getAttrname());
        if (attr1 != null) {
            throw new ServiceException("属性不能重名!");
        } else {
            attr.setId(SnowID.nextID());
            insert(attr);
        }
    }
}
