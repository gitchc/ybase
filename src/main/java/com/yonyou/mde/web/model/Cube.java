package com.yonyou.mde.web.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class Cube {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * cube名称
     */
    private String cubename;

    /**
     * cube编码
     */
    private String cubecode;

    /**
     * 加载SQL
     */
    private String loadsql;

    /**
     * 状态:1自动加载,0手动加载
     */
    private Integer autoload;

    /**
     * 状态:1自动生成,0手动设置
     */
    private Integer autosql;

    /**
     * cube排序
     */
    private Integer position;

    /**
     * 维度的ide信息
     */
    private String dimids;


}