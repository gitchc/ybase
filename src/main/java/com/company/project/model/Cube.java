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
     * 实体表名
     */
    private String facttable;

    /**
     * id列
     */
    private String pkcol;

    /**
     * 度量列
     */
    private String measurecol;

    /**
     * cube排序
     */
    private Integer postion;

    /**
     * 状态,0自动加载,1手动加载
     */
    private Byte autoload;

    /**
     * 包含的id信息
     */
    private String dimids;

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
     * 获取实体表名
     *
     * @return facttable - 实体表名
     */
    public String getFacttable() {
        return facttable;
    }

    /**
     * 设置实体表名
     *
     * @param facttable 实体表名
     */
    public void setFacttable(String facttable) {
        this.facttable = facttable;
    }

    /**
     * 获取id列
     *
     * @return pkcol - id列
     */
    public String getPkcol() {
        return pkcol;
    }

    /**
     * 设置id列
     *
     * @param pkcol id列
     */
    public void setPkcol(String pkcol) {
        this.pkcol = pkcol;
    }

    /**
     * 获取度量列
     *
     * @return measurecol - 度量列
     */
    public String getMeasurecol() {
        return measurecol;
    }

    /**
     * 设置度量列
     *
     * @param measurecol 度量列
     */
    public void setMeasurecol(String measurecol) {
        this.measurecol = measurecol;
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
     * 获取状态,0自动加载,1手动加载
     *
     * @return autoload - 状态,0自动加载,1手动加载
     */
    public Byte getAutoload() {
        return autoload;
    }

    /**
     * 设置状态,0自动加载,1手动加载
     *
     * @param autoload 状态,0自动加载,1手动加载
     */
    public void setAutoload(Byte autoload) {
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
}