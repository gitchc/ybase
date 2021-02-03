package com.yonyou.mde.web.service;
import com.yonyou.mde.web.model.ViewLayout;
import com.yonyou.mde.web.core.Service;
import org.apache.ibatis.annotations.Update;


/**
* @Author chenghch
* @Date 2021-01-27
*/
public interface ViewLayoutService extends Service<ViewLayout> {
    void updateScope(String viewid, String dimid, String layouttype, String scope);
}
