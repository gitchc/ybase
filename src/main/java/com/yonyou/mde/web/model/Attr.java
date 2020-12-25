package com.yonyou.mde.web.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class Attr {
    /**
     * 雪花id
     */
    @Id
    @JSONField(serializeUsing= ToStringSerializer.class)
    private String id;

    /**
     * 维度id
     */
    @JSONField(serializeUsing= ToStringSerializer.class)
    private String dimid;

    /**
     * 属性名称
     */
    @Column(name = "attrName")
    private String attrname;


}