package com.yonyou.mde.web.web;

import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.vos.Completer;
import com.yonyou.mde.web.model.Script;
import com.yonyou.mde.web.model.vos.ScriptVo;
import com.yonyou.mde.web.service.ScriptService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2020-12-15.
 */
@RestController
@RequestMapping("/script")
public class ScriptController {
    @Resource
    private ScriptService scriptService;

    @RequestMapping("/insert")
    public Result add(Script script) {
        scriptService.insertScript(script);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        scriptService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateName")
    public Result updateName(Script script) {
        scriptService.updateName(script);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateContent")
    public Result updateContent(Script script) {
        scriptService.updateContent(script);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/run")
    public Result run(ScriptVo vo) {
        Map<String, Object> res = scriptService.run(vo);
        return ResultGenerator.genSuccessResult(res);
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Script Script = scriptService.findById(id);
        return ResultGenerator.genSuccessResult(Script);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam String keyword, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        Condition condition = new Condition(Script.class);
        condition.orderBy("name").asc();
        if (StringUtils.isNotBlank(keyword)) {
            if (keyword.contains("*")) {
                keyword = keyword.replaceAll("\\*", "%");
            } else {
                keyword = "%" + keyword + "%";
            }
            Example.Criteria criteria = condition.createCriteria();
            criteria.andLike("name", keyword);
        }
        List<Script> list = scriptService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping("/keywords")
    public Result keywords() {
        List<Completer> kewords = scriptService.getKeywords();
        return ResultGenerator.genSuccessResult(kewords);
    }
}
