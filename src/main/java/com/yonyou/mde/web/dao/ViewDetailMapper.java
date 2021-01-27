package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.ViewDetail;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Update;

public interface ViewDetailMapper extends Mapper<ViewDetail> {
    @Update("update view_detail set version=1 where viewid = #{viewid}")
    void disabled(String viewid);

    @Delete("delete from view_detail where viewid = #{viewid} and version = 1")
    void deleteDisabledView(String viewid);

    @Delete("delete from view_detail where viewid = #{viewid}")
    void deleteView(String viewid);
}