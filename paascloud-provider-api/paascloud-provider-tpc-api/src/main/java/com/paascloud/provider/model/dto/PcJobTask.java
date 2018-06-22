/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PcJobTask.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


/**
 * The class Pc job task.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class PcJobTask implements Serializable {

	private static final long serialVersionUID = -1689940882253489536L;

	/**
	 * 自增ID
	 */
	private String id;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 关联单号
	 */
	private String refNo;

	/**
	 * Worker任务类型
	 */
	private String taskType;

	/**
	 * 业务json数据
	 */
	private String taskData;

	/**
	 * 执行次数
	 */
	private Integer taskExeCount;

	/**
	 * 执行实例IP
	 */
	private String exeInstanceIp;

	/**
	 * 任务运行状态
	 */
	private Integer taskStatus;

	/**
	 * 前置状态
	 */
	private List<Integer> preStatusList;
}