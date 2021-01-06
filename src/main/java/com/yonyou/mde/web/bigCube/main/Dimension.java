package com.yonyou.mde.web.bigCube.main;

import com.yonyou.mde.model.graph.DimNode;
import com.yonyou.mde.model.graph.DimTree;
import com.yonyou.mde.web.bigCube.interfaces.IDimension;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Dimension implements IDimension {
    private DimTree dimTree;
    private String name;

    public Dimension(DimTree dimTree, String name) {
        this.dimTree = dimTree;
        this.name = name;
    }

    public List<Member> getMembers() {
        List<Member> members = new ArrayList<>();
        for (DimNode node : dimTree.nodes()) {
            members.add(new Member(node, node.getCode()));
        }
        return members;
    }

    public Member getMember(String name) {
        String uname = name.toUpperCase();
        return new Member(dimTree.getNodeByCode(uname), uname);
    }
}
