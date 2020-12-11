package com.company.project.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class Script {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 脚本名称
     */
    @Column(name = "scrpitName")
    private String scrpitname;

    /**
     * 状态,0正常,1不执行
     */
    private int status;

    /**
     * 上次执行状态,0未执行,1执行成功,2执行失败
     */
    @Column(name = "lastStatus")
    private int laststatus;

    /**
     * 更新时间
     */
    private Date lastupdate;

    /**
     * 更新人
     */
    private String updateuser;

    /**
     * 脚本信息
     */
    @Column(name = "scrpitContent")
    private String scrpitcontent;


}