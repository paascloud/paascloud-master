/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenuCommonController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.admin;


import com.paascloud.provider.model.domain.UacMenu;
import com.paascloud.provider.model.dto.menu.UacMenuCheckCodeDto;
import com.paascloud.provider.model.dto.menu.UacMenuCheckNameDto;
import com.paascloud.provider.model.dto.menu.UacMenuCheckUrlDto;
import com.paascloud.provider.service.UacMenuService;
import com.paascloud.core.support.BaseController;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * 菜单.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/menu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacMenuCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacMenuCommonController extends BaseController {


	@Resource
	private UacMenuService uacMenuService;

	/**
	 * 检测菜单编码是否已存在
	 *
	 * @param uacMenuCheckCodeDto the uac menu check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuCode")
	@ApiOperation(httpMethod = "POST", value = "检测菜单编码是否已存在")
	public Wrapper<Boolean> checkUacMenuActionCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody UacMenuCheckCodeDto uacMenuCheckCodeDto) {
		logger.info("校验菜单编码唯一性 uacMenuCheckCodeDto={}", uacMenuCheckCodeDto);

		Long id = uacMenuCheckCodeDto.getMenuId();
		String menuCode = uacMenuCheckCodeDto.getMenuCode();

		Example example = new Example(UacMenu.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("menuCode", menuCode);

		int result = uacMenuService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}

	/**
	 * 检测菜单名称唯一性
	 *
	 * @param uacMenuCheckNameDto the uac menu check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuName")
	@ApiOperation(httpMethod = "POST", value = "检测菜单名称唯一性")
	public Wrapper<Boolean> checkUacMenuName(@ApiParam(name = "uacMenuCheckNameDto", value = "id与name") @RequestBody UacMenuCheckNameDto uacMenuCheckNameDto) {
		logger.info("校验菜单名称唯一性 uacMenuCheckNameDto={}", uacMenuCheckNameDto);
		Long id = uacMenuCheckNameDto.getMenuId();
		Long pid = uacMenuCheckNameDto.getPid();
		String menuName = uacMenuCheckNameDto.getMenuName();

		Example example = new Example(UacMenu.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("menuName", menuName);
		criteria.andEqualTo("pid", pid);

		int result = uacMenuService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}


	/**
	 * 检测菜单URL唯一性
	 *
	 * @param uacMenuCheckUrlDto the uac menu check url dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkMenuUrl")
	@ApiOperation(httpMethod = "POST", value = "检测菜单URL唯一性")
	public Wrapper<Boolean> checkUacMenuUrl(@ApiParam(name = "uacMenuCheckUrlDto", value = "id与url") @RequestBody UacMenuCheckUrlDto uacMenuCheckUrlDto) {
		logger.info("检测菜单URL唯一性 uacMenuCheckUrlDto={}", uacMenuCheckUrlDto);

		Long id = uacMenuCheckUrlDto.getMenuId();
		String url = uacMenuCheckUrlDto.getUrl();

		Example example = new Example(UacMenu.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("url", url);

		int result = uacMenuService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}
}
