package com.familyan.smarth.manager.util;

/**
 * Created by Admin on 2015/8/18.
 */
public class DWZPage {
    Integer pageNum = 1;
    Integer numPerPage = 20;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        if(pageNum == null || pageNum < 1)
            pageNum = 1;
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        if(numPerPage == null || numPerPage < 1)
            numPerPage = 20;
        this.numPerPage = numPerPage;
    }
    public int getStart(){
        return (pageNum - 1)  *  numPerPage;
    }
}
