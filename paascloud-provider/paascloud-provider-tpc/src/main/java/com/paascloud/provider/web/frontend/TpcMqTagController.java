/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：TpcMqTagController.java
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
import com.paascloud.provider.model.domain.TpcMqTag;
import com.paascloud.provider.model.vo.TpcMqTagVo;
import com.paascloud.provider.service.TpcMqTagService;
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
 * The class Tpc mq tag controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/tag", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TpcMqTagController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMqTagController extends BaseController {

	@Resource
	private TpcMqTagService tpcMqTagService;

	/**
	 * 查询MQ Tag列表.
	 *
	 * @param tpcMqTag the tpc mq tag
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryTagListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询MQ-Tag列表")
	public Wrapper<PageInfo<TpcMqTagVo>> queryTagListWithPage(@ApiParam(name = "tag", value = "角色信息") @RequestBody TpcMqTag tpcMqTag) {

		logger.info("查询角色列表tpcMqTagQuery={}", tpcMqTag);
		PageHelper.startPage(tpcMqTag.getPageNum(), tpcMqTag.getPageSize());
		tpcMqTag.setOrderBy("update_time desc");
		List<TpcMqTagVo> list = tpcMqTagService.listWithPage(tpcMqTag);
		return WrapMapper.ok(new PageInfo<>(list));
	}

	/**
	 * 修改tag状态.
	 *
	 * @param updateStatusDto the update status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatusById")
	@ApiOperation(httpMethod = "POST", value = "修改MQ-Tag状态")
	@LogAnnotation
	public Wrapper modifyProducerStatusById(@ApiParam(value = "修改tag状态") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("修改tag状态 updateStatusDto={}", updateStatusDto);
		Long roleId = updateStatusDto.getId();

		LoginAuthDto loginAuthDto = getLoginAuthDto();

		TpcMqTag tag = new TpcMqTag();
		tag.setId(roleId);
		tag.setStatus(updateStatusDto.getStatus());
		tag.setUpdateInfo(loginAuthDto);

		int result = tpcMqTagService.update(tag);
		return super.handleResult(result);
	}

	/**
	 * 根据Tag ID删除TAG.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据ID删除TAG")
	@LogAnnotation
	public Wrapper deleteTagById(@ApiParam(value = "Tag ID") @PathVariable Long id) {
		logger.info("删除tag id={}", id);
		int result = tpcMqTagService.deleteTagById(id);
		return super.handleResult(result);
	}
}
