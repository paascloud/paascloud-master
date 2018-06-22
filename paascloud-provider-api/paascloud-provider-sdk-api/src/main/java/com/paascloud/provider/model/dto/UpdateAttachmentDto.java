/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UpdateAttachmentDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;


import com.paascloud.base.dto.LoginAuthDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新福建表.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@AllArgsConstructor
public class UpdateAttachmentDto implements Serializable {
	private static final long serialVersionUID = -768471033009336091L;

	public UpdateAttachmentDto() {

	}

	private String refNo;
	/**
	 * 商品图片流水号集合
	 */
	private List<Long> attachmentIdList;

	/**
	 * 操作人信息
	 */
	private LoginAuthDto loginAuthDto;
}
