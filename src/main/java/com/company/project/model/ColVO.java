package com.company.project.model;

import lombok.Data;

@Data
public class ColVO {
    private String field;
    private String title;
    private int width = 100;
    private String edit="";
    private String fixed = "";
    private String event ;

    public ColVO(String field, String attrid) {
        this.field = field;
        this.title = field;
        this.edit = "text";
        this.event = field+","+attrid;
    }

    public ColVO(String field, String title, int width) {
        this.field = field;
        this.title = title;
        this.width = width;
        this.fixed = "left";

    }
}
