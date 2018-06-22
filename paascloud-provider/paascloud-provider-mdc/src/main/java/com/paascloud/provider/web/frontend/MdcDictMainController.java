/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcDictMainController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.MdcDict;
import com.paascloud.provider.model.dto.MdcEditDictDto;
import com.paascloud.provider.model.vo.MdcDictVo;
import com.paascloud.provider.service.MdcDictService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Mdc dict main controller.
 *
 * @author paascloud.net@gmail.com
 */
@RestController
@RequestMapping(value = "/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - MdcDictMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MdcDictMainController extends BaseController {

	@Resource
	private MdcDictService mdcDictService;

	/**
	 * 获取字典列表数据
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/getTree")
	@ApiOperation(httpMethod = "POST", value = "获取字典树")
	public Wrapper<List<MdcDictVo>> queryDictTreeList() {
		List<MdcDictVo> dictVoList = mdcDictService.getDictTreeList();
		return WrapMapper.ok(dictVoList);
	}

	/**
	 * 根据ID获取字典信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据ID获取字典信息")
	public Wrapper<MdcDictVo> queryDictVoById(@ApiParam(name = "id", value = "字典id") @PathVariable Long id) {
		logger.info("根据Id查询字典信息, dictId={}", id);
		MdcDictVo mdcDictVo = mdcDictService.getMdcDictVoById(id);
		return WrapMapper.ok(mdcDictVo);
	}


	/**
	 * 根据id修改字典的禁用状态
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "根据id修改字典的禁用状态")
	public Wrapper updateMdcDictStatusById(@ApiParam(name = "mdcDictStatusDto", value = "修改字典状态Dto") @RequestBody UpdateStatusDto updateStatusDto) {
		logger.info("根据id修改字典的禁用状态 updateStatusDto={}", updateStatusDto);
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		mdcDictService.updateMdcDictStatusById(updateStatusDto, loginAuthDto);
		return WrapMapper.ok();
	}

	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "编辑字典")
	public Wrapper saveDict(@ApiParam(name = "saveDict", value = "编辑字典") @RequestBody MdcEditDictDto mdcDictAddDto) {
		MdcDict mdcDict = new MdcDict();
		LoginAuthDto loginAuthDto = getLoginAuthDto();
		BeanUtils.copyProperties(mdcDictAddDto, mdcDict);
		mdcDictService.saveMdcDict(mdcDict, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 根据id删除字典
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据id删除字典")
	public Wrapper<Integer> deleteMdcDictById(@ApiParam(name = "id", value = "字典id") @PathVariable Long id) {
		logger.info(" 根据id删除字典 id={}", id);
		// 判断此字典是否有子节点
		boolean hasChild = mdcDictService.checkDictHasChildDict(id);
		if (hasChild) {
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "此字典含有子字典, 请先删除子字典");
		}

		int result = mdcDictService.deleteByKey(id);
		return super.handleResult(result);
	}
}