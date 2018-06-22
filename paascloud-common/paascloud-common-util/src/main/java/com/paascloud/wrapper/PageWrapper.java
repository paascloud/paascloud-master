/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PageWrapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.wrapper;


import com.paascloud.page.PageUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class Page wrapper.
 *
 * @param <T> the type parameter @author paascloud.net@gmail.com
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PageWrapper<T> extends Wrapper<T> {

	private static final long serialVersionUID = 666985064788933395L;

	private PageUtil pageUtil;


	/**
	 * Instantiates a new Page wrapper.
	 */
	PageWrapper() {
		super();
	}


	/**
	 * Instantiates a new Page wrapper.
	 *
	 * @param code    the code
	 * @param message the message
	 */
	public PageWrapper(int code, String message) {
		super(code, message);
	}

	/**
	 * Instantiates a new pageWrapper default code=200
	 *
	 * @param result   the result
	 * @param pageUtil the page util
	 */
	public PageWrapper(T result, PageUtil pageUtil) {
		super();
		this.setResult(result);
		this.setPageUtil(pageUtil);
	}

	/**
	 * Instantiates a new Page wrapper.
	 *
	 * @param code     the code
	 * @param message  the message
	 * @param result   the result
	 * @param pageUtil the page util
	 */
	PageWrapper(int code, String message, T result, PageUtil pageUtil) {
		super(code, message, result);
		this.pageUtil = pageUtil;
	}

	/**
	 * Sets the 分页数据 , 返回自身的引用.
	 *
	 * @param pageUtil the page util
	 *
	 * @return the page wrapper
	 */
	public PageWrapper<T> pageUtil(PageUtil pageUtil) {
		this.setPageUtil(pageUtil);
		return this;
	}

	/**
	 * Sets the 结果数据 , 返回自身的引用.
	 *
	 * @param result the result
	 *
	 * @return the page wrapper
	 */
	@Override
	public PageWrapper<T> result(T result) {
		super.setResult(result);
		return this;
	}
}
