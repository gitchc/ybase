package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.Cube;
import org.apache.ibatis.annotations.Select;

public interface CubeMapper extends Mapper<Cube> {
    @Select("select * from cube where cubecode = #{cubecode}")
    Cube getCubeByCode(String cubecode);
    @Select("select max(position) from cube ")
    Integer getMaxPosition();
}