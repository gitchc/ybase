package com.yonyou.mde.web.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Description: 类描述
 * @Author chenghch
 * @Date 2021/2/3 17:11
 */
@Data
public class MemberTree {
    private String id;
    private String name;
    private List<MemberTree> children;

    public MemberTree(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void add(MemberTree memberTree) {
        if (children == null) {
            children = new ArrayList<>();
        }
        children.add(memberTree);
    }
}
