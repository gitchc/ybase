package com.company.project.model;

import javax.persistence.*;

public class Attr {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 维度id
     */
    private Long dimid;

    /**
     * 属性名称
     */
    @Column(name = "attrName")
    private String attrname;

    /**
     * 获取雪花id
     *
     * @return id - 雪花id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置雪花id
     *
     * @param id 雪花id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取维度id
     *
     * @return dimid - 维度id
     */
    public Long getDimid() {
        return dimid;
    }

    /**
     * 设置维度id
     *
     * @param dimid 维度id
     */
    public void setDimid(Long dimid) {
        this.dimid = dimid;
    }

    /**
     * 获取属性名称
     *
     * @return attrName - 属性名称
     */
    public String getAttrname() {
        return attrname;
    }

    /**
     * 设置属性名称
     *
     * @param attrname 属性名称
     */
    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }
}