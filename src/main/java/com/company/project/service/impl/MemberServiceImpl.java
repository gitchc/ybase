package com.company.project.service.impl;

import com.company.project.core.AbstractService;
import com.company.project.dao.AttrMapper;
import com.company.project.dao.AttrvalueMapper;
import com.company.project.dao.MemberMapper;
import com.company.project.model.DataType;
import com.company.project.model.Member;
import com.company.project.model.MemberType;
import com.company.project.model.StatusType;
import com.company.project.service.MemberService;
import com.company.project.utils.SnowID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class MemberServiceImpl extends AbstractService<Member> implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private AttrMapper attrMapper;
    @Resource
    private AttrvalueMapper attrvalueMapper;

    public void addDim(Member member) {
        member.setId(SnowID.nextID());
        member.setPid("-1");
        String pid = member.getPid();
        member.setMembertype(MemberType.DIM);
        member.setDatatype(DataType.MAROLLUP);//手动上卷
        member.setGeneration(-1);
        member.setDimid("-1");
        member.setUniquecode(member.getCode());
        member.setUniqueposition("0");
        Integer maxPos = memberMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        member.setPosition(nextPos);
        memberMapper.insert(member);
    }

    public void addMember(Member member) {
        member.setId(SnowID.nextID());
        String pid = member.getPid();
        Member Pmember = memberMapper.selectByPrimaryKey(pid);
        member.setGeneration(Pmember.getGeneration() + 1);
        member.setUniquecode(Pmember.getUniquecode() + "," + member.getCode());
        member.setUniqueposition(Pmember.getUniqueposition() + "." + member.getPosition());
        member.setStatus(StatusType.NORMAL);
        member.setWeight(member.getWeight() == null ? 1L : member.getWeight());
        Integer maxPos = memberMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        member.setPosition(nextPos);
        member.setDimid(Pmember.getDimid().equals("-1") ? Pmember.getId() : Pmember.getId());
        memberMapper.insert(member);
    }

    public List<Member> findAllDim() {
        return memberMapper.selectAllDim();
    }

    @Override
    public void switchDim(Member member) {
        memberMapper.switchDim(member.getDimid(), member.getDatatype());
    }

    @Override
    public void delDim(Member member) {
        attrvalueMapper.deleteByDim(member.getDimid());//删除属性值
        attrMapper.deleteByDim(member.getDimid());//删除属性
        memberMapper.delDim(member.getDimid());//删除维度
    }
}
