package com.paascloud.provider.service.impl;

import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.TpcMqTopicMapper;
import com.paascloud.provider.model.domain.TpcMqTopic;
import com.paascloud.provider.model.vo.TpcMqTopicVo;
import com.paascloud.provider.service.TpcMqTopicService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Tpc mq topic service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TpcMqTopicServiceImpl extends BaseService<TpcMqTopic> implements TpcMqTopicService {
	@Resource
	private TpcMqTopicMapper mdcMqTopicMapper;

	@Override
	public List<TpcMqTopicVo> listWithPage(TpcMqTopic mdcMqTopic) {
		return mdcMqTopicMapper.listTpcMqTopicVoWithPage(mdcMqTopic);
	}
}
