package com.company.project.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class Attrvalue {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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