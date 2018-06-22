/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：AliyunSmsConfiguration.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.paascloud.config.properties.PaascloudProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * The class Aliyun sms configuration.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Configuration
public class AliyunSmsConfiguration {

	@Resource
	private PaascloudProperties paascloudProperties;

	/**
	 * Acs client acs client.
	 *
	 * @return the acs client
	 *
	 * @throws ClientException the client exception
	 */
	@Bean
	public IAcsClient acsClient() throws ClientException {
		log.info("SMS Bean IAcsClient Start");
		IClientProfile profile = DefaultProfile.getProfile(paascloudProperties.getAliyun().getSms().getRegionId(), paascloudProperties.getAliyun().getKey().getAccessKeyId(), paascloudProperties.getAliyun().getKey().getAccessKeySecret());
		DefaultProfile.addEndpoint(paascloudProperties.getAliyun().getSms().getEndpointName(), paascloudProperties.getAliyun().getSms().getRegionId(), paascloudProperties.getAliyun().getSms().getProduct(), paascloudProperties.getAliyun().getSms().getDomain());
		DefaultAcsClient defaultAcsClient = new DefaultAcsClient(profile);
		log.info("加载SMS Bean IAcsClient OK");
		return defaultAcsClient;
	}

}
