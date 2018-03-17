package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.OmcShipping;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Omc shipping mapper.
 *
 * @author paascloud.net@gmail.com
 */
@Mapper
@Component
public interface OmcShippingMapper extends MyMapper<OmcShipping> {
	/**
	 * Delete by shipping id user id int.
	 *
	 * @param userId     the user id
	 * @param shippingId the shipping id
	 *
	 * @return the int
	 */
	int deleteByShippingIdUserId(@Param("userId") Long userId, @Param("shippingId") Integer shippingId);

	/**
	 * Select by shipping id user id omc shipping.
	 *
	 * @param userId     the user id
	 * @param shippingId the shipping id
	 *
	 * @return the omc shipping
	 */
	OmcShipping selectByShippingIdUserId(@Param("userId") Long userId, @Param("shippingId") Long shippingId);

	/**
	 * Select by user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<OmcShipping> selectByUserId(@Param("userId") Long userId);

	/**
	 * Select default address by user id omc shipping.
	 *
	 * @param userId the user id
	 *
	 * @return the omc shipping
	 */
	OmcShipping selectDefaultAddressByUserId(Long userId);
}