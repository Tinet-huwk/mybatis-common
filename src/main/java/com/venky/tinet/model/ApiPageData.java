package com.venky.tinet.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据对象的统一封装
 *
 * @param <T> 数据集对象泛型
 * @author 侯法超
 * @date 2018/08/28
 */
public class ApiPageData<T> {
	public static final int DEFAULT_PAGE_SIZE = 15;
	public static final int MIN_PAGE_SIZE = 10;
	public static final int MAX_PAGE_SIZE = 100;
	public static final int DEFAULT_PAGE_OFFSET = 0;
	public static final int MAX_PAGE_OFFSET = 10000;

    /**
     * 当前页第一条数据的位置,从0开始则表示当前页为第1页
     */
    private int start;


    /**
	 * 当前页码
	 */
	private int pageNumber;

	/**
	 * 每页的记录数
	 */
	private int pageSize = 15;

	/**
	 * 当前页中存放的记录
	 */
	private List<T> data;

	/**
	 * 总记录数
	 */
	private int totalCount;

	private Object message;

	/**
	 * 构造方法，只构造空页
	 */
	public ApiPageData() {
		this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
	}

	/**
	 * 构造方法，只构造空页,初始化pagesize
	 */
	public ApiPageData(int pageSize) {
		this(0, 0, pageSize, new ArrayList<T>());
	}


	/**
	 * 默认构造方法
	 *
     * @param start 本页数据在数据库中的起始位置
	 * @param totalCount 数据库中总记录条数
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 */
	public ApiPageData(int start, int totalCount, int pageSize, List<T> data) {
	    this.start = start;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.data = data;
	}

	/**
	 * 默认构造方法,不分页查询全部
	 *
	 * @param list 包含的数据
	 */
	public ApiPageData(List<T> list) {
		if (list != null) {
			this.pageSize = DEFAULT_PAGE_SIZE;
			this.totalCount = list.size();
			this.data = list;
		}
	}

	/**
	 * 取数据库中包含的总记录数
	 */
	public int getTotalCount() {
		return this.totalCount;
	}

	/**
	 * 设置数据库中包含的总记录数
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 取总页数。
	 */
	public int getTotalPageCount() {
		if (totalCount == 0) {
			return 0;
		} else {
			return ((totalCount - 1) / pageSize) + 1;
		}
	}

	/**
	 * 取每页数据容量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 取当前页码,页码从1开始
	 */
	public int getPageNumber() {
        if (start == 0) {
            pageNumber = 1;
        } else {
            int curPage = (start / pageSize) + 1;
            if (curPage > this.getTotalPageCount()) {
                curPage = this.getTotalPageCount();
            }
            pageNumber = curPage;
        }
		return pageNumber;
	}

	/**
	 * 是否有下一页
	 */
	public boolean hasNextPage() {
		return (this.getPageNumber() < this.getTotalPageCount());
	}

	/**
	 * 是否有上一页
	 */
	public boolean hasPreviousPage() {
		return (this.getPageNumber() > 1);
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public List<T> getData() {
		return data;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

}
