package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.Dimension;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface DimensionMapper extends Mapper<Dimension> {
    @Select("select max(position) from Member where pid = #{pid}")
    Integer getMaxPosition(String pid);

    @Select("select * from Member where membertype=0 order by position")
    List<Dimension> selectAllDim();

    @Update("update Member set datatype=#{datatype} where id = #{id}")
    void switchDim(String id, Integer datatype);

    @Delete("delete from Member where dimid= #{dimid} or id = #{dimid}")
    void delDim(String dimid);

    @Update("update Member set name=#{name} where id = #{id}")
    void updateDim(String id, String name);

    @Delete("delete from Member where id= #{id} or unicode like #{unicode}")
    void delMemberByUnicode(String id, String unicode);

    @Select("select  id from Member where membertype=0 and code = #{dimCode}")
    String getDimIdByCode(@Param(value = "dimCode") String dimCode);

    @Select("select  * from Member where membertype=0 and code = #{dimCode}")
    List<Dimension> getDimsByCode(String code);

    @Select("select  * from Member where membertype=0 and id = #{id}")
    Dimension getDimById(String id);


    @Select("<script>\n" +
            "select  * from Member where membertype=0 and id in" +
            " <foreach item='item' index='index' collection='ids' open='(' separator=',' close=')'>\n" +
            "                 #{item}\n" +
            "       </foreach>\n" +
            "</script>"
    )
    List<Dimension> getDimensionByIds(@Param("ids") String[] ids);
}