package com.company.project.model;

import lombok.Data;


/*
* 前台修改成员对象
* */
@Data
public class MemberUpdateVO {
    private String code;
    private String dimid;
    private String field;
    private String value;
}
