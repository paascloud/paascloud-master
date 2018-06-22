/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqMessageDataMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.base.dto.MqMessageVo;
import com.paascloud.provider.model.domain.MqMessageData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Mq message data mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Component
@Mapper
public interface MqMessageDataMapper extends tk.mybatis.mapper.common.Mapper<MqMessageData> {
	/**
	 * 获取7天前消息总数.
	 *
	 * @param shardingTotalCount the sharding total count
	 * @param shardingItem       the sharding item
	 * @param messageType        the message type
	 *
	 * @return the delete total count
	 */
	int getBefore7DayTotalCount(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem, @Param("messageType") int messageType);

	/**
	 * Gets id list before 7 day.
	 *
	 * @param shardingTotalCount the sharding total count
	 * @param shardingItem       the sharding item
	 * @param messageType        the message type
	 * @param currentPage        the current page
	 * @param pageSize           the page size
	 *
	 * @return the id list before 7 day
	 */
	List<Long> getIdListBefore7Day(@Param("shardingTotalCount") int shardingTotalCount, @Param("shardingItem") int shardingItem, @Param("messageType") int messageType, @Param("currentPage") int currentPage, @Param("pageSize") int pageSize);

	/**
	 * Batch delete by id list.
	 *
	 * @param idList the id list
	 *
	 * @return the int
	 */
	int batchDeleteByIdList(@Param("idList") List<Long> idList);

	/**
	 * Query message key list list.
	 *
	 * @param messageKeyList the message key list
	 *
	 * @return the list
	 */
	List<String> queryMessageKeyList(@Param("messageKeyList") List<String> messageKeyList);

	/**
	 * Query message list with page list.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the list
	 */
	List<MqMessageVo> queryMessageListWithPage(MessageQueryDto messageQueryDto);
}