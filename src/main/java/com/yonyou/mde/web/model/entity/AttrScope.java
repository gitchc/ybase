package com.yonyou.mde.web.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2021/3/8
 */
public class AttrScope {
    boolean union = true;//不同属性间 求交集还是并集
    List<AttrKV> attrs = new ArrayList<>();

    public Map<String, List<AttrKV>> getAttrs() {
        return attrs.stream().collect(Collectors.groupingBy(AttrKV::getAttr));
    }

    public boolean isUnion() {
        return union;
    }

    public void setUnion(boolean union) {
        this.union = union;
    }

    public void setAttrs(List<AttrKV> attrs) {
        this.attrs = attrs;
    }
}
