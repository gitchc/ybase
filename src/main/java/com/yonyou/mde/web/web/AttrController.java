package com.yonyou.mde.web.web;

import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.Attr;
import com.yonyou.mde.web.model.ColVO;
import com.yonyou.mde.web.service.AttrService;
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
    private AttrService attrService;

    @RequestMapping("/insert")
    public Result insert(Attr attr) {
        attrService.insertAttr(attr);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        attrService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(Attr attr) {
        attrService.update(attr);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Attr Attr = attrService.findById(id);
        return ResultGenerator.genSuccessResult(Attr);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam String dimid, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        List<ColVO> attrs = attrService.getAttrByDimid(dimid);
        return ResultGenerator.genSuccessResult(attrs);
    }
}
