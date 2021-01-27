package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.View;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ViewMapper extends Mapper<View> {
    @Select("select * from view order by cubeid,position")
    List<View> getAll();

    @Select("select max(position) from view where cubeid = #{cubeid}")
    Integer getMaxPosition(String cubeid);
}