package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Cube;
import org.apache.ibatis.annotations.Select;

public interface CubeMapper extends Mapper<Cube> {
    @Select("select * from cube where cubecode = #{cubecode}")
    Cube getCubeByCode(String cubecode);
    @Select("select max(position) from cube ")
    Integer getMaxPosition();
}