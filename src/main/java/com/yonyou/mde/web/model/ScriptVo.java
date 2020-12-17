package com.yonyou.mde.web.model;

import lombok.Data;

import java.util.Map;

@Data
public class ScriptVo {
    private String id;
    private String name;
    private Map<String,Object>vars;
}
