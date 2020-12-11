package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Attrvalue;
import org.apache.ibatis.annotations.Delete;

public interface AttrvalueMapper extends Mapper<Attrvalue> {

    @Delete("delete from attrvalue where id in (select id from(select id from attr  where dimid = #{dimid} )ctr)")
    void deleteByDim(String dimid);
}