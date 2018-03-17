package com.paascloud.provider.model.service;


import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.base.dto.MqMessageVo;
import com.paascloud.provider.model.service.hystrix.UacMqMessageApiHystrix;
import com.paascloud.security.feign.OAuth2FeignAutoConfiguration;
import com.paascloud.wrapper.Wrapper;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The interface Uac user token feign api.
 *
 * @author paascloud.net @gmail.com
 */
@FeignClient(value = "paascloud-provider-uac", configuration = OAuth2FeignAutoConfiguration.class, fallback = UacMqMessageApiHystrix.class)
public interface UacMqMessageFeignApi {


	/**
	 * Query waiting confirm message list wrapper.
	 *
	 * @param messageKeyList the message key list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/uac/message/queryMessageKeyList")
	Wrapper<List<String>> queryMessageKeyList(@RequestParam("messageKeyList") List<String> messageKeyList);

	/**
	 * Query message list with page wrapper.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/api/uac/message/queryMessageListWithPage")
	Wrapper<PageInfo<MqMessageVo>> queryMessageListWithPage(@RequestBody MessageQueryDto messageQueryDto);
}
