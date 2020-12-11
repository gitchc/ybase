package com.company.project.model;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Member {
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
     * 父id
     */
    private String pid;

    /**
     * 成员名称
     */
    private String name;

    /**
     * 成员编码
     */
    private String code;

    /**
     * 成员排序
     */
    private Integer position;

    /**
     * 成员代
     */
    private Integer generation;

    /**
     * 数据类型:0数值,1货币,2整数,3时间类型,4文本,5下拉列表,6手动上卷,7自动上卷
     */
    private Integer datatype;

    /**
     * 成员类型,0维度,1成员,2共享成员
     */
    private Integer membertype;

    /**
     * 成员状态,0正常,1只读,2冻结
     */
    private Integer status;

    /**
     * 权重
     */
    private Float weight;

    /**
     * 唯一编码
     */
    private String uniquecode;

    /**
     * 唯一排序
     */
    private String uniqueposition;


}