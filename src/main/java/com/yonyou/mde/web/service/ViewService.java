package com.yonyou.mde.web.service;
import com.yonyou.mde.web.model.View;
import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.model.entity.LayoutDim;
import com.yonyou.mde.web.model.vos.ViewVO;
import com.yonyou.mde.web.model.vos.ViewTree;

import java.util.List;


/**
* @Author chenghch
* @Date 2021-01-27
*/
public interface ViewService extends Service<View> {

    List<ViewTree> findAllViews();

    String saveview(ViewVO viewLayout);

    void deleteViewByid(String viewid);

    ViewVO getView(String cubeid, String viewid);

    LayoutDim getViewPage(String dimid, String scope);
}
