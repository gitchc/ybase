package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Attr;
import com.company.project.model.ColVo;
import com.company.project.service.AttrService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2020-12-10.
 */
@RestController
@RequestMapping("/attr")
public class AttrController {
    @Resource
    private AttrService AttrService;

    @RequestMapping("/insert")
    public Result insert(Attr Attr) {
        AttrService.insertAttr(Attr);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        AttrService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(Attr Attr) {
        AttrService.update(Attr);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Attr Attr = AttrService.findById(id);
        return ResultGenerator.genSuccessResult(Attr);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam String dimid, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        List<ColVo> attrs = AttrService.getAttrByDimid(dimid);
        return ResultGenerator.genSuccessResult(attrs);
    }
}
