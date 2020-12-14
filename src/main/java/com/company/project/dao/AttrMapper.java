package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Attr;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AttrMapper extends Mapper<Attr> {
    @Delete("delete from attr where dimid = #{dimid}")
    void deleteByDim(String dimid);

    @Select("select * from attr where dimid = #{dimid} order by attrname asc")
    List<Attr> getAttrByDimid(String dimid);

    @Select("select * from attr where dimid = #{dimid} and attrname=#{attrname}")
    Attr getIdByDimidAndAttrName(String dimid, String attrname);

    @Delete("delete from attr where dimid = #{dimid} and attrname=#{attrname}")
    void deleteByDimIdAndName(String attrname, String dimid);
}