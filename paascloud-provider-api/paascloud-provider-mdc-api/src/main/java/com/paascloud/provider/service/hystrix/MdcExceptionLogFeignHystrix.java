package com.paascloud.provider.service.hystrix;

import com.paascloud.provider.model.dto.GlobalExceptionLogDto;
import com.paascloud.provider.service.MdcExceptionLogFeignApi;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;


/**
 * The class Mdc exception log feign hystrix.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class MdcExceptionLogFeignHystrix implements MdcExceptionLogFeignApi {

	@Override
	public Wrapper saveAndSendExceptionLog(final GlobalExceptionLogDto exceptionLogDto) {
		return null;
	}
}
