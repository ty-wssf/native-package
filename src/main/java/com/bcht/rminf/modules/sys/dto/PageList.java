package com.bcht.rminf.modules.sys.dto;

import java.util.List;

public class PageList<T> {

    private List<T> rows;
    private int count;

    public PageList(List<T> rows, int count) {
        this.rows = rows;
        this.count = count;
    }

}
