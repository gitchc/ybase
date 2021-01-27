package com.yonyou.mde.web.model.entity;

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
public class ViewLayout {
    private List<PageDim> page = new ArrayList<>();
    private List<PageDim> row = new ArrayList<>();
    private List<PageDim> col = new ArrayList<>();
    private Integer position;
    private String viewid;
    private String cubeid;
    private String viewname;
}
