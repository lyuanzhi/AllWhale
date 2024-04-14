package com.jumbox.demo.mbg.vo;

import com.jumbox.demo.common.utils.PageResult;

public class ChatVo {

    private PageResult pagedResult;

    private String name;

    private Double price;

    private String title;

    public PageResult getPagedResult() {
        return pagedResult;
    }

    public void setPagedResult(PageResult pagedResult) {
        this.pagedResult = pagedResult;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
