package com.paascloud.provider.service.impl;

import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.UacRoleActionMapper;
import com.paascloud.provider.model.domain.UacRoleAction;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.service.UacRoleActionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * The class Uac role action service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleActionServiceImpl extends BaseService<UacRoleAction> implements UacRoleActionService {
	@Resource
	private UacRoleActionMapper uacRoleActionMapper;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleAction> listByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleAction roleMenu = new UacRoleAction();
		roleMenu.setRoleId(roleId);
		return uacRoleActionMapper.select(roleMenu);
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleAction roleMenu = new UacRoleAction();
		roleMenu.setRoleId(roleId);
		uacRoleActionMapper.delete(roleMenu);
	}

	@Override
	public void insert(Long roleId, Set<Long> actionIdList) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		UacRoleAction uacRoleAction = new UacRoleAction();
		uacRoleAction.setRoleId(roleId);
		for (Long actionId : actionIdList) {
			uacRoleAction.setActionId(actionId);
			uacRoleActionMapper.insert(uacRoleAction);
		}
	}

	@Override
	public int deleteByRoleIdList(final List<Long> roleIdList) {
		return uacRoleActionMapper.deleteByRoleIdList(roleIdList);
	}
}
