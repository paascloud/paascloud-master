/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcRpcServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.model.dto.gaode.GaodeLocation;
import com.paascloud.provider.service.OpcGaodeFeignApi;
import com.paascloud.provider.service.OpcRpcService;
import com.paascloud.wrapper.Wrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * The class Opc rpc service.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Service
public class OpcRpcServiceImpl implements OpcRpcService {
	@Resource
	private OpcGaodeFeignApi opcGaodeFeignApi;

	@Override
	public String getLocationById(String addressId) {
		try {
			Wrapper<GaodeLocation> wrapper = opcGaodeFeignApi.getLocationByIpAddr(addressId);
			if (wrapper == null) {
				throw new MdcBizException(ErrorCodeEnum.GL99990002);
			}
			if (wrapper.error()) {
				throw new MdcBizException(ErrorCodeEnum.MDC10021002);
			}

			GaodeLocation result = wrapper.getResult();

			assert result != null;
			return result.getProvince().contains("市") ? result.getCity() : result.getProvince() + GlobalConstant.Symbol.SHORT_LINE + result.getCity();
		} catch (Exception e) {
			log.error("getLocationById={}", e.getMessage(), e);
		}
		return null;
	}
}
