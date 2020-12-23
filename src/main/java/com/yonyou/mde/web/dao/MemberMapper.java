package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MemberMapper extends Mapper<Member> {
    @Select("select max(position) from Member where pid = #{pid}")
    Integer getMaxPosition(String pid);

    @Select("select * from Member where membertype=0 order by position")
    List<Member> selectAllDim();

    @Update("update Member set datatype=#{datatype} where id = #{id}")
    void switchDim(String id, Integer datatype);

    @Update("update Member set datatype=#{newdatatype} where dimid = #{dimid} and datatype = #{olddatatype}")
    void switchMember(String dimid, Integer olddatatype, Integer newdatatype);

    @Delete("delete from Member where dimid= #{dimid} or id = #{dimid}")
    void delDim(String dimid);

    @Update("update Member set name=#{name} where id = #{id}")
    void updateDim(String id, String name);

    @Delete("delete from Member where id= #{id} or unicode like #{unicode}")
    void delMemberByUnicode(String id, String unicode);

    @Update("update Member set ${field}=${value} where code = #{code} and dimid=#{dimid}")
    void updateFiled(String code, String dimid, String field, String value);

    @Update("update Member set name=#{name},datatype=#{datatype},weight=#{weight} where code = #{code} and dimid=#{dimid}")
    void updateMember(String code, String dimid, String name, Integer datatype, Float weight);

    @Select("select distinct code from Member where datatype <>10 and datatype<>11 and dimid=#{dimid}")
    List<String> getMemberCodesByDimid(String dimid);

}