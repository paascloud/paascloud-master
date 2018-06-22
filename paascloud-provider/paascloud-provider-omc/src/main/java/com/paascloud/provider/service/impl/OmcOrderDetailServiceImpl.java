/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcOrderDetailServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.exceptions.OmcBizException;
import com.paascloud.provider.mapper.OmcOrderDetailMapper;
import com.paascloud.provider.model.domain.OmcOrderDetail;
import com.paascloud.provider.service.OmcOrderDetailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Omc order detail service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
public class OmcOrderDetailServiceImpl extends BaseService<OmcOrderDetail> implements OmcOrderDetailService {
	@Resource
	private OmcOrderDetailMapper omcOrderDetailMapper;

	@Override
	public List<OmcOrderDetail> getListByOrderNoUserId(String orderNo, Long userId) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(orderNo), "订单号不能为空");

		return omcOrderDetailMapper.getListByOrderNoUserId(orderNo, userId);
	}

	@Override
	public List<OmcOrderDetail> getListByOrderNo(String orderNo) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(orderNo), "订单号不能为空");
		return omcOrderDetailMapper.getListByOrderNo(orderNo);
	}

	@Override
	public void batchInsertOrderDetail(List<OmcOrderDetail> omcOrderDetailList) {
		int insertResult = omcOrderDetailMapper.batchInsertOrderDetail(omcOrderDetailList);
		if (insertResult < omcOrderDetailList.size()) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031009);
		}
	}
}