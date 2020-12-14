package com.company.project.service;
import com.company.project.model.Attr;
import com.company.project.core.Service;
import com.company.project.model.ColVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface AttrService extends Service<Attr> {

    List<ColVo> getAttrByDimid(String dimid);

    void insertAttr(Attr attr);
}
