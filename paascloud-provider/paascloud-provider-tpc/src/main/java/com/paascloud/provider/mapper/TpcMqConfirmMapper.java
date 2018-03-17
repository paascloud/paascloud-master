package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.TpcMqConfirm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Tpc mq confirm mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Component
@Mapper
public interface TpcMqConfirmMapper extends MyMapper<TpcMqConfirm> {
	/**
	 * Confirm receive message.
	 *
	 * @param confirmId the confirm id
	 */
	void confirmReceiveMessage(@Param("cid") Long confirmId);

	/**
	 * Confirm consumed message.
	 *
	 * @param confirmId the confirm id
	 */
	void confirmConsumedMessage(@Param("cid") Long confirmId);

	/**
	 * Gets id mq confirm.
	 *
	 * @param cid        the cid
	 * @param messageKey the message key
	 *
	 * @return the id mq confirm
	 */
	Long getIdMqConfirm(@Param("cid") String cid, @Param("messageKey") String messageKey);

	/**
	 * Batch create mq confirm int.
	 *
	 * @param list the list
	 *
	 * @return the int
	 */
	int batchCreateMqConfirm(@Param("tpcMqConfirmList") List<TpcMqConfirm> list);

	/**
	 * 查询没有消费的数量.
	 *
	 * @param messageKey the message key
	 *
	 * @return the int
	 */
	int selectUnConsumedCount(@Param("messageKey") String messageKey);
}