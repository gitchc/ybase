package com.yonyou.mde.web.model.entity;

import lombok.Data;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghch
 * @Date 2021/1/14 9:41
 */
@Data
public class LayoutMember {
    private String displayName;
    private String id;
    private String code;
    private String dimCode;
    private int childCount;
    private int generation;

}
