package com.paascloud.provider.web.rpc;

import com.paascloud.provider.service.OmcOrderDetailFeignApi;
import com.paascloud.core.support.BaseController;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * The class Omc order detail feign client.
 *
 * @author paascloud.net@gmail.com
 */
@RefreshScope
@RestController
@Api(value = "API - OmcOrderDetailFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OmcOrderDetailFeignClient extends BaseController implements OmcOrderDetailFeignApi {

}
