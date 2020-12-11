package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.Attr;
import org.apache.ibatis.annotations.Delete;

public interface AttrMapper extends Mapper<Attr> {
    @Delete("delete from attr where dimid = #{dimid}")
    void deleteByDim(String dimid);

}