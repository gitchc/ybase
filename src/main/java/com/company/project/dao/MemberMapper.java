package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Member;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MemberMapper extends Mapper<Member> {
    @Select("select max(position) from Member where pid = #{pid}")
    Integer getMaxPosition(@Param("pid") String pid);

    @Select("select * from Member where membertype=0")
    List<Member> selectAllDim();

    @Update("update Member set datatype=#{datatype} where id = #{id}")
    void switchDim(String id, Integer datatype);

    @Delete("delete from Member where dimid= #{dimid} or id = #{dimid}")
    void delDim(String dimid);

    @Update("update Member set name=#{name} where id = #{id}")
    void updateDim(String id, String name);

    @Delete("delete from Member where id= #{id} or unicode like #{unicode}")
    void delMemberByUnicode(String id, String unicode);

    @Update("update Member set ${field}=#{value} where code = #{code} and dimid=#{dimid}")
    void updateFiled(String code, String dimid, String field, String value);

    @Update("update Member set name=#{name},membertype=#{membertype},weight=#{weight} where code = #{code} and dimid=#{dimid}")
    void updateMember(String code, String dimid, String name, Integer membertype, Float weight);
}