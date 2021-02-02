package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.vos.MemberFiled;
import com.yonyou.mde.web.model.vos.MemberVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface MemberService extends Service<Member> {

    /**
     * @description: 在Pmember下面插入成员
     * @param: member
     * @param: Pmember
     * @author chenghch
     */
    Member insertMember(Member member, Member Pmember);

    /**
     * @description: 在顶级节点下面插入成员, 返回插入的id
     * @param: member
     * @param: Pmember
     * @author chenghch
     */
    String insertMember(Member member);

    /**
     * @description: 删除成员
     * @param: id
     * @author chenghch
     */
    void deleteMember(String id);

    /**
     * @description: 更新成员的基础属性
     * @param: member
     * @author chenghch
     */
    void updateFiled(MemberFiled member);

    /**
     * @description: 更新成员
     * @param: member
     * @author chenghch
     */
    void updateMember(Member member);

    /**
     * @description: 返回前台成员显示对象
     * @param: dimid
     * @author chenghch
     */
    List<MemberVO> getMemberVOsBydimid(String dimid);

    /**
     * @description: 返回维度的所有成员
     * @param: dimid 维度id
     * @author chenghch
     */
    List<Member> getMembersByDimid(String dimid);
    /**
     * @description: 返回维度的所有成员
     * @param: dimid 维度id
     * @param: scope 维度成员表达式
     * @author chenghch
     */
    List<Member> getMembersByScope(String dimid, String scope);
    /**
     * @description: 返回维度的所有编码
     * @param: dimid
     * @author chenghch
     */

    List<String> getMemberCodesByDimid(String dimid);

    /**
     * @description: 根据维度id和成员名称获取code
     * @param: dimid
     * @author chenghch
     */
    String getMemberIdByCode(String dimid, String memberName);

    /**
     * @description: 获取所有的成员编码和名称
     * @param:
     * @author chenghch
     */
    List<Member> getAllMemberMeta();


    /**
     * @description: 上移
     * @param: id
     * @author chenghch
     */
    boolean moveUp(String id);

    /**
     * @description: 下移
     * @param: id
     * @author chenghch
     */
    boolean moveDown(String id);
}
