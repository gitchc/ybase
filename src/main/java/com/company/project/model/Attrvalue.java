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

    /**
     * 成员id
     */
    private String memberid;

    /**
     * 属性值
     */
    @Column(name = "attrValue")
    private String attrvalue;


}