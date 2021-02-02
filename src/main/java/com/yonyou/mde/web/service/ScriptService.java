package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.model.vos.Completer;
import com.yonyou.mde.web.model.vos.ScriptVo;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface ScriptService extends Service<Script> {

    void insertScript(Script script);

    void updateName(Script script) throws ServiceException;

    void updateContent(Script script) ;

    Map<String, Object> run(ScriptVo vo) throws ScriptException;

    Map<String, Object> run(String scriptName, Map<String, Object> params) throws ScriptException;

    String getId(String scriptName);
    /**
     * @description: 获取关键字信息
     * @param:
     * @author chenghch
     *
     */
    List<Completer> getKeywords();
}
