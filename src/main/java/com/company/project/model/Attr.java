package com.company.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Attr {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 维度id
     */
    private String dimid;

    /**
     * 属性名称
     */
    @Column(name = "attrName")
    private String attrname;


}