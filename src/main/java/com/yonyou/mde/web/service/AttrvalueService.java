package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.model.AttrValueVO;
import com.yonyou.mde.web.model.Attrvalue;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface AttrvalueService extends Service<Attrvalue> {

    List<Map<String,String>> getAllAttrValues(String dimid);

    void updateValue(AttrValueVO attrValueVO);

    void deleteByDimIdAndName(AttrValueVO attrValueVO);
}
