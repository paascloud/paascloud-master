/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcExceptionLogMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.MdcExceptionLog;
import com.paascloud.provider.model.dto.MdcExceptionQueryDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Mdc exception log mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface MdcExceptionLogMapper extends MyMapper<MdcExceptionLog> {
	/**
	 * Query exception list with page list.
	 *
	 * @param mdcExceptionQueryDto the mdc exception query dto
	 *
	 * @return the list
	 */
	List<MdcExceptionLog> queryExceptionListWithPage(MdcExceptionQueryDto mdcExceptionQueryDto);
}