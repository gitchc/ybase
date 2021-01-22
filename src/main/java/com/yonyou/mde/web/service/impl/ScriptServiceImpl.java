package com.yonyou.mde.web.service.impl;

import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.ScriptMapper;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.model.types.KeywordType;
import com.yonyou.mde.web.model.types.ScriptType;
import com.yonyou.mde.web.model.vos.Completer;
import com.yonyou.mde.web.model.vos.ScriptVo;
import com.yonyou.mde.web.script.classloder.JavaClassUtils;
import com.yonyou.mde.web.script.utils.KeyWordUtil;
import com.yonyou.mde.web.service.CubeService;
import com.yonyou.mde.web.service.DimensionService;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.service.ScriptService;
import com.yonyou.mde.web.utils.SnowID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    @Resource
    private MemberService memberService;
    @Resource
    private DimensionService dimensionService;
    @Resource
    private CubeService cubeService;

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
        List<Completer> all = new ArrayList<>();
        all.addAll(KeyWordUtil.getKeyWords());
        getMetaKeyWords(all);
        return all;
    }

    /**
     * @description: 获取主数据关键字
     * @param: all
     * @author chenghch
     */
    private void getMetaKeyWords(List<Completer> all) {
        List<Dimension> allDims = dimensionService.getAllDims();
        for (Dimension allDim : allDims) {
            all.add(new Completer(allDim.getCode(), allDim.getCode(), KeywordType.DIM));//添加维度关键字
        }
        List<String> memberCodes = memberService.getAllMemberCodes();
        for (String memberCode : memberCodes) {
            all.add(new Completer(memberCode, memberCode, KeywordType.MEMBER));//添加成员关键字
        }
        List<String> cubeCodes = cubeService.getAllCubeCodes();
        for (String cubeCode : cubeCodes) {
            all.add(new Completer(cubeCode, cubeCode, KeywordType.CUBE));//添加模型关键字
        }
    }

    //执行脚本
    private Map<String, Object> run(Script script, Map<String, Object> vars) throws ScriptException {
        if (vars == null) {
            vars = new HashMap<>();
        }
        String id = script.getId();
        try {
            Map<String, Object> runResult = JavaClassUtils.Run(script, vars);
            scriptMapper.updateStatus(id, ScriptType.SUCESS);
            return runResult;
        } catch (Exception e) {
            scriptMapper.updateStatus(id, ScriptType.FAIL);
            Map<String, Object> res = new HashMap<>();
            res.put("code", "-1");
            res.put("msg", e.getMessage());
            return res;
        }

    }
}
