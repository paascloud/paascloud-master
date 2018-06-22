/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MqProducerChangeListener.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.core.registry.base.ReliableMessageRegisterDto;
import com.paascloud.core.registry.exception.RegExceptionHandler;
import com.paascloud.provider.service.MqProducerBeanFactory;
import com.paascloud.provider.service.TpcMqProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * The class Mq producer change listener.
 *
 * @author paascloud.net @gmail.com
 */
@Component
@Slf4j
public class MqProducerChangeListener implements TreeCacheListener {
	@Resource
	private TpcMqProducerService tpcMqProducerService;

	/**
	 * Child event.
	 *
	 * @param client the client
	 * @param event  the event
	 */
	@Override
	public void childEvent(final CuratorFramework client, final TreeCacheEvent event) {
		ChildData data = event.getData();
		if (data == null) {
			return;
		}
		String path = data.getPath();
		if (GlobalConstant.ZK_REGISTRY_PRODUCER_ROOT_PATH.equals(path) || GlobalConstant.ZK_REGISTRY_CONSUMER_ROOT_PATH.equals(path)) {
			return;
		}
		String[] split = path.split(GlobalConstant.Symbol.SLASH);
		String dataStr = new String(data.getData());
		switch (event.getType()) {
			case NODE_ADDED:
				log.info("MqProducerChangeListener CHILD_ADDED path={}, data={}", path, dataStr);
				if (split.length == GlobalConstant.Number.SIX_INT) {
					String appPath = path.substring(0, path.lastIndexOf(GlobalConstant.Symbol.SLASH));

					ReliableMessageRegisterDto dto = JSON.parseObject(getDirectly(client, appPath), ReliableMessageRegisterDto.class);
					if (this.getNumChildren(client, appPath) > 0) {
						tpcMqProducerService.updateOnLineStatusByPid(dto.getProducerGroup());
						MqProducerBeanFactory.buildProducerBean(dto);
						MqProducerBeanFactory.putPid(dto.getProducerGroup());
					}
				}
				break;
			case NODE_REMOVED:
				log.info("MqProducerChangeListener NODE_REMOVED path={}, data={}", path, dataStr);
				if (split.length == GlobalConstant.Number.SIX_INT) {
					String appPath = path.substring(0, path.lastIndexOf(GlobalConstant.Symbol.SLASH));

					ReliableMessageRegisterDto dto = JSON.parseObject(getDirectly(client, appPath), ReliableMessageRegisterDto.class);
					if (this.getNumChildren(client, appPath) < 1) {
						tpcMqProducerService.updateOffLineStatusByPid(dto.getProducerGroup());
						MqProducerBeanFactory.rmPid(dto.getProducerGroup());
					}
				}
				break;
			case NODE_UPDATED:
				log.error("本次版本不对更新做处理, path={}, data={}", path, new String(data.getData()));
				break;
			default:
				break;
		}
	}

	private int getNumChildren(CuratorFramework client, final String key) {
		log.info("获取生产者节点个数 path={}", key);
		Stat stat = null;
		try {
			stat = client.checkExists().forPath(key);
		} catch (final Exception ex) {
			log.error("获取目录 key={}的子节点个数出现异常={}", key, ex.getMessage(), ex);
		}
		return stat == null ? 0 : stat.getNumChildren();
	}

	private String getDirectly(CuratorFramework client, final String key) {
		try {
			return new String(client.getData().forPath(key), Charsets.UTF_8);
		} catch (final Exception ex) {
			RegExceptionHandler.handleException(ex);
			return null;
		}
	}
}
