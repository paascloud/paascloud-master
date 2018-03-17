package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.provider.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * The class Redis service.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {
	@Resource
	private StringRedisTemplate rt;

	@Override
	public String getKey(String key) {
		String value = null;
		ValueOperations<String, String> ops = rt.opsForValue();
		if (rt.hasKey(key)) {
			value = ops.get(key);
		}
		log.info("getKey. [OK] key={}, value={}", key, value);
		return value;
	}

	@Override
	public void deleteKey(String key) {
		rt.delete(key);
		log.info("deleteKey. [OK] key={}", key);

	}

	@Override
	public void setKey(String key, String value) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");

		ValueOperations<String, String> ops = rt.opsForValue();
		ops.set(key, value);
		rt.expire(key, GlobalConstant.Sys.REDIS_DEFAULT_EXPIRE, TimeUnit.MINUTES);
		log.info("setKey. [OK] key={}, value={}, expire=默认超时时间", key, value);


	}

	@Override
	public void setKey(String key, String value, long timeout, TimeUnit unit) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Redis key is not null");
		Preconditions.checkArgument(unit != null, "TimeUnit is not null");
		ValueOperations<String, String> ops = rt.opsForValue();
		ops.set(key, value);
		rt.expire(key, timeout, unit);
		log.info("setKey. [OK] key={}, value={}, timeout={}, unit={}", key, value, timeout, unit);

	}
}
