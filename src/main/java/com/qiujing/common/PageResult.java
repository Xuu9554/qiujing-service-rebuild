package com.qiujing.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private static final long serialVersionUID = -8860964191650309173L;

    /**
     * 每页记录数
     */
    private long pageSize;

    /**
     * 当前页
     */
    private long pageNum;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 分页信息
     */
    private List<T> list;

}