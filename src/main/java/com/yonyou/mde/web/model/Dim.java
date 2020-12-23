package com.yonyou.mde.web.model;

import lombok.Data;


@Data
public class Dim {
    String dimName;
    boolean rollUp;

    public Dim(String dimName, boolean rollUp) {
        this.dimName = dimName;
        this.rollUp = rollUp;
    }
}
