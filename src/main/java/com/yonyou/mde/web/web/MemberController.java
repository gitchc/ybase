package com.yonyou.mde.web.web;

import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.MemberUpdateVO;
import com.yonyou.mde.web.model.MemberVO;
import com.yonyou.mde.web.service.MemberService;
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
        List<MemberVO> finalRes = MemberService.getMemberVOsBydimid(dimid);
        return ResultGenerator.genSuccessResult(finalRes);
    }
}
