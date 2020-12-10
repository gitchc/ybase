package com.company.project.model;

import javax.persistence.*;

public class Member {
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
     * 父id
     */
    private Long pid;

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
    private Byte datatype;

    /**
     * 成员类型,0维度,1成员,2共享成员
     */
    private Byte membertype;

    /**
     * 成员状态,0正常,1只读,2冻结
     */
    private Byte status;

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
     * 获取父id
     *
     * @return pid - 父id
     */
    public Long getPid() {
        return pid;
    }

    /**
     * 设置父id
     *
     * @param pid 父id
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /**
     * 获取成员名称
     *
     * @return name - 成员名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置成员名称
     *
     * @param name 成员名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取成员编码
     *
     * @return code - 成员编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置成员编码
     *
     * @param code 成员编码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取成员排序
     *
     * @return position - 成员排序
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * 设置成员排序
     *
     * @param position 成员排序
     */
    public void setPosition(Integer position) {
        this.position = position;
    }

    /**
     * 获取成员代
     *
     * @return generation - 成员代
     */
    public Integer getGeneration() {
        return generation;
    }

    /**
     * 设置成员代
     *
     * @param generation 成员代
     */
    public void setGeneration(Integer generation) {
        this.generation = generation;
    }

    /**
     * 获取数据类型:0数值,1货币,2整数,3时间类型,4文本,5下拉列表,6手动上卷,7自动上卷
     *
     * @return datatype - 数据类型:0数值,1货币,2整数,3时间类型,4文本,5下拉列表,6手动上卷,7自动上卷
     */
    public Byte getDatatype() {
        return datatype;
    }

    /**
     * 设置数据类型:0数值,1货币,2整数,3时间类型,4文本,5下拉列表,6手动上卷,7自动上卷
     *
     * @param datatype 数据类型:0数值,1货币,2整数,3时间类型,4文本,5下拉列表,6手动上卷,7自动上卷
     */
    public void setDatatype(Byte datatype) {
        this.datatype = datatype;
    }

    /**
     * 获取成员类型,0维度,1成员,2共享成员
     *
     * @return membertype - 成员类型,0维度,1成员,2共享成员
     */
    public Byte getMembertype() {
        return membertype;
    }

    /**
     * 设置成员类型,0维度,1成员,2共享成员
     *
     * @param membertype 成员类型,0维度,1成员,2共享成员
     */
    public void setMembertype(Byte membertype) {
        this.membertype = membertype;
    }

    /**
     * 获取成员状态,0正常,1只读,2冻结
     *
     * @return status - 成员状态,0正常,1只读,2冻结
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置成员状态,0正常,1只读,2冻结
     *
     * @param status 成员状态,0正常,1只读,2冻结
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取权重
     *
     * @return weight - 权重
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    /**
     * 获取唯一编码
     *
     * @return uniquecode - 唯一编码
     */
    public String getUniquecode() {
        return uniquecode;
    }

    /**
     * 设置唯一编码
     *
     * @param uniquecode 唯一编码
     */
    public void setUniquecode(String uniquecode) {
        this.uniquecode = uniquecode;
    }

    /**
     * 获取唯一排序
     *
     * @return uniqueposition - 唯一排序
     */
    public String getUniqueposition() {
        return uniqueposition;
    }

    /**
     * 设置唯一排序
     *
     * @param uniqueposition 唯一排序
     */
    public void setUniqueposition(String uniqueposition) {
        this.uniqueposition = uniqueposition;
    }
}