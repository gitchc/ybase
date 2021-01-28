package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Set;

public interface MemberMapper extends Mapper<Member> {
    @Select("select max(position) from Member where pid = #{pid}")
    Integer getMaxPosition(String pid);

    @Update("update Member set datatype=#{newdatatype} where dimid = #{dimid} and datatype = #{olddatatype}")
    void switchMember(String dimid, Integer olddatatype, Integer newdatatype);


    @Delete("delete from Member where id= #{id} or unicode like #{unicode}")
    void delMemberByUnicode(String id, String unicode);

    @Update("update Member set ${field}=${value} where code = #{code} and dimid=#{dimid}")
    void updateFiled(String code, String dimid, String field, String value);

    @Update("update Member set name=#{name},datatype=#{datatype},weight=#{weight} where code = #{code} and dimid=#{dimid}")
    void updateMember(String code, String dimid, String name, Integer datatype, Float weight);

    @Select("select  code from Member where datatype <>10 and datatype<>11 and dimid=#{dimid} order by unipos asc")
    Set<String> getMemberCodesByDimid(String dimid);

    @Select("select  id from Member where membertype<>0 and dimid = #{dimid} and code=#{code}")
    String getMemberIdByCode(String dimid, String code);

    @Select("select distinct  dimid,code ,name from Member where membertype<>0  ")
    List<Member> getAllMemberMeta();

    @Select("select *  from member t where  t.pid = #{pid} and t.position<#{position} order by position desc")
    List<Member> getUpPosition(String pid, int position);

    @Select("select * from member t where  t.pid = #{pid} and t.position>#{position} order by position")
    List<Member> getDownPosition(String pid, int position);

    @Update("update member set position = #{position},unipos=#{unipos} where id = #{id}")
    void updatePosition(int position, String unipos, String id);

    @Update("update member set unipos=#{unipos} where id = #{id}")
    void updateUniPosition(String unipos, String id);

    @Select("select * from Member where dimid=#{dimid} and status<>2 order by generation, position")
    List<Member> getMembersByDimid(String dimid);

    @Select("select * from Member where pid = #{pid}")
    List<Member> getMembersByPid(String id);

    @Update("update member set unipos = concat(#{unipos},',',position) where pid=#{pid}")
    void updateUniPositionByPid(String unipos, String pid);

    @Update("update member set unicode = concat(#{unicode},',',code) where pid=#{pid}")
    void updateUniCodeByPid(String unicode, String pid);
}