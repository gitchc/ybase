package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.AttrValueVO;
import com.company.project.model.Attrvalue;
import com.company.project.service.AttrvalueService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2020-12-10.
 */
@RestController
@RequestMapping("/attrvalue")
public class AttrvalueController {
    @Resource
    private AttrvalueService AttrvalueService;

    @RequestMapping("/insert")
    public Result insert(Attrvalue Attrvalue) {
        AttrvalueService.insert(Attrvalue);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(AttrValueVO AttrValueVO) {
        AttrvalueService.deleteByDimIdAndName(AttrValueVO);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(Attrvalue Attrvalue) {
        AttrvalueService.update(Attrvalue);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/updateValue")
    public Result updateValue(AttrValueVO AttrValueVO) {
        AttrvalueService.updateValue(AttrValueVO);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Attrvalue Attrvalue = AttrvalueService.findById(id);
        return ResultGenerator.genSuccessResult(Attrvalue);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Attrvalue> list = AttrvalueService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @RequestMapping("/listAttrValues")
    public Result list(@RequestParam String dimid, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit) {
        List<Map<String, String>> res = AttrvalueService.getAllAttrValues(dimid);
        return ResultGenerator.genSuccessResult(res);
    }
}
