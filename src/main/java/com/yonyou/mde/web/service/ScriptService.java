package com.yonyou.mde.web.service;
import com.yonyou.mde.web.model.Completer;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.model.ScriptVo;

import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface ScriptService extends Service<Script> {

    void insertScript(Script script);

    void updateName(Script script);

    void updateContent(Script script);

    Map<String, Object> run(ScriptVo vo);

    List<Completer> getKeywords();
}
