package com.yonyou.mde.web.service;

import com.yonyou.mde.web.core.Service;
import com.yonyou.mde.web.core.ServiceException;
import com.yonyou.mde.web.model.Member;
import com.yonyou.mde.web.model.MemberUpdateVO;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface MemberService extends Service<Member> {

    void insertDim(Member member) throws ServiceException;

    void insertMember(Member member);

    List<Member> findAllDim();

    void switchDim(Member member);

    void delDim(String dimid);

    void updateDim(Member member);

    void deleteMember(String id);

    void updateFiled(MemberUpdateVO member);

    void updateMember(Member member);
}
