package com.jumbox.demo.common.utils;

import com.github.pagehelper.PageInfo;

import java.util.List;

public class PageResult {

    private int page;			// 当前页数
    private int total;			// 总页数
    private long records;		// 总记录数
    private List<?> rows;		// 每行显示的内容

    public PageResult(){
    }

    public PageResult(PageInfo pageInfo){
        this.page = pageInfo.getPageNum();
        this.total = pageInfo.getPages();
        this.records = pageInfo.getTotal();
        this.rows = pageInfo.getList();
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotal() {
        return total;
    }
    public void setTotal(int total) {
        this.total = total;
    }
    public long getRecords() {
        return records;
    }
    public void setRecords(long records) {
        this.records = records;
    }
    public List<?> getRows() {
        return rows;
    }
    public void setRows(List<?> rows) {
        this.rows = rows;
    }

}
