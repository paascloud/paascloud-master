/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptFreeMarkerService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;


/**
 * The interface Opt free marker service.
 *
 * @author paascloud.net@gmail.com
 */
public interface OptFreeMarkerService {

	/**
	 * Gets template.
	 *
	 * @param map              the map
	 * @param templateLocation the template location
	 *
	 * @return the template
	 *
	 * @throws IOException       the io exception
	 * @throws TemplateException the template exception
	 */
	String getTemplate(Map<String, Object> map, String templateLocation) throws IOException, TemplateException;
}
