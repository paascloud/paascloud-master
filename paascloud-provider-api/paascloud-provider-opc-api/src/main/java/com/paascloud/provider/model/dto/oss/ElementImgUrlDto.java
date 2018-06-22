/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ElementImgUrlDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.oss;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Element img url dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@AllArgsConstructor
public class ElementImgUrlDto implements Serializable {
	public ElementImgUrlDto() {
	}

	private static final long serialVersionUID = -5800852605728871320L;
	/**
	 * 图片完整地址
	 */
	private String url;
	/**
	 * 图片名称
	 */
	private String name;
	/**
	 * 图片关联的附件ID
	 */
	private Long attachmentId;
}
