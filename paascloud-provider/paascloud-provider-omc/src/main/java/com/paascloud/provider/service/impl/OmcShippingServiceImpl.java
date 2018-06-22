/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OmcShippingServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.exceptions.OmcBizException;
import com.paascloud.provider.mapper.OmcShippingMapper;
import com.paascloud.provider.model.constant.OmcApiConstant;
import com.paascloud.provider.model.domain.OmcShipping;
import com.paascloud.provider.service.OmcShippingService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Omc shipping service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
public class OmcShippingServiceImpl extends BaseService<OmcShipping> implements OmcShippingService {

	@Resource
	private OmcShippingMapper omcShippingMapper;

	@Override
	public int saveShipping(LoginAuthDto loginAuthDto, OmcShipping shipping) {
		int resultInt;
		shipping.setUpdateInfo(loginAuthDto);
		if (shipping.isNew()) {
			resultInt = omcShippingMapper.insertSelective(shipping);
		} else {
			resultInt = omcShippingMapper.updateByPrimaryKeySelective(shipping);
		}
		return resultInt;
	}

	@Override
	public int deleteShipping(Long userId, Integer shippingId) {

		return omcShippingMapper.deleteByShippingIdUserId(userId, shippingId);
	}

	@Override
	public OmcShipping selectByShippingIdUserId(Long userId, Long shippingId) {
		return omcShippingMapper.selectByShippingIdUserId(userId, shippingId);
	}

	@Override
	public List<OmcShipping> queryShippingList(OmcShipping shipping) {
		return omcShippingMapper.select(shipping);
	}

	@Override
	public PageInfo queryShippingListWithPage(OmcShipping shipping) {

		shipping.setOrderBy("update_time desc");
		PageHelper.startPage(shipping.getPageNum(), shipping.getPageSize());
		if (StringUtils.isEmpty(shipping.getReceiverName())) {
			shipping.setReceiverName(null);
		}
		if (StringUtils.isEmpty(shipping.getReceiverMobileNo())) {
			shipping.setReceiverMobileNo(null);
		}
		List<OmcShipping> omcShippingList = this.queryShippingList(shipping);
		return new PageInfo<>(omcShippingList);
	}

	@Override
	public PageInfo queryListWithPageByUserId(Long userId, int pageNum, int pageSize) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		PageHelper.startPage(pageNum, pageSize);
		//TODO 写死 不维护收人列表
		List<OmcShipping> omcShippingList = this.selectByUserId(1L);
		return new PageInfo<>(omcShippingList);
	}

	@Override
	public List<OmcShipping> selectByUserId(Long userId) {
		Preconditions.checkArgument(userId != null, ErrorCodeEnum.UAC10011001.msg());
		return omcShippingMapper.selectByUserId(userId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int setDefaultAddress(LoginAuthDto loginAuthDto, Long addressId) {
		Long userId = loginAuthDto.getUserId();
		Preconditions.checkArgument(addressId != null, "地址ID不能为空");

		// 1. 查找当前默认地址
		OmcShipping omcShipping = omcShippingMapper.selectDefaultAddressByUserId(userId);
		if (PublicUtil.isEmpty(omcShipping)) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031007);
		}
		// 2. 判断默认地址和当前传入地址是否相同
		if (addressId.equals(omcShipping.getId())) {
			logger.info("所选地址和当前用户默认地址相同 userId={}, addressId={}", userId, addressId);
			return 1;
		}
		// 3. 相同不处理不相同把当前改为非默认, 把当前地址改为默认地址
		setDefault(loginAuthDto, addressId, OmcApiConstant.Shipping.DEFAULT);
		setDefault(loginAuthDto, omcShipping.getId(), OmcApiConstant.Shipping.NOT_DEFAULT);

		return 1;
	}

	private void setDefault(LoginAuthDto loginAuthDto, Long addressId, int isDefault) {
		int result;
		OmcShipping updateNotDefault = new OmcShipping();
		updateNotDefault.setDefaultAddress(isDefault);
		updateNotDefault.setUpdateInfo(loginAuthDto);
		updateNotDefault.setId(addressId);
		result = omcShippingMapper.updateByPrimaryKeySelective(updateNotDefault);
		if (result < 1) {
			throw new OmcBizException(ErrorCodeEnum.OMC10031008, addressId);
		}
	}
}