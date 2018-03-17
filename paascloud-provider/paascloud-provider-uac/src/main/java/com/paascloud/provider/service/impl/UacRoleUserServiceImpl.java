package com.paascloud.provider.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.UacRoleUserMapper;
import com.paascloud.provider.model.domain.UacRoleUser;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.service.UacRoleUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * The class Uac role user service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleUserServiceImpl extends BaseService<UacRoleUser> implements UacRoleUserService {
	@Resource
	private UacRoleUserMapper uacRoleUserMapper;

	@Override
	public int deleteByUserId(Long userId) {
		if (null == userId) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		UacRoleUser param = new UacRoleUser();
		param.setUserId(userId);
		return uacRoleUserMapper.delete(param);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleUser> queryByUserId(Long userId) {
		if (null == userId) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		return uacRoleUserMapper.listByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacRoleUser getByUserIdAndRoleId(Long userId, Long roleId) {

		if (null == userId) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		if (null == roleId) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		return uacRoleUserMapper.getByUserIdAndRoleId(userId, roleId);
	}

	@Override
	public int saveRoleUser(Long userId, Long roleId) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		UacRoleUser roleUser = new UacRoleUser();
		roleUser.setUserId(userId);
		roleUser.setRoleId(roleId);
		return uacRoleUserMapper.insertSelective(roleUser);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleUser> listByRoleId(Long roleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		return uacRoleUserMapper.listByRoleId(roleId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleUser> listByRoleIdList(List<Long> idList) {
		if (PublicUtil.isEmpty(idList)) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		return uacRoleUserMapper.listByRoleIdList(idList);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Long> listSuperUser(Long superManagerRoleId) {
		if (superManagerRoleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		return uacRoleUserMapper.listSuperUser(superManagerRoleId);
	}

	@Override
	public void deleteExcludeSuperMng(Long roleId, Long superManagerRoleId) {
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}
		if (superManagerRoleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012004);
		}
		uacRoleUserMapper.deleteExcludeSuperMng(roleId, superManagerRoleId);

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRoleUser> listByUserId(Long userId) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		return uacRoleUserMapper.listByUserId(userId);
	}

	@Override
	public void deleteByRoleIdList(List<Long> roleIdList) {
		Preconditions.checkArgument(PublicUtil.isNotEmpty(roleIdList), ErrorCodeEnum.UAC10012001.msg());
		Preconditions.checkArgument(!roleIdList.contains(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID), "超级管理员角色不能删除");
		int result = uacRoleUserMapper.deleteByRoleIdList(roleIdList);
		if (result < roleIdList.size()) {
			throw new UacBizException(ErrorCodeEnum.UAC10012007, Joiner.on(GlobalConstant.Symbol.COMMA).join(roleIdList));
		}
	}

	@Override
	public void deleteByRoleId(Long roleId) {
		Preconditions.checkArgument(roleId != null, ErrorCodeEnum.UAC10012001.msg());
		Preconditions.checkArgument(!Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID), "超级管理员角色不能删除");

		int result = uacRoleUserMapper.deleteByRoleId(roleId);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10012006, roleId);
		}
	}
}
