package com.company.project.service;

import com.company.project.core.Service;
import com.company.project.core.ServiceException;
import com.company.project.model.Member;
import com.company.project.model.MemberUpdateVO;

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
