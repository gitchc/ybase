package com.yonyou.mde.web.web;
import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.ViewLayout;
import com.yonyou.mde.web.service.ViewLayoutService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * @Author chenghch
 * @Date 2021-01-27
 */
@RestController
@RequestMapping("/viewlayout")
public class ViewLayoutController {
    @Resource
    private ViewLayoutService viewLayoutService;

    @RequestMapping("/insert")
    public Result add(ViewLayout viewLayout) {
        viewLayoutService.insert(viewLayout);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        viewLayoutService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(ViewLayout viewLayout) {
        viewLayoutService.update(viewLayout);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        ViewLayout viewLayout = viewLayoutService.findById(id);
        return ResultGenerator.genSuccessResult(viewLayout);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ViewLayout> list = viewLayoutService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
    @RequestMapping("/setScope")
    public Result setScope(@RequestParam String viewid,@RequestParam String dimid,@RequestParam String layouttype,@RequestParam String scope) {
        viewLayoutService.updateScope(viewid,dimid,layouttype,scope);
        return ResultGenerator.genSuccessResult();
    }
}
