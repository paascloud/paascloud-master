/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTagServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.TpcMqTagMapper;
import com.paascloud.provider.model.domain.TpcMqTag;
import com.paascloud.provider.model.vo.TpcMqTagVo;
import com.paascloud.provider.service.TpcMqConsumerService;
import com.paascloud.provider.service.TpcMqTagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Tpc mq tag service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqTagServiceImpl extends BaseService<TpcMqTag> implements TpcMqTagService {

	@Resource
	private TpcMqTagMapper mdcMqTagMapper;
	@Resource
	private TpcMqConsumerService mdcMqConsumerService;

	@Override
	public List<TpcMqTagVo> listWithPage(TpcMqTag mdcMqTag) {
		return mdcMqTagMapper.listTpcMqTagVoWithPage(mdcMqTag);
	}

	@Override
	public int deleteTagById(Long tagId) {
		// 删除订阅的tag
		mdcMqConsumerService.deleteSubscribeTagByTagId(tagId);
		// 删除tag
		return mdcMqTagMapper.deleteByPrimaryKey(tagId);
	}
}
