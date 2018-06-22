/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：GroupBindUserDto.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto.group;

import com.paascloud.provider.model.dto.role.BindUserDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * The class Group bind user dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "GroupBindUserDto")
public class GroupBindUserDto implements Serializable {

	private static final long serialVersionUID = 1383005712348886707L;
	/**
	 * 未绑定的用户集合
	 */
	@ApiModelProperty(value = "所有用户集合")
	private Set<BindUserDto> allUserSet;

	/**
	 * 已经绑定的用户集合
	 */
	@ApiModelProperty(value = "已经绑定的用户集合")
	private Set<Long> alreadyBindUserIdSet;

}
