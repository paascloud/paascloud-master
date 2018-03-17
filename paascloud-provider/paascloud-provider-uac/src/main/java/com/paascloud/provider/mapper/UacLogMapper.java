package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacLog;
import com.paascloud.provider.model.dto.log.UacLogMainDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Uac log mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface UacLogMapper extends MyMapper<UacLog> {
	/**
	 * Select user log list with user id list.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacLog> selectUserLogListByUserId(@Param("userId") Long userId);

	/**
	 * Query log list with page list.
	 *
	 * @param uacLogQueryDtoPage the uac log query dto page
	 *
	 * @return the list
	 */
	List<UacLog> queryLogListWithPage(UacLogMainDto uacLogQueryDtoPage);
}