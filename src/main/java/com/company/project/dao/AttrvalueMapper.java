package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Attrvalue;
import org.apache.ibatis.annotations.Delete;

public interface AttrvalueMapper extends Mapper<Attrvalue> {

    @Delete("delete from attrvalue where attrid in (select id from(select id from attr  where dimid = #{dimid} )cd)")
    void deleteByDim(String dimid);

    @Delete("delete from attrvalue where membercode in (select code from(select code from member  where id = #{id} or unicode like #{unicode})cm)")
    void deleteByMemberid(String id, String unicode);
}