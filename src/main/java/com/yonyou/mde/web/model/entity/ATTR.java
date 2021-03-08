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
@Data
public class ATTR {
    boolean union = true;//不同属性间 求交集还是并集
    List<ATTRVALUE> attrs = new ArrayList<>();

    public Map<String, List<ATTRVALUE>> getAttrs() {
        return attrs.stream().collect(Collectors.groupingBy(ATTRVALUE::getAttr));
    }
}
