package com.paascloud.provider.web.frontend;


import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.MdcDict;
import com.paascloud.provider.model.dto.MdcDictCheckCodeDto;
import com.paascloud.provider.model.dto.MdcDictCheckNameDto;
import com.paascloud.provider.service.MdcDictService;
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
 * The class Uac dict common controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/dict", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - UacDictCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacDictCommonController extends BaseController {


	@Resource
	private MdcDictService mdcDictService;

	/**
	 * 检测菜单编码是否已存在
	 *
	 * @param mdcDictCheckCodeDto the mdc dict check code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkDictCode")
	@ApiOperation(httpMethod = "POST", value = "检测数据字典编码是否已存在")
	public Wrapper<Boolean> checkDictCode(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckCodeDto mdcDictCheckCodeDto) {
		logger.info("检测数据字典编码是否已存在 mdcDictCheckCodeDto={}", mdcDictCheckCodeDto);

		Long id = mdcDictCheckCodeDto.getDictId();
		String dictCode = mdcDictCheckCodeDto.getDictCode();

		Example example = new Example(MdcDict.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("dictCode", dictCode);

		int result = mdcDictService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}

	/**
	 * 检测数据字典名称是否已存在.
	 *
	 * @param mdcDictCheckNameDto the mdc dict check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkDictName")
	@ApiOperation(httpMethod = "POST", value = "检测数据字典名称是否已存在")
	public Wrapper<Boolean> checkDictName(@ApiParam(name = "uacMenuCheckCodeDto", value = "id与url") @RequestBody MdcDictCheckNameDto mdcDictCheckNameDto) {
		logger.info("检测数据字典名称是否已存在 mdcDictCheckNameDto={}", mdcDictCheckNameDto);

		Long id = mdcDictCheckNameDto.getDictId();
		String dictName = mdcDictCheckNameDto.getDictName();

		Example example = new Example(MdcDict.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("dictName", dictName);

		int result = mdcDictService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}
}
