/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqConfirmMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

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