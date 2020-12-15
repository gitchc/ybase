package com.company.project.model;

import javax.persistence.*;

public class Cube {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * cube名称
     */
    private String cubename;

    /**
     * cube排序
     */
    private Integer postion;

    /**
     * 状态:1自动加载,0手动加载
     */
    private Integer autoload;

    /**
     * 包含的id信息
     */
    private String dimids;

    /**
     * 加载SQL
     */
    private String loadsql;

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
     * 获取cube名称
     *
     * @return cubename - cube名称
     */
    public String getCubename() {
        return cubename;
    }

    /**
     * 设置cube名称
     *
     * @param cubename cube名称
     */
    public void setCubename(String cubename) {
        this.cubename = cubename;
    }

    /**
     * 获取cube排序
     *
     * @return postion - cube排序
     */
    public Integer getPostion() {
        return postion;
    }

    /**
     * 设置cube排序
     *
     * @param postion cube排序
     */
    public void setPostion(Integer postion) {
        this.postion = postion;
    }

    /**
     * 获取状态:1自动加载,0手动加载
     *
     * @return autoload - 状态:1自动加载,0手动加载
     */
    public Integer getAutoload() {
        return autoload;
    }

    /**
     * 设置状态:1自动加载,0手动加载
     *
     * @param autoload 状态:1自动加载,0手动加载
     */
    public void setAutoload(Integer autoload) {
        this.autoload = autoload;
    }

    /**
     * 获取包含的id信息
     *
     * @return dimids - 包含的id信息
     */
    public String getDimids() {
        return dimids;
    }

    /**
     * 设置包含的id信息
     *
     * @param dimids 包含的id信息
     */
    public void setDimids(String dimids) {
        this.dimids = dimids;
    }

    /**
     * 获取加载SQL
     *
     * @return loadsql - 加载SQL
     */
    public String getLoadsql() {
        return loadsql;
    }

    /**
     * 设置加载SQL
     *
     * @param loadsql 加载SQL
     */
    public void setLoadsql(String loadsql) {
        this.loadsql = loadsql;
    }
}