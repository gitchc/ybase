package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.ViewLayout;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ViewLayoutMapper extends Mapper<ViewLayout> {
    @Update("update view_layout set version=1 where viewid = #{viewid}")
    void disabled(String viewid);

    @Delete("delete from view_layout where viewid = #{viewid} and version = 1")
    void deleteDisabledView(String viewid);

    @Delete("delete from view_layout where viewid = #{viewid}")
    void deleteViewLayout(String viewid);

    @Select("select * from view_layout where viewid= #{viewid} and version = 0")
    List<ViewLayout> getLayoutsByViewid(String viewid);
}