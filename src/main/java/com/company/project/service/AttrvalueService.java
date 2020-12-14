package com.company.project.service;

import com.company.project.core.Service;
import com.company.project.model.AttrValueVO;
import com.company.project.model.Attrvalue;

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
