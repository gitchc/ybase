package com.yonyou.mde.web.script;

import com.yonyou.mde.web.core.ScriptException;

import java.util.Map;

public class TestScript extends BaseScript {
    @Override
    public Map<String, Object> execute(Map<String, Object> vars) throws ScriptException {
        initVars(vars);
        String [] dims = new String[]{"SCENARIO","VERSION","CURRENCY","TRAIL","ENTITY","ACCOUNT","SCOPE","ICP","VIEW","YEAR","TIME_HALFYEAR","QUARTER","MONTH","C1","C2","C3","C4","C5"};
        for (String dim : dims) {
            DimensionAdd(dim);
        }
        return super.getResults();
    }


}