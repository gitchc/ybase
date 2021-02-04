package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Member;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface DimensionService extends Service<Dimension> {

    String insertDim(Dimension member) throws ServiceException;

    List<Dimension> getAllDims();

    void switchDim(Dimension member);

    void delDim(String dimid);

    void updateDim(Dimension member);

    String getDimidByCode(String dimCode);

    List<Dimension> getDimensionByIds(String dimids);

    Dimension getDimensionById(String dimid);
}
