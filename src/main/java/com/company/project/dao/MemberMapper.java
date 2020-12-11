package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface MemberMapper extends Mapper<Member> {
    @Select("select max(position) from Member where pid = #{pid}")
    Integer getMaxPosition(@Param("pid") String pid);

    @Select("select * from Member where membertype=1")
    List<Member> selectAllDim();

    @Update("update Member set datatype=#{datatype} where id = #{id}")
    void switchDim(String id, Integer datatype);
}