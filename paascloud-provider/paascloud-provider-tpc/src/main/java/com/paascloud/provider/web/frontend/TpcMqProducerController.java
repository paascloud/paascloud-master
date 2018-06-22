/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqProducerController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.TpcMqProducer;
import com.paascloud.provider.model.vo.TpcMqProducerVo;
import com.paascloud.provider.model.vo.TpcMqPublishVo;
import com.paascloud.provider.service.TpcMqProducerService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


/**
 * 生产者管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/producer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqProducerController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqProducerController extends BaseController {

	@Resource
	private TpcMqProducerService tpcMqProducerService;

	/**
	 * 查询生产者列表.
	 *
	 * @param tpcMqProducer the tpc mq producer
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryProducerVoListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询生产者列表")
	public Wrapper<List<TpcMqProducerVo>> queryProducerList(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducer tpcMqProducer) {

		logger.info("查询生产者列表tpcMqTopicQuery={}", tpcMqProducer);
		List<TpcMqProducerVo> list = tpcMqProducerService.listProducerVoWithPage(tpcMqProducer);
		return WrapMapper.ok(list);
	}

	/**
	 * 查询发布者列表.
	 *
	 * @param tpcMqProducer the tpc mq producer
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryPublishListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询发布者列表")
	public Wrapper<PageInfo<TpcMqPublishVo>> queryPublishListWithPage(@ApiParam(name = "producer", value = "Mq生产者") @RequestBody TpcMqProducer tpcMqProducer) {
		logger.info("查询Mq发布列表tpcMqTopicQuery={}", tpcMqProducer);
		PageHelper.startPage(tpcMqProducer.getPageNum(), tpcMqProducer.getPageSize());
		tpcMqProducer.setOrderBy("update_time desc");
		List<TpcMqPublishVo> list = tpcMqProducerService.listPublishVoWithPage(tpcMqProducer);
		return WrapMapper.ok(new PageInfo<>(list));
	}

	/**
	 * 修改生产者状态.
	 *
	 * @param updateStatusDto the update status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatusById")
	@ApiOperation(httpMethod = "POST", value = "修改生产者状态")
	@LogAnnotation
	public Wrapper modifyProducerStatusById(@ApiParam(value = "修改producer状态") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("修改producer状态 updateStatusDto={}", updateStatusDto);
		Long roleId = updateStatusDto.getId();

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		TpcMqProducer producer = new TpcMqProducer();
		producer.setId(roleId);
		producer.setStatus(updateStatusDto.getStatus());
		producer.setUpdateInfo(loginAuthDto);

		int result = tpcMqProducerService.update(producer);
		return super.handleResult(result);
	}

	/**
	 * 根据生产者ID删除生产者.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据生产者ID删除生产者")
	@LogAnnotation
	public Wrapper deleteProducerById(@PathVariable Long id) {
		logger.info("删除producer id={}", id);
		int result = tpcMqProducerService.deleteProducerById(id);
		return super.handleResult(result);
	}
}
