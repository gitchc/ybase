package com.yonyou.mde.web.web;

import com.yonyou.mde.web.core.Result;
import com.yonyou.mde.web.core.ResultGenerator;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.vos.MemberFiled;
import com.yonyou.mde.web.model.vos.MemberVO;
import com.yonyou.mde.web.service.DimensionService;
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
    private MemberService memberService;
    @Resource
    private DimensionService dimensionService;

    @RequestMapping("/insertDim")
    public Result insertDim(Dimension dimension) throws ServiceException {
        dimensionService.insertDim(dimension);
        return ResultGenerator.genSuccessResult();

    }

    @RequestMapping("/listDim")
    public Result listDim() {
        List<Dimension> list = dimensionService.getAllDims();
        return ResultGenerator.genSuccessResult(list);
    }

    @RequestMapping("/switchDim")
    public Result switchDim(Dimension dimension) {
        dimensionService.switchDim(dimension);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delDim")
    public Result delDim(@RequestParam String id) {
        dimensionService.delDim(id);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateDim")
    public Result updateDim(Dimension dimension) {
        dimensionService.updateDim(dimension);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/insertMember")
    public Result insert(Member member) {
        memberService.insertMember(member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/delMember")
    public Result deleteMember(@RequestParam String id) {
        memberService.deleteMember(id);
        return ResultGenerator.genSuccessResult();
    }
    @RequestMapping("/upMember")
    public Result upMember(@RequestParam String id) {
        boolean suc = memberService.moveUp(id);
        return ResultGenerator.genSuccessResult(suc);
    }
    @RequestMapping("/downMember")
    public Result downMember(@RequestParam String id) {
        boolean suc = memberService.moveDown(id);
        return ResultGenerator.genSuccessResult(suc);
    }
    @RequestMapping("/updateField")
    public Result updateField(MemberFiled filed) {
        memberService.updateFiled(filed);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/updateMember")
    public Result updateMember(Member member) {
        memberService.updateMember(member);
        return ResultGenerator.genSuccessResult();
    }

    @RequestMapping("/detail")
    public Result detail(@RequestParam String id) {
        Member Member = memberService.findById(id);
        return ResultGenerator.genSuccessResult(Member);
    }

    @RequestMapping("/listMemmbers")
    public Result list(@RequestParam String dimid, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer limit) {
        List<MemberVO> finalRes = memberService.getMemberVOsBydimid(dimid);
        return ResultGenerator.genSuccessResult(finalRes);
    }
}
