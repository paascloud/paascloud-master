/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptGetUrlRequest.java
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

import java.util.List;

/**
 * The class Opt get url request.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel
public class OptGetUrlRequest {
	@ApiModelProperty(value = "附件ID")
	private Long attachmentId;

	@ApiModelProperty(value = "超时时间")
	private Long expires;

	@ApiModelProperty(value = "是否需要解密")
	private boolean encrypt;

	@ApiModelProperty(value = "附件ID集合")
	private List<Long> attachmentIdList;

}
