package com.company.project.model;


import lombok.Data;

@Data
public class AttrValueVO extends Position {
    private String attrname;
    private String attrvalue;
    private String code;
    private String name;
    private String dimid;
}
