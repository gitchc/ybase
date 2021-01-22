package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.Cube;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CubeMapper extends Mapper<Cube> {
    @Select("select * from cube where cubecode = #{cubecode}")
    Cube getCubeByCode(String cubecode);

    @Select("select max(position) from cube ")
    Integer getMaxPosition();

    @Select("select * from cube where autoload = 1 order by position")
    List<Cube> getAutoLoadCues();

    @Update("drop table ${table}")
    void dropTable(@Param(value = "table") String table);

    @Select("select * from cube order by position")
    List<Cube> getAll();

    @Select("select cubecode from cube")
    List<String> getAllCubeCodes();
}