package com.paascloud.provider.service;


import com.paascloud.TreeNode;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.MdcAddress;

import java.util.List;

/**
 * The interface Mdc address service.
 *
 * @author paascloud.net@gmail.com
 */
public interface MdcAddressService extends IService<MdcAddress> {
	/**
	 * 根据PID查询地址信息
	 *
	 * @param pid the pid
	 *
	 * @return the list
	 */
	List<MdcAddress> listByPid(Long pid);

	/**
	 * Gets by id.
	 *
	 * @param id the id
	 *
	 * @return the by id
	 */
	MdcAddress getById(Long id);

	/**
	 * Gets 4 city.
	 *
	 * @return the 4 city
	 */
	List<TreeNode> get4City();
}
