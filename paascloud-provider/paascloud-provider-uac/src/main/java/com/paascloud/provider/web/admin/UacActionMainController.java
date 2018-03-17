package com.paascloud.provider.web.admin;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.annotation.LogAnnotation;
import com.paascloud.core.annotation.ValidateAnnotation;
import com.paascloud.core.support.BaseController;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.model.domain.UacAction;
import com.paascloud.provider.model.dto.action.ActionMainQueryDto;
import com.paascloud.provider.model.dto.base.ModifyStatusDto;
import com.paascloud.provider.service.UacActionService;
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
 * The class Uac action main controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/action", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - UacActionMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UacActionMainController extends BaseController {

	@Resource
	private UacActionService uacActionService;

	/**
	 * 分页查询角色信息.
	 *
	 * @param action the action
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryListWithPage")
	@ApiOperation(httpMethod = "POST", value = "查询角色列表")
	public Wrapper queryUacActionListWithPage(@ApiParam(name = "action", value = "角色信息") @RequestBody ActionMainQueryDto action) {

		logger.info("查询角色列表actionQuery={}", action);
		PageInfo pageInfo = uacActionService.queryActionListWithPage(action);
		return WrapMapper.ok(pageInfo);
	}

	/**
	 * 删除角色信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteActionById/{id}")
	@ApiOperation(httpMethod = "POST", value = "删除角色")
	@LogAnnotation
	public Wrapper deleteUacActionById(@ApiParam(name = "id", value = "角色id") @PathVariable Long id) {
		int result = uacActionService.deleteActionById(id);
		return super.handleResult(result);
	}

	/**
	 * 批量删除角色.
	 *
	 * @param deleteIdList the delete id list
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/batchDeleteByIdList")
	@ApiOperation(httpMethod = "POST", value = "批量删除角色")
	@LogAnnotation
	public Wrapper batchDeleteByIdList(@ApiParam(name = "deleteIdList", value = "角色Id") @RequestBody List<Long> deleteIdList) {
		logger.info("批量删除角色 idList={}", deleteIdList);
		uacActionService.batchDeleteByIdList(deleteIdList);
		return WrapMapper.ok();
	}


	/**
	 * 保存权限信息.
	 *
	 * @param action the action
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/save")
	@ApiOperation(httpMethod = "POST", value = "新增角色")
	@ValidateAnnotation
	@LogAnnotation
	public Wrapper save(@ApiParam(name = "action", value = "角色信息") @RequestBody UacAction action) {
		LoginAuthDto loginAuthDto = RequestUtil.getLoginUser();
		uacActionService.saveAction(action, loginAuthDto);
		return WrapMapper.ok();
	}

	/**
	 * 根据权限Id修改角色状态.
	 *
	 * @param modifyStatusDto the modify status dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/modifyStatus")
	@ApiOperation(httpMethod = "POST", value = "根据权限Id修改角色状态")
	@LogAnnotation
	public Wrapper modifyActionStatus(@ApiParam(name = "modifyActionStatus", value = "修改权限状态") @RequestBody ModifyStatusDto modifyStatusDto) {
		logger.info("根据角色Id修改权限状态 modifyStatusDto={}", modifyStatusDto);
		Long actionId = modifyStatusDto.getId();
		Preconditions.checkArgument(actionId != null, "权限ID不能为空");

		UacAction uacRole = new UacAction();
		uacRole.setId(actionId);
		uacRole.setStatus(modifyStatusDto.getStatus());
		uacRole.setUpdateInfo(getLoginAuthDto());

		int result = uacActionService.update(uacRole);
		return super.handleResult(result);
	}
}
