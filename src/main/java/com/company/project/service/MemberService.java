package com.company.project.service;
import com.company.project.model.Member;
import com.company.project.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
public interface MemberService extends Service<Member> {

    void addDim(Member member);

    void addMember(Member member);

    List<Member> findAllDim();

    void switchDim(Member member);

    void delDim(Member member);
}
