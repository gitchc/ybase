package com.yonyou.mde.web.Script;

import com.yonyou.mde.web.core.ScriptException;

import java.util.Map;

public class TestScript extends BaseScript {
    @Override
    public Map<String, Object> execute(Map<String, Object> vars) throws ScriptException {
        initVars(vars);

        return getResults();
    }


}