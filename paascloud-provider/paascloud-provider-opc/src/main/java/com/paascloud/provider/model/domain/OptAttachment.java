/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptAttachment.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Opt attachment.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_opt_attachment")
public class OptAttachment extends BaseEntity {

	private static final long serialVersionUID = -2419047791219240612L;
	/**
	 * 中心名称(英文简写)
	 */
	@Column(name = "center_name")
	private String centerName;

	/**
	 * 文件服务器根目录
	 */
	@Column(name = "bucket_name")
	private String bucketName;

	/**
	 * 上传附件的相关业务流水号
	 */
	@Column(name = "ref_no")
	private String refNo;

	/**
	 * 附件名称
	 */
	private String name;

	/**
	 * 附件存储相对路径
	 */
	private String path;

	/**
	 * 附件类型
	 */
	private String type;

	/**
	 * 附件格式
	 */
	private String format;

	/**
	 * 备注
	 */
	private String description;
}