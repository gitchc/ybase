package com.yonyou.mde.web.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class AttrValue {
    /**
     * 雪花id
     */
    @Id
    private String id;

    /*
    * 属性id
    * */
    private String attrid;
    /**
     * 成员code
     */
    private String membercode;

    /**
     * 属性值
     */
    @Column(name = "attrValue")
    private String attrvalue;


}