/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacUserTokenMapper.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacUserToken;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * The interface Uac user token mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface UacUserTokenMapper extends MyMapper<UacUserToken> {
	/**
	 * Select token list list.
	 *
	 * @param userToken the user token
	 *
	 * @return the list
	 */
	List<UacUserToken> selectTokenList(UacUserToken userToken);

	/**
	 * 超时token更新为离线.
	 *
	 * @param map the map
	 *
	 * @return the int
	 */
	int batchUpdateTokenOffLine(Map<String, Object> map);

	/**
	 * 查询超时token Id 集合.
	 *
	 * @return the list
	 */
	List<Long> listOffLineTokenId();
}