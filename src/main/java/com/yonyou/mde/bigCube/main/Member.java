package com.yonyou.mde.bigCube.main;

import com.yonyou.mde.model.graph.DimNode;
import com.yonyou.mde.bigCube.interfaces.IMember;
import lombok.Data;

@Data
public class Member implements IMember {
    private DimNode node;
    private String name;
    public Member(DimNode node, String name) {
        this.node = node;
        this.name = name;
    }
    public String getAttrValue(String attrName){
        return node.getAttr(attrName);
    }
}
