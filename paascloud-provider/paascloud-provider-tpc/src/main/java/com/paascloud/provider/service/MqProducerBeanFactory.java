package com.paascloud.provider.service;

import com.google.common.base.Preconditions;
import com.paascloud.core.registry.base.ReliableMessageRegisterDto;
import com.paascloud.core.support.SpringContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * The class Mq producer bean factory.
 *
 * @author paascloud.net @gmail.com
 */
public class MqProducerBeanFactory {

	private MqProducerBeanFactory() {
	}

	private static final ConcurrentHashMap<String, DefaultMQProducer> DEFAULT_MQ_PRODUCER_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, String> CONSUMER_STATUS_MAP = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<String, String> PRODUCER_STATUS_MAP = new ConcurrentHashMap<>();

	/**
	 * Gets bean.
	 *
	 * @param pid the pid
	 *
	 * @return the bean
	 */
	public static DefaultMQProducer getBean(String pid) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(pid), "getBean() pid is null");
		return DEFAULT_MQ_PRODUCER_MAP.get(pid);
	}

	/**
	 * Build producer bean.
	 *
	 * @param producerDto the producer dto
	 */
	public static void buildProducerBean(ReliableMessageRegisterDto producerDto) {

		String pid = producerDto.getProducerGroup();
		DefaultMQProducer mQProducer = DEFAULT_MQ_PRODUCER_MAP.get(pid);
		if (mQProducer == null) {
			String simpleName = producerDto.getProducerGroup();
			BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(DefaultMQProducer.class);
			beanDefinitionBuilder.setScope(BeanDefinition.SCOPE_SINGLETON);
			beanDefinitionBuilder.addPropertyValue("producerGroup", producerDto.getProducerGroup());
			beanDefinitionBuilder.addPropertyValue("namesrvAddr", producerDto.getNamesrvAddr());
			beanDefinitionBuilder.setInitMethodName("start");
			beanDefinitionBuilder.setDestroyMethodName("shutdown");
			SpringContextHolder.getDefaultListableBeanFactory().registerBeanDefinition(simpleName, beanDefinitionBuilder.getBeanDefinition());
			DEFAULT_MQ_PRODUCER_MAP.put(simpleName, SpringContextHolder.getBean(simpleName));
		}
	}

	public static void putCid(String cid) {
		CONSUMER_STATUS_MAP.put(cid, cid);
	}

	public static void rmCid(String cid) {
		CONSUMER_STATUS_MAP.remove(cid);
	}

	public static void putPid(final String pid) {
		PRODUCER_STATUS_MAP.put(pid, pid);
	}

	public static void rmPid(String pid) {
		PRODUCER_STATUS_MAP.remove(pid);
	}
}
