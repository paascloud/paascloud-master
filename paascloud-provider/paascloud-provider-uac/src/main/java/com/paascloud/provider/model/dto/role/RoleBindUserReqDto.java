/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：RoleBindUserReqDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The class Role bind user req dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "角色绑定用户")
public class RoleBindUserReqDto implements Serializable {
	private static final long serialVersionUID = 89217138744995863L;

	@ApiModelProperty(value = "角色ID")
	private Long roleId;

	@ApiModelProperty(value = "需要绑定的用户ID集合")
	private List<Long> userIdList;
}
