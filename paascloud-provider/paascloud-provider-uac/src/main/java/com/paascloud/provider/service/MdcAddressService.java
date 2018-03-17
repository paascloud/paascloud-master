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
