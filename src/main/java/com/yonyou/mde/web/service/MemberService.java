package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.MemberFiled;
import com.yonyou.mde.web.model.MemberVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface MemberService extends Service<Member> {

    String insertDim(Member member) throws ServiceException;

    Member insertMember(Member member, Member Pmember);

    String insertMember(Member member);

    List<Member> findAllDim();

    void switchDim(Member member);

    void delDim(String dimid);

    void updateDim(Member member);

    void deleteMember(String id);

    void updateFiled(MemberFiled member);

    void updateMember(Member member);

    List<MemberVO> getMemberVOsBydimid(String dimid);

    List<Member> getMembersBydimid(String id);

    List<String> getMemberCodesByDimid(String id);

    String getDimidByCode(String dimCode);

    String getMemberIdByCode(String dimid, String memberName);
}
