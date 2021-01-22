package com.yonyou.mde.web.model.vos;

import lombok.Data;

@Data
public class Completer {
    private String caption;
    private String value;
    private int score = 100;
    private String meta;

    public Completer(String caption, String value,String meta) {
        this.caption = caption;
        this.value = value;
        this.meta = meta;
    }
    public Completer(String caption, String value,String meta,int score) {
        this.caption = caption;
        this.value = value;
        this.meta = meta;
        this.score =score;
    }
}
