package com.yonyou.mde.web.model.vos;

import lombok.Data;

@Data
public class Completer {
    private String caption;
    private String value;
    private int score = 100;
    private String meta = "自定义函数";

    public Completer(String caption, String value) {
        this.caption = caption;
        this.value = value;
    }
}
