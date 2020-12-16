package com.yonyou.mde.web.service;
import com.yonyou.mde.web.model.Attr;
import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.model.ColVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface AttrService extends Service<Attr> {

    List<ColVO> getAttrByDimid(String dimid);

    void insertAttr(Attr attr);
}
