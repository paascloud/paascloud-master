package com.paascloud.provider.web.frontend;


import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.dto.MdcCategoryCheckNameDto;
import com.paascloud.provider.service.MdcProductCategoryService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
public class UacProductCategoryCommonController extends BaseController {

	@Resource
	private MdcProductCategoryService mdcProductCategoryService;

	/**
	 * 检测数据分类名称是否已存在.
	 *
	 * @param categoryCheckNameDto the category check name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkCategoryName")
	@ApiOperation(httpMethod = "POST", value = "检测数据分类名称是否已存在")
	public Wrapper<Boolean> checkCategoryName(@RequestBody MdcCategoryCheckNameDto categoryCheckNameDto) {
		logger.info("检测数据分类名称是否已存在 categoryCheckNameDto={}", categoryCheckNameDto);

		Long id = categoryCheckNameDto.getCategoryId();
		String categoryName = categoryCheckNameDto.getCategoryName();

		Example example = new Example(MdcProductCategory.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("name", categoryName);

		int result = mdcProductCategoryService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}
}
