package com.company.project.service.impl;

import com.company.project.core.AbstractService;
import com.company.project.core.ServiceException;
import com.company.project.dao.AttrMapper;
import com.company.project.dao.AttrvalueMapper;
import com.company.project.dao.MemberMapper;
import com.company.project.model.*;
import com.company.project.service.MemberService;
import com.company.project.utils.SnowID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

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

    public void addDim(Member member) throws ServiceException {
        Condition condition = new Condition(Member.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("membertype", MemberType.DIM);
        criteria.andEqualTo("code", member.getCode());
        List<Member> old = memberMapper.selectByCondition(condition);
        if (old.size() > 0) {
            throw new ServiceException("维度编码不能重复!");
        }
        member.setId(SnowID.nextID());
        member.setPid("-1");
        String pid = member.getPid();
        member.setMembertype(MemberType.DIM);
        member.setDatatype(DataType.MAROLLUP);//手动上卷
        member.setGeneration(-1);
        member.setDimid("-1");
        member.setUnicode(member.getCode());
        member.setUnipos("0");
        Integer maxPos = memberMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        member.setPosition(nextPos);
        memberMapper.insert(member);
    }

    public void addMember(Member member) {
        member.setId(SnowID.nextID());
        String pid = member.getPid();
        if (StringUtils.isBlank(pid)) {
            String dimid = member.getDimid();
            pid = dimid;
            member.setPid(dimid);
        }
        Member Pmember = memberMapper.selectByPrimaryKey(pid);
        member.setStatus(StatusType.NORMAL);
        member.setWeight(member.getWeight() == null ? 1L : member.getWeight());
        Integer maxPos = memberMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        member.setPosition(nextPos);
        member.setDimid(Pmember.getDimid().equals("-1") ? Pmember.getId() : Pmember.getId());
        member.setGeneration(Pmember.getGeneration() + 1);
        member.setUnicode(Pmember.getUnicode() + "," + member.getCode());
        member.setUnipos(Pmember.getUnipos() + "." + member.getPosition());
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
    public void delDim(String dimid) {
        attrvalueMapper.deleteByDim(dimid);//删除属性值
        attrMapper.deleteByDim(dimid);//删除属性
        memberMapper.delDim(dimid);//删除维度
    }

    @Override
    public void updateDim(Member member) {
        memberMapper.updateDim(member.getId(), member.getName());
    }

    @Override
    public void deleteMember(String id) {
        Member member = memberMapper.selectByPrimaryKey(id);
        if (member != null) {
            attrvalueMapper.deleteByMemberid(id, member.getUnicode() + ",%");//删除属性值
            memberMapper.delMemberByUnicode(id, member.getUnicode() + ",%");//删除维度
        }
    }

    @Override
    public void updateFiled(MemberUpdateVO member) {
        memberMapper.updateFiled(member.getCode(),member.getDimid(),member.getField(),member.getValue());
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateMember(member.getCode(),member.getDimid(),member.getName(),member.getMembertype(),member.getWeight());
    }
}
