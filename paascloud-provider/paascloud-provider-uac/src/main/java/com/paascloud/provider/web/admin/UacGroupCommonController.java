package com.paascloud.provider.web.admin;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.UacGroup;
import com.paascloud.provider.model.dto.group.CheckGroupCodeDto;
import com.paascloud.provider.model.dto.group.CheckGroupNameDto;
import com.paascloud.provider.model.enums.UacGroupTypeEnum;
import com.paascloud.provider.model.vo.GroupZtreeVo;
import com.paascloud.provider.service.UacGroupService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 组织相关公用接口
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/group", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacGroupCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacGroupCommonController extends BaseController {

	@Resource
	private UacGroupService uacGroupService;

	/**
	 * 根据当前登录人查询组织列表
	 *
	 * @return the group tree by id
	 */
	@PostMapping(value = "/getGroupTree")
	@ApiOperation(httpMethod = "POST", value = "根据当前登录人查询组织列表")
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById() {

		logger.info("根据当前登录人查询组织列表");
		LoginAuthDto loginAuthDto = super.getLoginAuthDto();
		Long groupId = loginAuthDto.getGroupId();
		UacGroup uacGroup = uacGroupService.queryById(groupId);
		List<GroupZtreeVo> tree = uacGroupService.getGroupTree(uacGroup.getId());
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", tree);
	}

	/**
	 * 通过组织ID查询组织树
	 *
	 * @param groupId the group id
	 *
	 * @return the group tree by id
	 */
	@PostMapping(value = "/getGroupTree/{groupId}")
	@ApiOperation(httpMethod = "POST", value = "通过组织ID查询组织列表")
	public Wrapper<List<GroupZtreeVo>> getGroupTreeById(@ApiParam(name = "groupId", value = "通过组织ID查询组织列表") @PathVariable Long groupId) {

		logger.info("通过组织ID查询组织列表 groupId={}", groupId);

		List<GroupZtreeVo> tree = uacGroupService.getGroupTree(groupId);
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, "操作成功", tree);
	}

	/**
	 * Check group name with edit wrapper.
	 *
	 * @param checkGroupNameDto the check group name dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkGroupName")
	@ApiOperation(httpMethod = "POST", value = "编辑校验组织名唯一性")
	public Wrapper<Boolean> checkGroupName(@ApiParam(name = "checkGroupName", value = "组织名称") @RequestBody CheckGroupNameDto checkGroupNameDto) {
		logger.info("校验组织名称唯一性 checkGroupNameDto={}", checkGroupNameDto);

		Long id = checkGroupNameDto.getGroupId();
		String groupName = checkGroupNameDto.getGroupName();

		Example example = new Example(UacGroup.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("groupName", groupName);

		int result = uacGroupService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}

	/**
	 * 修改时验证组织编码
	 *
	 * @param checkGroupCodeDto the check group code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkGroupCode")
	@ApiOperation(httpMethod = "POST", value = "修改校验组织编码唯一性")
	public Wrapper<Boolean> checkGroupCode(@ApiParam(name = "checkGroupCode", value = "组织相关信息") @RequestBody CheckGroupCodeDto checkGroupCodeDto) {
		logger.info("校验组织编码唯一性 checkGroupCodeDto={}", checkGroupCodeDto);

		Long id = checkGroupCodeDto.getGroupId();
		String groupCode = checkGroupCodeDto.getGroupCode();

		Example example = new Example(UacGroup.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("groupCode", groupCode);

		int result = uacGroupService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}

	/**
	 * 查询组织类型
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "queryGroupType")
	@ApiOperation(httpMethod = "POST", value = "查询组织类型")
	public Wrapper<List<Map<String, String>>> queryGroupType() {
		List<Map<String, String>> groupTypeList = UacGroupTypeEnum.getMap2List();
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, groupTypeList);
	}

}
