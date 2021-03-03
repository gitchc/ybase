package com.yonyou.mde.web.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.yonyou.mde.web.core.AbstractService;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.dao.AttrvalueMapper;
import com.yonyou.mde.web.dao.CubeMapper;
import com.yonyou.mde.web.dao.DimensionMapper;
import com.yonyou.mde.web.dao.MemberMapper;
import com.yonyou.mde.web.model.Cube;
import com.yonyou.mde.web.model.Dimension;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.entity.MemberTree;
import com.yonyou.mde.web.model.types.DataType;
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

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class MemberServiceImpl extends AbstractService<Member> implements MemberService {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private DimensionMapper dimensionMapper;
    @Resource
    private AttrvalueMapper attrvalueMapper;
    @Resource
    private CubeMapper cubeMapper;


    @Override
    public Member insertMember(Member member, Member Pmember) {
        member.setId(SnowID.nextID());
        String pid = member.getPid();
        String dimid = member.getDimid();
        if (StringUtils.isBlank(pid)) {
            member.setPid(dimid);
        }
        member.setStatus(StatusType.NORMAL);
        member.setWeight(member.getWeight() == null ? 1L : member.getWeight());
        member.setGeneration(Pmember.getGeneration() + 1);
        member.setUnicode(Pmember.getUnicode() + "," + member.getCode());
        member.setUnipos(Pmember.getUnipos() + "," + member.getPosition());
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
        member.setUnipos(Pmember.getUnipos() + "," + member.getPosition());
        memberMapper.insert(member);
        return member.getId();
    }


    @Override
    public void deleteMember(String id) {
        Member member = memberMapper.selectByPrimaryKey(id);
        if (member != null) {
            attrvalueMapper.deleteByMemberid(id, member.getUnicode() + ",%");//删除属性值
            memberMapper.delMemberByUnicode(id, member.getUnicode() + ",%");//删除维度
            String dimid = member.getDimid();
            List<Cube> cubes = cubeMapper.getCubeByHasDimID("%" + dimid + "%");
            Dimension dimension = dimensionMapper.getDimById(dimid);
            String temp = "update {} set isdeleted=1 where {}='{}'";
            for (Cube cube : cubes) {
                memberMapper.executeSql(StrUtil.format(temp, cube.getCubecode(), dimension.getCode(), member.getCode()));
            }
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
        List<Member> list = getMembersByDimid(dimid);
        List<MemberVO> res = new ArrayList<>(list.size());
        for (Member member : list) {
            MemberVO vo = new MemberVO();
            BeanUtil.copyProperties(member, vo);
            vo.setDatatypedetail(DataType.getStr(member.getDatatype()));
            vo.setStatusdetail(StatusType.getStr(member.getStatus()));
//            vo.setNamedetail(MemberUtil.getLevelName(member));
            res.add(vo);
        }
        return res;
    }

    /**
     * @description: 获取维度全部成员
     * @param: dimid
     * @author chenghch
     */
    @Override
    public List<Member> getMembersByDimid(String dimid) {
        List<Member> list = memberMapper.getMembersByDimid(dimid);
        return SortUtil.sortMember(list);//排序
    }

    @Override
    public List<MemberTree> getMemberTreeByDimid(String dimid) {
        List<MemberTree> newlist = new ArrayList<>();
        List<Member> list = getMembersByDimid(dimid);
        Map<String, MemberTree> treeMap = new HashMap<>();
        for (Member member : list) {
            String id = member.getId();
            MemberTree memberTree = new MemberTree(id, member.getName());
            treeMap.put(id, memberTree);
            MemberTree PMember = treeMap.get(member.getPid());
            if (PMember != null) {
                PMember.add(memberTree);
            } else {
                newlist.add(memberTree);
            }
        }
        return newlist;
    }

    @Override
    public List<String> getMemberCodesByDimid(String dimid) {
        return new ArrayList<>(memberMapper.getMemberCodesByDimid(dimid));
    }


    @Override
    public String getMemberIdByCode(String dimid, String membercode) {
        return memberMapper.getMemberIdByCode(dimid, membercode);
    }

    /**
     * @description: 获取元数据基础信息, 前台显示提示用
     * @param:
     * @author chenghch
     */
    @Override
    public List<Member> getAllMemberMeta() {
        return memberMapper.getAllMemberMeta();
    }

    @Override
    public List<Member> getMembersByScope(String dimid, String scope) {
        if (StringUtils.isBlank(scope)) {
            return getMembersByDimid(dimid);
        } else {
            String scopes = "'" + scope.replaceAll(",", "','") + "'";
            List<Member> list = memberMapper.getMembersByScope(dimid, scopes);
            return SortUtil.sortMember(list);
        }

    }

    @Override
    public boolean moveUp(String id) {
        return move(id, true);
    }

    @Override
    public boolean moveDown(String id) {
        return move(id, false);
    }

    /**
     * @description: 移动方法
     * @param: id
     * @param: up
     * @author chenghch
     */
    private boolean move(String id, boolean up) {
        Member member = memberMapper.selectByPrimaryKey(id);
        String pid = member.getPid();
        Member PMember = memberMapper.selectByPrimaryKey(pid);
        String PUniPosition = PMember.getUnipos();
        int selfPos = member.getPosition();
        List<Member> nextmembers;
        if (up) {
            nextmembers = memberMapper.getUpPosition(pid, selfPos);
        } else {
            nextmembers = memberMapper.getDownPosition(pid, selfPos);
        }
        Member nextmember;
        if (nextmembers == null || nextmembers.size() == 0) {
            return false;
        } else {
            nextmember = nextmembers.get(0);
        }
        int nextPos = nextmember.getPosition();
        String nextid = nextmember.getId();
        String nextUniPos = PUniPosition + "," + selfPos;
        String selfUniPos = PUniPosition + "," + nextPos;
        member.setUnipos(selfUniPos);
        nextmember.setUnipos(nextUniPos);
        memberMapper.updatePosition(selfPos, nextUniPos, nextid);
        memberMapper.updatePosition(nextPos, selfUniPos, id);
        freshUniPos(member);
        freshUniPos(nextmember);
        return true;
    }


    /**
     * @description: 递归替换unionPostion, 排序方法
     * @param: nextmember
     * @author chenghch
     */

    public void freshUniPos(Member nextmember) {
        String id = nextmember.getId();
        if (nextmember.getDatatype() >= DataType.MAROLLUP) {//如果有子项,递归替换
            memberMapper.updateUniPositionByPid(nextmember.getUnipos(), id);
            List<Member> children = memberMapper.getParentMembersByPid(id);
            if (children != null) {
                for (Member child : children) {
                    freshUniPos(child);
                }
            }
        }
    }

    /**
     * @description: 递归刷新unionCode, 唯一标识,确保member的UnionCode是正确的
     * @param: nextmember
     * @author chenghch
     */
    public void freshUniCode(Member member) {
        String id = member.getId();
        if (member.getDatatype() >= DataType.MAROLLUP) {//如果有子项,递归替换
            memberMapper.updateUniCodeByPid(member.getUnicode(), id);
            List<Member> children = memberMapper.getParentMembersByPid(id);
            if (children != null) {
                for (Member child : children) {
                    freshUniCode(child);
                }
            }
        }
    }
}
