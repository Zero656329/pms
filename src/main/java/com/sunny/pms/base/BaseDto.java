package com.sunny.pms.base;

public class BaseDto {
    /** 当前记录起始索引 */
    private Integer pageNo = 1;
    /** 每页显示记录数 */
    private Integer pageSize = 1;


    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
