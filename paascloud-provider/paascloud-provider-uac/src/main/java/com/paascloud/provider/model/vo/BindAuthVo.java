/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：BindAuthVo.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * The class Bind auth vo.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
public class BindAuthVo {
	/**
	 * 包含按钮权限和菜单权限
	 */
	private List<MenuVo> authTree;
	/**
	 * 该角色含有的权限ID
	 */
	private List<Long> checkedAuthList;
}
