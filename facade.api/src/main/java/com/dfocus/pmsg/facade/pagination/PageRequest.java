package com.dfocus.pmsg.facade.pagination;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 分页请求参数
 */
@Getter
@ToString
@NoArgsConstructor
public class PageRequest {

    public PageRequest(long pageNumber, long pageSize) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    /**
     * 第几页
     */
    private long pageNumber = 1;

    /**
     * 每页条数
     */
    private long pageSize = 20;

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber < 1 ? 1 : pageNumber;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize < 1 ? 20 : pageSize;
    }

}
