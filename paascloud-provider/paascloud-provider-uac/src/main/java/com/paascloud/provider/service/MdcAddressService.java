/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcAddressService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.AddressDTO;

/**
 * The interface Omc order service.
 *
 * @author paascloud.net@gmail.com
 */
public interface MdcAddressService {
	/**
	 * Gets address by id.
	 *
	 * @param addressId the address id
	 *
	 * @return the address by id
	 */
	AddressDTO getAddressById(Long addressId);
}
