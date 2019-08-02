/**
 *
 */
package com.face.page;

import java.io.Serializable;

/**
 * @author Administrator
 *
 */
public abstract class BasePage implements IPage, Serializable {
    private static final long serialVersionUID = 1L;
    public static int DEFAULT_PAGE_SIZE = 20;
    //查询页码
    private int pageNo = 1;

    //每页条数
    private int pageSize = DEFAULT_PAGE_SIZE;

    private int currentResult;

    //总记录数
    private long total = -1;

    //总页数
    private int totalPage;

    //排序列
    private String sidx;

    //排序，升序还是降序
    private String sord;

    public BasePage() {

    }

    public BasePage(int pageNo, int pageSize, int total) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        if (total < 0) {
            this.total = 0;
            return;
        }
        this.total = total;
    }

    public int getFirstResult() {
        return (this.pageNo - 1) * this.pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalPage() {
        if (this.totalPage <= 0) {
            this.totalPage = Long.valueOf(this.total / this.pageSize).intValue();
            if ((this.totalPage == 0) || (this.total % this.pageSize != 0)) {
                this.totalPage += 1;
            }
        }
        return this.totalPage;
    }


    public boolean isFirstPage() {
        return this.pageNo <= 1;
    }

    public boolean isLastPage() {
        return this.pageNo >= getTotalPage();
    }

    public int getNextPage() {
        if (isLastPage()) {
            return this.pageNo;
        }
        return this.pageNo + 1;
    }

    public int getCurrentResult() {
        this.currentResult = ((getPageNo() - 1) * getPageSize());
        if (this.currentResult < 0) {
            this.currentResult = 0;
        }
        return this.currentResult;
    }

    public int getPrePage() {
        if (isFirstPage()) {
            return this.pageNo;
        }
        return this.pageNo - 1;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }
}
