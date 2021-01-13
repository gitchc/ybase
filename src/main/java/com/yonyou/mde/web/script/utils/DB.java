package com.yonyou.mde.web.script.utils;

import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import com.yonyou.mde.web.core.ScriptException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2020/12/16
 */
@Log4j2
public class DB {
    private Db db;

    public DB(Db db) {
        this.db = db;
    }
    
    /** 
     * @description: 查询
     * @param: sql 脚本
     * @param: values 替换变量
     * @return: java.util.List<cn.hutool.db.Entity> 
     * @author Administrator
     * 
     */
    public List<Entity> query(String sql, Object... values) throws ScriptException {
        try {
            return db.query(sql, values);
        } catch (Exception e) {
            throw new ScriptException(e.getMessage());
        }
    }

    /**
     * @description: 执行sql
     * @param: sql 脚本
     * @param: values 替换变量
     * @return: int 执行条数
     * @author Administrator
     *
     */
    public int excute(String sql, Object... values) throws ScriptException {
        try {
            return db.execute(sql, values);
        } catch (Exception e) {
            throw new ScriptException(e.getMessage());
        }
    }

}
