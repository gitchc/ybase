package com.yonyou.mde.web.web;
import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.model.ViewDetail;
import com.yonyou.mde.web.service.ViewDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
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
@RequestMapping("/viewdetail")
public class ViewDetailController {
    @Resource
    private ViewDetailService viewDetailService;

    @RequestMapping("/insert")
    public Result add(ViewDetail viewDetail) {
        viewDetailService.insert(viewDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam String id) {
        viewDetailService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(ViewDetail viewDetail) {
        viewDetailService.update(viewDetail);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        ViewDetail viewDetail = viewDetailService.findById(id);
        return ResultGenerator.genSuccessResult(viewDetail);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ViewDetail> list = viewDetailService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
