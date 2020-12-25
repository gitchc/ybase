package com.yonyou.mde.web.web;

import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.AttrValueVO;
import com.yonyou.mde.web.model.Attrvalue;
import com.yonyou.mde.web.service.AttrvalueService;
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
    private AttrvalueService attrvalueService;

    @RequestMapping("/insert")
    public Result insert(Attrvalue attrvalue) {
        attrvalueService.insert(attrvalue);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(AttrValueVO attrValueVO) {
        attrvalueService.deleteByDimIdAndName(attrValueVO);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(Attrvalue attrvalue) {
        attrvalueService.update(attrvalue);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/updateValue")
    public Result updateValue(AttrValueVO attrValueVO) {
        attrvalueService.updateValue(attrValueVO);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Attrvalue Attrvalue = attrvalueService.findById(id);
        return ResultGenerator.genSuccessResult(Attrvalue);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Attrvalue> list = attrvalueService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @RequestMapping("/listAttrValues")
    public Result list(@RequestParam String dimid, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit) {
        List<Map<String, String>> res = attrvalueService.getAllAttrValues(dimid);
        return ResultGenerator.genSuccessResult(res);
    }
}
