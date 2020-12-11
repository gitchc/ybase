package com.company.project.web;

import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Member;
import com.company.project.service.MemberService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by CodeGenerator on 2020-12-10.
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService MemberService;

    @RequestMapping("/addDim")
    public Result addDim(Member Member) {
        MemberService.addDim(Member);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/listDim")
    public Result listDim() {
        List<Member> list = MemberService.findAllDim();
        return ResultGenerator.genSuccessResult(list);
    }
    @RequestMapping("/switchDim")
    public Result switchDim(Member Member) {
        MemberService.switchDim(Member);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/delDim")
    public Result delDim(Member Member) {
        MemberService.delDim(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/addMember")
    public Result add(Member Member) {
        MemberService.addMember(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        MemberService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/update")
    public Result update(Member Member) {
        MemberService.update(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Member Member = MemberService.findById(id);
        return ResultGenerator.genSuccessResult(Member);
    }

    @RequestMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Member> list = MemberService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
