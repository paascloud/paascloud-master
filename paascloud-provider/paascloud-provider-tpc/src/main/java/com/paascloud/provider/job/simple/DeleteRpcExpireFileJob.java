package com.paascloud.provider.job.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.paascloud.elastic.lite.annotation.ElasticJobConfig;
import com.paascloud.provider.service.OpcRpcService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;

/**
 * 定时清理所有生产者发送成功的消息数据.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@ElasticJobConfig(cron = "0 0 0 1/1 * ?")
public class DeleteRpcExpireFileJob implements SimpleJob {

	@Resource
	private OpcRpcService opcRpcService;

	/**
	 * Execute.
	 *
	 * @param shardingContext the sharding context
	 */
	@Override
	public void execute(final ShardingContext shardingContext) {
		opcRpcService.deleteExpireFile();
	}
}
