package com.company.project.service.impl;

import com.company.project.core.AbstractService;
import com.company.project.core.ServiceException;
import com.company.project.dao.ScriptMapper;
import com.company.project.model.Script;
import com.company.project.service.ScriptService;
import com.company.project.utils.SnowID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
@Service
@Transactional
public class ScriptServiceImpl extends AbstractService<Script> implements ScriptService {
    @Resource
    private ScriptMapper ScriptMapper;

    @Override
    public void insertScript(Script script) {
        Script oldsc = ScriptMapper.getScriptByName(script.getName());
        if (oldsc != null) {
            throw new ServiceException("脚本名称不能重复!");
        } else {
            script.setId(SnowID.nextID());
            script.setUpdateuser("admin");
            insert(script);
        }

    }

    @Override
    public void updateName(Script script) {
        Script oldsc = ScriptMapper.getScriptByName(script.getName());
        if (oldsc != null) {
            throw new ServiceException("脚本名称不能重复!");
        } else {
            ScriptMapper.updateName(script.getId(), script.getName());
        }

    }

    @Override
    public void updateContent(Script script) {
        String nowuser = "admin";
        ScriptMapper.updateContent(script.getId(), script.getContent(), nowuser);
    }


}
