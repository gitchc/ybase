package com.yonyou.mde.web.service;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.core.Service;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface ScriptService extends Service<Script> {

    void insertScript(Script script);

    void updateName(Script script);

    void updateContent(Script script);

}
