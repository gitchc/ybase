package com.company.project.service;
import com.company.project.model.Attr;
import com.company.project.core.Service;
import com.company.project.model.ColVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface AttrService extends Service<Attr> {

    List<ColVO> getAttrByDimid(String dimid);

    void insertAttr(Attr attr);
}
