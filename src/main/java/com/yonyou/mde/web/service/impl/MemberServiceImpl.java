package com.yonyou.mde.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.AttrMapper;
import com.yonyou.mde.web.dao.AttrvalueMapper;
import com.yonyou.mde.web.dao.MemberMapper;
import com.yonyou.mde.web.model.*;
import com.yonyou.mde.web.model.types.DataType;
import com.yonyou.mde.web.model.types.MemberType;
import com.yonyou.mde.web.model.types.StatusType;
import com.yonyou.mde.web.model.vos.MemberFiled;
import com.yonyou.mde.web.model.vos.MemberVO;
import com.yonyou.mde.web.service.MemberService;
import com.yonyou.mde.web.utils.MemberUtil;
import com.yonyou.mde.web.utils.SnowID;
import com.yonyou.mde.web.utils.SortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
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

    public String insertDim(Member member) throws ServiceException {
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
        return member.getId();
    }

    @Override
    public Member insertMember(Member member, Member Pmember) {
        member.setId(SnowID.nextID());
        String pid = member.getPid();
        String dimid = member.getDimid();
        if (StringUtils.isBlank(pid)) {
            pid = dimid;
            member.setPid(dimid);
        }
        member.setStatus(StatusType.NORMAL);
        member.setWeight(member.getWeight() == null ? 1L : member.getWeight());
      /*  Integer maxPos = memberMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        member.setPosition(nextPos);*/
        member.setGeneration(Pmember.getGeneration() + 1);
        member.setUnicode(Pmember.getUnicode() + "," + member.getCode());
        member.setUnipos(Pmember.getUnipos() + "." + member.getPosition());
        memberMapper.insert(member);
        return member;
    }

    public String insertMember(Member member) {
        member.setId(SnowID.nextID());
        String pid = member.getPid();
        String dimid = member.getDimid();
        if (StringUtils.isBlank(pid)) {
            pid = dimid;
            member.setPid(dimid);
        }
        Member Pmember = memberMapper.selectByPrimaryKey(pid);
        if (Pmember == null) {
            throw new ServiceException("父节点不存在");
        }
        if (Pmember.getDatatype() != DataType.AUTOROLLUP || Pmember.getDatatype() != DataType.MAROLLUP) {//父项不为汇聚改一下
            Member dim = memberMapper.selectByPrimaryKey(dimid);
            memberMapper.updateFiled(Pmember.getCode(), member.getDimid(), "datatype", dim.getDatatype() + "");
        }
        member.setStatus(StatusType.NORMAL);
        member.setWeight(member.getWeight() == null ? 1L : member.getWeight());
        Integer maxPos = memberMapper.getMaxPosition(pid);
        Integer nextPos = maxPos == null ? 1 : maxPos + 1;
        member.setPosition(nextPos);
        member.setGeneration(Pmember.getGeneration() + 1);
        member.setUnicode(Pmember.getUnicode() + "," + member.getCode());
        member.setUnipos(Pmember.getUnipos() + "." + member.getPosition());
        memberMapper.insert(member);
        return member.getId();
    }

    public List<Member> findAllDim() {
        return memberMapper.selectAllDim();
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
    public void updateFiled(MemberFiled member) {
        memberMapper.updateFiled(member.getCode(), member.getDimid(), member.getField(), member.getValue());
    }

    @Override
    public void updateMember(Member member) {
        memberMapper.updateMember(member.getCode(), member.getDimid(), member.getName(), member.getDatatype(), member.getWeight());
    }

    @Override
    public List<MemberVO> getMemberVOsBydimid(String dimid) {
        Condition condition = new Condition(Member.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("dimid", dimid);
        criteria.andNotIn("status", Arrays.asList(StatusType.DISABLED));
        List<Member> list = findByCondition(condition);
        List<MemberVO> res = new ArrayList<>(list.size());
        list.forEach(item -> {
            MemberVO vo = new MemberVO();
            BeanUtil.copyProperties(item, vo);
            vo.setDatatypedetail(DataType.getStr(item.getDatatype()));
            vo.setStatusdetail(StatusType.getStr(item.getStatus()));
            vo.setNamedetail(MemberUtil.getLevelName(item));
            res.add(vo);
        });
        List<MemberVO> finalRes = SortUtil.sort(res);
        return finalRes;
    }

    @Override
    public List<Member> getMembersBydimid(String id) {
        Condition condition = new Condition(Member.class);
        Example.Criteria criteria = condition.createCriteria();
        criteria.andEqualTo("dimid", id);
        criteria.andNotIn("status", Arrays.asList(StatusType.DISABLED));
        List<Member> list = findByCondition(condition);
        List<Member> finalRes = SortUtil.sortMember(list);
        return finalRes;
    }

    @Override
    public List<String> getMemberCodesByDimid(String dimid) {
        return new ArrayList<>(memberMapper.getMemberCodesByDimid(dimid));
    }


    @Override
    public String getMemberIdByCode(String dimid, String membercode) {
        return memberMapper.getMemberIdByCode(dimid, membercode);
    }

    @Override
    public List<Member> getAllMemberMeta() {
        return memberMapper.getAllMemberCodes();
    }
}
