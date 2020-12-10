package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Script {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 脚本名称
     */
    @Column(name = "scrpitName")
    private String scrpitname;

    /**
     * 状态,0正常,1不执行
     */
    private Byte status;

    /**
     * 上次执行状态,0未执行,1执行成功,2执行失败
     */
    @Column(name = "lastStatus")
    private Byte laststatus;

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
     * 获取脚本名称
     *
     * @return scrpitName - 脚本名称
     */
    public String getScrpitname() {
        return scrpitname;
    }

    /**
     * 设置脚本名称
     *
     * @param scrpitname 脚本名称
     */
    public void setScrpitname(String scrpitname) {
        this.scrpitname = scrpitname;
    }

    /**
     * 获取状态,0正常,1不执行
     *
     * @return status - 状态,0正常,1不执行
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态,0正常,1不执行
     *
     * @param status 状态,0正常,1不执行
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取上次执行状态,0未执行,1执行成功,2执行失败
     *
     * @return lastStatus - 上次执行状态,0未执行,1执行成功,2执行失败
     */
    public Byte getLaststatus() {
        return laststatus;
    }

    /**
     * 设置上次执行状态,0未执行,1执行成功,2执行失败
     *
     * @param laststatus 上次执行状态,0未执行,1执行成功,2执行失败
     */
    public void setLaststatus(Byte laststatus) {
        this.laststatus = laststatus;
    }

    /**
     * 获取更新时间
     *
     * @return lastupdate - 更新时间
     */
    public Date getLastupdate() {
        return lastupdate;
    }

    /**
     * 设置更新时间
     *
     * @param lastupdate 更新时间
     */
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     * 获取更新人
     *
     * @return updateuser - 更新人
     */
    public String getUpdateuser() {
        return updateuser;
    }

    /**
     * 设置更新人
     *
     * @param updateuser 更新人
     */
    public void setUpdateuser(String updateuser) {
        this.updateuser = updateuser;
    }

    /**
     * 获取脚本信息
     *
     * @return scrpitContent - 脚本信息
     */
    public String getScrpitcontent() {
        return scrpitcontent;
    }

    /**
     * 设置脚本信息
     *
     * @param scrpitcontent 脚本信息
     */
    public void setScrpitcontent(String scrpitcontent) {
        this.scrpitcontent = scrpitcontent;
    }
}