package com.yonyou.mde.web.model.types;

/**
 * @Author:chenghch
 * @Description:
 * @Date:First Created 2021/3/8
 */
public class ScopeType {
    public static final String MEMBER = "MEMBER";//成员
    public static final String IDESCENDANTS= "IDESCENDANTS"; //成员以及后代
    public static final String DESCENDANTS= "IDESCENDANTS"; //后代
    public static final String LEAVES= "LEAVES"; //最小后代
    public static final String ICHILDREN= "ICHILDREN"; //成员以及子项
    public static final String CHILDREN= "CHILDREN"; //子项
    public static final String ATTR= "ATTR"; //属性

}
