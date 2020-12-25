package com.yonyou.mde.web.model;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

@Data
public class Script {
    /**
     * 雪花id
     */
    @Id
    private String id;

    /**
     * 脚本名称
     */
    private String name;

    /**
     * 上次执行状态,0未执行,1执行成功,2执行失败
     */
    private Integer laststatus;

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
    private String content;
    //版本信息
    private int version;

}