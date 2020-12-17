package com.yonyou.mde.web.utils.classloder;

import com.yonyou.mde.web.Script.IScript;
import com.yonyou.mde.web.core.ScriptException;
import com.yonyou.mde.web.model.Script;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2020/12/16
 */
public class JavaClassUtils {
    public static final int run = 1;
    public static final int check = 0;
    private static Map<String, IScript> beans = new HashMap<>();
    public static final String[] importClass = new String[]{
            "import cn.hutool.db.*;"
            , "import com.google.common.base.Stopwatch;"
            , "import com.yonyou.mde.api.*;"
            , "import com.yonyou.mde.context.MdeContext;"
            , "import com.yonyou.mde.dto.*;"
            , "import com.yonyou.mde.error.MdeException;"
            , "import com.yonyou.mde.model.dataloader.budget.*;"
            , "import com.yonyou.mde.model.dataloader.config.*;"
            , "import com.yonyou.mde.model.dataloader.entity.*;"
            , "import com.yonyou.mde.model.dim.*;"
            , "import com.yonyou.mde.model.dim.budget.*;"
            , "import com.yonyou.mde.model.impl.*;"
            , "import com.yonyou.mde.model.result.*;"
            , "import com.yonyou.mde.util.*;"
            , "import com.yonyou.mde.web.Script.Utils.*;"
            , "import com.yonyou.mde.web.Script.*;"
            , "import com.yonyou.mde.web.core.ScriptException;"
            , "import org.apache.commons.lang3.StringUtils;"
            , "import tech.tablesaw.api.*;"
            , "import java.util.*;"
            , "import java.util.concurrent.*;"
    };

    public static String getFullJavaScript(String classname, String source) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(StringUtils.join(importClass, "\n"));
        stringBuilder.append("\n");
        stringBuilder.append("public class " + classname + " extends BaseScript {\n");
        stringBuilder.append("@Override\n");
        stringBuilder.append("public Map<String, Object> execute(Map<String, Object> vars) throws ScriptException {\n");
        stringBuilder.append("initVars(vars);\n");
        stringBuilder.append(source);
        stringBuilder.append("   \nreturn getResults();\n" + "    } \n }");
        return stringBuilder.toString();
    }

    //检查java语法是否通过，执行java代码
    private static Map<String, Object> runOrCheckClass(String className, String source, long version, int type, Map<String, Object> varMap) throws ScriptException {
        Map<String, Object> result = new HashMap<>();
        try {
            IScript script = beans.get(className);
            boolean reloadClass = false;
            if (script != null && script.getVersion() != version) {
                reloadClass = true;
            }
            if (type == check || reloadClass) {
                Object instance = DynaCompiler.compile(className, source);
                if (instance != null && instance instanceof String) {
                    String error = instance.toString();
                    throw new ScriptException(error);
                } else {
                    script = (IScript) instance;
                    script.setVersion(version);
                    setBeanToFactory(className, script);
                }

            } else if (type == run) {
                script = beans.get(className);
                result = script.getClass().newInstance().execute(varMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            String error = e.getMessage();
            if (error == null) {
                error = e.toString();
            }
            throw new ScriptException(error);
        }
        return result;
    }

    private static void setBeanToFactory(String code, IScript script) {
        beans.put(code, script);
    }

    public static void Check(Script script) throws ScriptException {
        String classname = "Y" + script.getId();
        runOrCheckClass(classname, getFullJavaScript(classname, script.getContent()), script.getVersion(), check, null);
    }

    public static Map<String, Object> run(Script script, Map<String, Object> vars) throws ScriptException {
        String classname = "Y" + script.getId();
        return runOrCheckClass(classname, getFullJavaScript(classname, script.getContent()), script.getVersion(), run, vars);
    }
}
