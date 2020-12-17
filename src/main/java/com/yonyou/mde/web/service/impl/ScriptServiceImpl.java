package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.script.Utils.KeyWord;
import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Completer;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.model.ScriptVo;
import com.yonyou.mde.web.service.ScriptService;
import com.yonyou.mde.web.utils.SnowID;
import com.yonyou.mde.web.script.classloder.JavaClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
@Service
@Transactional
public class ScriptServiceImpl extends AbstractService<Script> implements ScriptService {
    @Resource
    private com.yonyou.mde.web.dao.ScriptMapper ScriptMapper;

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
        checkContent(script);
    }

    @Override
    public Map<String, Object> run(ScriptVo vo) {
        Script script;
        String id = vo.getId();
        String name = vo.getName();
        if (StringUtils.isNotBlank(id)) {
            script = ScriptMapper.selectByPrimaryKey(id);
        } else if (StringUtils.isNotBlank(name)) {
            script = ScriptMapper.getScriptByName(name);
            id = script.getId();
        } else {
            throw new ScriptException("名称或者id不能为空");
        }
        try {
            ScriptMapper.updateStatus(id, 1);
            return run(script, vo.getVars());
        } catch (Exception e) {
            ScriptMapper.updateStatus(id, 2);
            Map<String, Object> res = new HashMap<>();
            res.put("code", "-1");
            res.put("msg", e.getMessage());
            return res;
        }
    }

    @Override
    public List<Completer> getKeywords() {
        return KeyWord.getKeyWords();
    }

    //检查语法
    public void checkContent(Script script) {
        JavaClassUtils.Check(script);
    }

    //执行脚本
    public Map<String, Object> run(Script script, Map<String, Object> vars) {
        if (vars == null) {
            vars = new HashMap<>();
        }
        return JavaClassUtils.run(script, vars);
    }
}
