package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Script;
import com.company.project.service.ScriptService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2020-12-15.
 */
@RestController
@RequestMapping("/script")
public class ScriptController {
    @Resource
    private ScriptService ScriptService;

    @RequestMapping("/insert")
    public Result add(Script Script) {
        ScriptService.insertScript(Script);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        ScriptService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateName")
    public Result updateName(Script Script) {
        ScriptService.updateName(Script);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateContent")
    public Result updateContent(Script Script) {
        ScriptService.updateContent(Script);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Script Script = ScriptService.findById(id);
        return ResultGenerator.genSuccessResult(Script);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        Condition condition = new Condition(Script.class);
        condition.orderBy("name asc");
        List<Script> list = ScriptService.findByCondition(condition);
        return ResultGenerator.genSuccessResult(list);
    }
}
