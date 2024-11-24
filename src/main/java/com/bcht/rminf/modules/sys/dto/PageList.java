package com.bcht.rminf.modules.sys.dto;

import java.util.List;

public class PageList<T> {

    private List<T> rows;
    private long total;

    public PageList(List<T> rows, long total) {
        this.rows = rows;
        this.total = total;
    }

}
