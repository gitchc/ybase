package com.company.project.service.impl;

import com.company.project.dao.MemberMapper;
import com.company.project.model.Member;
import com.company.project.service.MemberService;
import com.company.project.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2020-12-10.
 */
@Service
@Transactional
public class MemberServiceImpl extends AbstractService<Member> implements MemberService {
    @Resource
    private MemberMapper memberMapper;

}
