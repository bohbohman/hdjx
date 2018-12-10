package com.spring.bohbohman.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * 封装API响应对象供自定义查询使用
 * 支持分页
 * Created by sunshow on 5/19/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse<E> implements Serializable {

    private static final long serialVersionUID = 2490364023692403771L;
    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /* 回写请求时的分页设置 */
    private int page;
    private int page_size;

    /* 满足条件的记录总数 */
    private long total = 0;

    private Collection<E> items;

    public ApiResponse(int page, int page_size, Collection<E> items, long total) {
        this.page = page;
        this.page_size = page_size;
        this.items = items;
        this.total = total;
    }

    public ApiResponse(int page, int page_size, Collection<E> items) {
        this(page, page_size, items, 0);
    }

    public ApiResponse(int page, int page_size) {
        this(page, page_size, null);
    }


    public ApiResponse() {
    }

    public int getCount() {
        if (items == null) {
            return 0;
        }

        return items.size();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Collection<E> getItems() {
        if (items == null) {
            items = Lists.newArrayList();
        }
        return items;
    }

    public void setItems(Collection<E> pagedData) {
        this.items = pagedData;
    }
}