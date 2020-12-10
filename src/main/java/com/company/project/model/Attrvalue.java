package com.company.project.model;

import javax.persistence.*;

public class Attrvalue {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 成员id
     */
    private Long memberid;

    /**
     * 属性值
     */
    @Column(name = "attrValue")
    private String attrvalue;

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
     * 获取成员id
     *
     * @return memberid - 成员id
     */
    public Long getMemberid() {
        return memberid;
    }

    /**
     * 设置成员id
     *
     * @param memberid 成员id
     */
    public void setMemberid(Long memberid) {
        this.memberid = memberid;
    }

    /**
     * 获取属性值
     *
     * @return attrValue - 属性值
     */
    public String getAttrvalue() {
        return attrvalue;
    }

    /**
     * 设置属性值
     *
     * @param attrvalue 属性值
     */
    public void setAttrvalue(String attrvalue) {
        this.attrvalue = attrvalue;
    }
}