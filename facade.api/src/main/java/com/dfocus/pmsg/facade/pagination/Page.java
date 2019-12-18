package com.dfocus.pmsg.facade.pagination;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页响应体
 */
@ToString
public class Page<T> {

    public Page(List<T> content, PageRequest pageable, long totalElements) {
        this.content.addAll(content);
        this.pageable = pageable;
        this.totalElements = totalElements;
    }

    /**
     * 分页请求参数
     */
    private final PageRequest pageable;

    /**
     * 第几页
     */
    public long getPageNumber() {
        return pageable.getPageNumber();
    }

    /**
     * 每页条数
     */
    public long getPageSize() {
        return pageable.getPageSize();
    }

    /**
     * 内容
     */
    @Getter
    private final List<T> content = new ArrayList<>();

    /**
     * 总条数
     */
    @Getter
    private long totalElements;

    /**
     * 当前页条数
     */
    public int getCurrentSize() {
        return content.size();
    }

    /**
     * 总页数
     */
    public long getTotalPages() {
        return (long) Math.ceil((double) totalElements / (double) getPageSize());
    }

    /**
     * 是否第一页
     */
    public boolean isFirst() {
        return getPageNumber() == 1;
    }

    /**
     * 是否最后一页
     */
    public boolean isLast() {
        return getPageNumber() == getTotalPages();
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        List<U> convertedContent = this.getContent().stream().map(converter).collect(Collectors.toList());
        return new Page<>(convertedContent, this.pageable, getTotalElements());
    }

}
