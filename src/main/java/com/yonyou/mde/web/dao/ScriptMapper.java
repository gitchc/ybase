package com.yonyou.mde.web.dao;

import com.yonyou.mde.web.core.Mapper;
import com.yonyou.mde.web.model.Script;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ScriptMapper extends Mapper<Script> {
    @Update("update script set lastupdate=now() where id=#{id}")
    void updateTime(String id);

    @Update("update script set name=#{name} where id=#{id}")
    void updateName(String id, String name);

    @Update("update script set content=#{content},lastupdate=now(),updateuser=#{nowuser},laststatus=0,version=version+1 where id=#{id}")
    void updateContent(String id, String content, String nowuser);

    @Select("select * from script where name=#{name}")
    Script getScriptByName(String name);
}