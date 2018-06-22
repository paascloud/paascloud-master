/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PageUtil.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.page;

import lombok.Data;


/**
 * The class Page util.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class PageUtil {

	/**
	 * The cur page.当前页
	 */
	private int currentPage = 1;

	/**
	 * The next page.下一页
	 */
	private int nextPage;

	/**
	 * The pre page. 上一页
	 */
	private int prePage;

	/**
	 * The total row. 总条数
	 */
	private int totalRow;

	/**
	 * The page size.每页条数
	 */
	private int pageSize = 10;

	/**
	 * The total page.总页数
	 */
	private int totalPage;

	/**
	 * The start. 开始条数
	 */
	private int start;

	/**
	 * The buttons.
	 */
	private int[] buttons;

	/**
	 * 当前页条数
	 */
	private int curPageSize;

	/**
	 * Instantiates a new page util.
	 */
	public PageUtil() {

	}

	/**
	 * Instantiates a new page util.
	 *
	 * @param currentPage the current page
	 */
	public PageUtil(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * Instantiates a new page util.
	 *
	 * @param currentPage the current page
	 * @param pageSize    the page size
	 */
	public PageUtil(int currentPage, int pageSize) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
	}

}
