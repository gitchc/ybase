package com.yonyou.mde.web.web;
import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.View;
import com.yonyou.mde.web.model.vos.ViewVO;
import com.yonyou.mde.web.model.vos.ViewTree;
import com.yonyou.mde.web.service.ViewService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
* @Author chenghch
* @Date 2021-01-27
*/
@RestController
@RequestMapping("/view")
public class ViewController {
    @Resource
    private ViewService viewService;

    @RequestMapping("/insert")
    public Result add(View view) {
        viewService.insert(view);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        viewService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(View view) {
        viewService.update(view);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        View view = viewService.findById(id);
        return ResultGenerator.genSuccessResult(view);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        List<ViewTree> list = viewService.findAllViews();
        return ResultGenerator.genSuccessResult(list);
    }
    @RequestMapping("/deleteview")
    public Result deleteview(@RequestParam String viewid) {
        viewService.deleteViewById(viewid);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/saveview")
    public Result saveview(@RequestBody ViewVO viewLayout) {
        String viewid = viewService.saveView(viewLayout);
        return ResultGenerator.genSuccessResult(viewid);
    }
}
