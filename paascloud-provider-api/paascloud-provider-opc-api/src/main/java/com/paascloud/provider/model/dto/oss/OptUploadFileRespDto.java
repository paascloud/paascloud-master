/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptUploadFileRespDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Opt upload file resp dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "OptUploadFileReqDto")
public class OptUploadFileRespDto implements Serializable {

	private static final long serialVersionUID = -8008720269972450739L;

	@ApiModelProperty(value = "附件ID")
	private Long attachmentId;
	@ApiModelProperty(value = "文件完整url")
	private String attachmentUrl;
	@ApiModelProperty(value = "文件名")
	private String attachmentName;
	@ApiModelProperty(value = "文件路径(等于七牛文件名)")
	private String attachmentPath;
	@ApiModelProperty(value = "文件类型")
	private String fileType;
	@ApiModelProperty(value = "关联业务单号")
	private String refNo;

}
