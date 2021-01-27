package com.yonyou.mde.web.model.vos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Description: 视图树
 * @Author chenghch
 * @Date 2021/1/27 10:59
 */
@Data
public class ViewTree {
    private String id;
    private String cubeid;
    private String viewid;
    private String title;
    private List<ViewTree> children = new ArrayList<>();

    public void add(ViewTree viewTree) {
        this.children.add(viewTree);
    }
}
