package com.paascloud.provider.service;


import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.annotation.OperationLogDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.UacLog;
import com.paascloud.provider.model.dto.log.UacLogMainDto;

import java.util.List;

/**
 * The interface Uac log service.
 *
 * @author paascloud.net @gmail.com
 */
public interface UacLogService extends IService<UacLog> {

	/**
	 * Save log int.
	 *
	 * @param uacLog       the uac log
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int saveLog(UacLog uacLog, LoginAuthDto loginAuthDto);

	/**
	 * Query user log list with user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacLog> selectUserLogListByUserId(Long userId);

	/**
	 * Save operation log integer.
	 *
	 * @param operationLogDto the operation log dto
	 *
	 * @return the integer
	 */
	Integer saveOperationLog(OperationLogDto operationLogDto);

	/**
	 * Query log list with page wrapper.
	 *
	 * @param uacLogQueryDtoPage the uac log query dto page
	 *
	 * @return the wrapper
	 */
	PageInfo queryLogListWithPage(UacLogMainDto uacLogQueryDtoPage);
}
