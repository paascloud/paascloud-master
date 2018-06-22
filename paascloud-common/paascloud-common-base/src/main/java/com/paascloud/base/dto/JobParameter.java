/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：JobParameter.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */
package com.paascloud.base.dto;

import com.google.common.base.Preconditions;

import java.io.Serializable;


/**
 * The class Job task model.
 *
 * @author paascloud.net @gmail.com
 */
public class JobParameter implements Serializable {
	private static final long serialVersionUID = -610797345091216847L;

	/**
	 * 每次查询数据量
	 */
	private int fetchNum;
	/**
	 * 取模条件
	 */
	private String condition;
	/**
	 * 取模条件
	 */
	private String taskType;

	public int getFetchNum() {
		Preconditions.checkArgument(fetchNum != 0);
		return fetchNum;
	}

	public void setFetchNum(int fetchNum) {
		this.fetchNum = fetchNum;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("JobTaskParameter{");
		sb.append("fetchNum=").append(fetchNum);
		sb.append(", condition='").append(condition).append('\'');
		sb.append(", taskType='").append(taskType).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
