package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Completer;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.model.ScriptVo;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface ScriptService extends Service<Script> {

    void insertScript(Script script);

    void updateName(Script script) throws ServiceException;

    void updateContent(Script script);

    Map<String, Object> run(ScriptVo vo) throws ScriptException;

    Map<String, Object> run(String scriptName, Map<String, Object> params) throws ScriptException;

    String getId(String scriptName);

    List<Completer> getKeywords();
}
