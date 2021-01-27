package com.yonyou.mde.web.model.entity;

import com.yonyou.mde.web.model.entity.PageMember;
import lombok.Data;

import java.util.List;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghuichao
 * @Date 2021/1/14 9:32
 */
@Data
public class PageDim {
    String dimId;
    String dimName;
    String dimCode;
    String selectedMember;
    String scope;
    List<PageMember> options;
}
