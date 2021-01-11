package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.ScriptMapper;
import com.yonyou.mde.web.model.Completer;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.model.ScriptType;
import com.yonyou.mde.web.model.ScriptVo;
import com.yonyou.mde.web.script.Utils.KeyWord;
import com.yonyou.mde.web.script.classloder.JavaClassUtils;
import com.yonyou.mde.web.service.ScriptService;
import com.yonyou.mde.web.utils.SnowID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
@Service
public class ScriptServiceImpl extends AbstractService<Script> implements ScriptService {
    @Resource
    private ScriptMapper scriptMapper;

    @Override
    public void insertScript(Script script) {
        Script oldsc = scriptMapper.getScriptByName(script.getName());
        if (oldsc != null) {
            throw new ServiceException("脚本名称不能重复!");
        } else {
            script.setId(SnowID.nextID());
            script.setUpdateuser("admin");
            insert(script);
        }

    }

    @Override
    public void updateName(Script script) throws ServiceException {
        Script oldsc = scriptMapper.getScriptByName(script.getName());
        if (oldsc != null) {
            throw new ServiceException("脚本名称不能重复!");
        } else {
            scriptMapper.updateName(script.getId(), script.getName());
        }

    }

    @Override
    public void updateContent(Script script) throws ScriptException {
        String nowuser = "admin";
        scriptMapper.updateContent(script.getId(), script.getContent(), nowuser);
        JavaClassUtils.Check(script);
    }

    @Override
    public Map<String, Object> run(ScriptVo vo) throws ScriptException {
        String id = vo.getId();
        Script script;
        String name = vo.getName();
        if (StringUtils.isNotBlank(id)) {
            script = scriptMapper.selectByPrimaryKey(id);
        } else if (StringUtils.isNotBlank(name)) {
            script = scriptMapper.getScriptByName(name);
        } else {
            throw new ScriptException("名称或者id不能为空");
        }
        return run(script, vo.getVars());
    }

    @Override
    public Map<String, Object> run(String name, Map<String, Object> params) throws ScriptException {
        Script script = scriptMapper.getScriptByName(name);
        if (script == null) {
            throw new ScriptException("脚本[" + name + "]不存在!");
        }
        return run(script, params);
    }


    @Override
    public String getId(String scriptName) {
        Script script = scriptMapper.getScriptByName(scriptName);
        if (script != null) {
            return script.getId();
        }
        return null;
    }

    @Override
    public List<Completer> getKeywords() {
        return KeyWord.getKeyWords();
    }

    //执行脚本
    private Map<String, Object> run(Script script, Map<String, Object> vars) throws ScriptException {
        if (vars == null) {
            vars = new HashMap<>();
        }
        String id = script.getId();
        try {
            scriptMapper.updateStatus(id, ScriptType.SUCESS);
            return JavaClassUtils.Run(script, vars);
        } catch (Exception e) {
            scriptMapper.updateStatus(id, ScriptType.FAIL);
            Map<String, Object> res = new HashMap<>();
            res.put("code", "-1");
            res.put("msg", e.getMessage());
            return res;
        }

    }
}
