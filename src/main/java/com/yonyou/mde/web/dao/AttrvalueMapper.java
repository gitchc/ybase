package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.AttrValueVO;
import com.yonyou.mde.web.model.Attrvalue;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AttrvalueMapper extends Mapper<Attrvalue> {

    @Delete("delete from attrvalue where attrid in (select id from(select id from attr  where dimid = #{dimid} )cd)")
    void deleteByDim(String dimid);

    @Delete("delete from attrvalue where membercode in (select code from(select code from member  where id = #{id} or unicode like #{unicode})cm)")
    void deleteByMemberid(String id, String unicode);

    @Select("select b.name," +
            "       b.code," +
            "       attrName," +
            "       attrValue," +
            "       unipos from(select dimid, name , code , unipos from member a   where dimid = #{dimid}) b left join attr c" +
            "  on b.dimid = c.dimid left join attrvalue d on c.id = d.attrid and b.code =d.memberCode ")
    List<AttrValueVO> getAllAttrValues(String dimid);

    @Select("select * from attrvalue where attrid = #{attrid} and membercode = #{code}")
    Attrvalue getAttrValue(String attrid, String code);

    @Delete("delete from attrvalue where attrid in (select id from(select id from attr  where dimid = #{dimid} and attrname=#{attrname})cd) ")
    void deleteByDimIdAndName(String attrname, String dimid);

    @Delete("delete from attrvalue where  attrid = #{attrid} ")
    void deleteByAttrid(String attrid);

    @Select("select a.memberCode,b.attrName,a.attrValue from attrvalue a left join attr b on a.attrid = b.id where dimid = #{dimid}")
    List<AttrValueVO> getAttrValues(String dimid);
}