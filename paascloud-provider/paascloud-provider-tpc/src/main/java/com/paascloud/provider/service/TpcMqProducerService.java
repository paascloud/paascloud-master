/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.TpcMqProducer;
import com.paascloud.provider.model.vo.TpcMqProducerVo;
import com.paascloud.provider.model.vo.TpcMqPublishVo;

import java.util.List;

/**
 * The interface Tpc mq producer service.
 *
 * @author paascloud.net @gmail.com
 */
public interface TpcMqProducerService extends IService<TpcMqProducer> {
	/**
	 * List producer vo with page list.
	 *
	 * @param mdcMqProducer the mdc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqProducerVo> listProducerVoWithPage(TpcMqProducer mdcMqProducer);

	/**
	 * 查询发布者列表.
	 *
	 * @param mdcMqProducer the mdc mq producer
	 *
	 * @return the list
	 */
	List<TpcMqPublishVo> listPublishVoWithPage(TpcMqProducer mdcMqProducer);

	/**
	 * 根据生产者ID删除生产者.
	 *
	 * @param id the id
	 *
	 * @return the int
	 */
	int deleteProducerById(Long id);

	/**
	 * 根据pid更新生产者状态为在线.
	 *
	 * @param producerGroup the producer group
	 */
	void updateOnLineStatusByPid(String producerGroup);

	/**
	 * 根据pid更新生产者状态为离线.
	 *
	 * @param producerGroup the producer group
	 */
	void updateOffLineStatusByPid(String producerGroup);
}
