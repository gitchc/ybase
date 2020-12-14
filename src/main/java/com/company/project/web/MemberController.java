package com.company.project.web;

import cn.hutool.core.bean.BeanUtil;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.core.ServiceException;
import com.company.project.model.*;
import com.company.project.service.MemberService;
import com.company.project.utils.MemberUtil;
import com.company.project.utils.SortUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by CodeGenerator on 2020-12-10.
 */
@RestController
@RequestMapping("/member")
public class MemberController {
    @Resource
    private MemberService MemberService;

    @RequestMapping("/insertDim")
    public Result insertDim(Member Member) throws ServiceException {
        MemberService.insertDim(Member);
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
    public Result delDim(@RequestParam String id) {
        MemberService.delDim(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateDim")
    public Result updateDim(Member Member) {
        MemberService.updateDim(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/insertMember")
    public Result insert(Member Member) {
        MemberService.insertMember(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delMember")
    public Result deleteMember(@RequestParam String id) {
        MemberService.deleteMember(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateField")
    public Result updateField(MemberUpdateVO Member) {
        MemberService.updateFiled(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateMember")
    public Result updateMember(Member Member) {
        MemberService.updateMember(Member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Member Member = MemberService.findById(id);
        return ResultGenerator.genSuccessResult(Member);
    }

    @RequestMapping("/listMemmbers")
    public Result list(@RequestParam String dimid, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit) {
        Condition condition = new Condition(Member.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("dimid", dimid);
        criteria.andNotIn("status", Arrays.asList(StatusType.DISABLED));
        List<Member> list = MemberService.findByCondition(condition);
        List<MemberVo> res = new ArrayList<>(list.size());
        list.forEach(item -> {
            MemberVo vo = new MemberVo();
            BeanUtil.copyProperties(item, vo);
            vo.setDatatypedetail(DataType.getStr(item.getDatatype()));
            vo.setStatusdetail(StatusType.getStr(item.getStatus()));
            vo.setCodedetail(MemberUtil.getCodeDetail(item));
            res.add(vo);
        });
        List<MemberVo> finalRes = SortUtils.sort(res);
        return ResultGenerator.genSuccessResult(finalRes);
    }
}
