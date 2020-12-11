package com.company.project.service;

import com.company.project.core.Service;
import com.company.project.core.ServiceException;
import com.company.project.model.Member;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface MemberService extends Service<Member> {

    void addDim(Member member) throws ServiceException;

    void addMember(Member member);

    List<Member> findAllDim();

    void switchDim(Member member);

    void delDim(Member member);

    void updateDim(Member member);
}
