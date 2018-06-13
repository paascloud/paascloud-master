package com.paascloud.provider.web.admin;

import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.domain.UacRole;
import com.paascloud.provider.model.dto.role.CheckRoleCodeDto;
import com.paascloud.provider.model.vo.BindAuthVo;
import com.paascloud.provider.service.UacRoleService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;

/**
 * 角色管理-公共方法.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/role", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacRoleCommonController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacRoleCommonController extends BaseController {

	@Resource
	private UacRoleService uacRoleService;

	/**
	 * 查看角色信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryRoleInfoById/{id}")
	@ApiOperation(httpMethod = "POST", value = "查看角色信息")
	public Wrapper<UacRole> queryRoleInfo(@PathVariable Long id) {
		UacRole role = uacRoleService.selectByKey(id);
		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, Wrapper.SUCCESS_MESSAGE, role);
	}

	/**
	 * 验证角色编码是否存在.
	 *
	 * @param checkRoleCodeDto the check role code dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkRoleCode")
	@ApiOperation(httpMethod = "POST", value = "验证角色编码是否存在")
	public Wrapper<Boolean> checkUacRoleCode(@ApiParam(name = "roleCode", value = "角色编码") @RequestBody CheckRoleCodeDto checkRoleCodeDto) {

		logger.info("校验角色编码唯一性 checkRoleCodeDto={}", checkRoleCodeDto);

		Long id = checkRoleCodeDto.getRoleId();
		String roleCode = checkRoleCodeDto.getRoleCode();

		Example example = new Example(UacRole.class);
		Example.Criteria criteria = example.createCriteria();

		if (id != null) {
			criteria.andNotEqualTo("id", id);
		}
		criteria.andEqualTo("roleCode", roleCode);

		int result = uacRoleService.selectCountByExample(example);
		return WrapMapper.ok(result < 1);
	}

	/**
	 * 获取权限树
	 *
	 * @param roleId the role id
	 *
	 * @return the auth tree by role id
	 */
	@PostMapping(value = "/getActionTreeByRoleId/{roleId}")
	@ApiOperation(httpMethod = "POST", value = "获取权限树")
	public Wrapper<BindAuthVo> getActionTreeByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable Long roleId) {
		logger.info("roleId={}", roleId);
		BindAuthVo bindAuthVo = uacRoleService.getActionTreeByRoleId(roleId);
		return WrapMapper.ok(bindAuthVo);
	}

	/**
	 * 获取菜单树.
	 *
	 * @param roleId the role id
	 *
	 * @return the menu tree by role id
	 */
	@PostMapping(value = "/getMenuTreeByRoleId/{roleId}")
	@ApiOperation(httpMethod = "POST", value = "获取菜单树")
	public Wrapper<BindAuthVo> getMenuTreeByRoleId(@ApiParam(name = "roleId", value = "角色id") @PathVariable Long roleId) {
		logger.info("roleId={}", roleId);
		BindAuthVo bindAuthVo = uacRoleService.getMenuTreeByRoleId(roleId);
		return WrapMapper.ok(bindAuthVo);
	}

}
