package com.yonyou.mde.web.model.entity;

import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Description: 视图的行列布局类
 * @Author chenghuichao
 * @Date 2021/1/14 9:32
 */
@Data
public class LayoutDim {
    String dimId;
    String dimName;
    String dimCode;
    String selectedMember;
    String scope;
    List<LayoutMember> options;
}
