package com.company.project.service;
import com.company.project.model.Script;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-15.
 */
public interface ScriptService extends Service<Script> {

    void insertScript(Script script);

    void updateName(Script script);

    void updateContent(Script script);

}
