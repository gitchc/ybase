package com.yonyou.mde.web.model.vos;

import com.yonyou.mde.web.model.entity.LayoutDim;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghch
 * @Date 2021/1/27 13:39
 */
@Data
public class ViewVO {
    private List<LayoutDim> page = new ArrayList<>();
    private List<LayoutDim> row = new ArrayList<>();
    private List<LayoutDim> col = new ArrayList<>();
    private Integer position;
    private String viewid;
    private String cubeid;
    private String viewname;
}
