package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.model.dto.AddressDTO;
import com.paascloud.provider.service.MdcAddressQueryFeignApi;
import com.paascloud.provider.service.MdcAddressService;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * The class Mdc address service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdcAddressServiceImpl implements MdcAddressService {
	@Resource
	private MdcAddressQueryFeignApi mdcAddressQueryFeignApi;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public AddressDTO getAddressById(Long addressId) {
		Preconditions.checkArgument(addressId != null, "地址ID不能为空");
		Wrapper<AddressDTO> wrapper = mdcAddressQueryFeignApi.getById(addressId);

		if (wrapper == null) {
			throw new MdcBizException(ErrorCodeEnum.GL99990002);
		}
		if (wrapper.error()) {
			throw new MdcBizException(ErrorCodeEnum.MDC10021002);
		}
		return wrapper.getResult();
	}
}
