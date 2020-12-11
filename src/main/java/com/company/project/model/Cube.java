package com.company.project.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Cube {
    /**
     * 雪花id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

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
    private int autoload;

    /**
     * 包含的id信息
     */
    private String dimids;


}