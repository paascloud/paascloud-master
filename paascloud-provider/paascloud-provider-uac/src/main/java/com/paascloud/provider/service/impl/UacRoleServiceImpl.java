package com.paascloud.provider.service.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.paascloud.Collections3;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.UacRoleMapper;
import com.paascloud.provider.mapper.UacRoleMenuMapper;
import com.paascloud.provider.model.domain.*;
import com.paascloud.provider.model.dto.role.*;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.vo.BindAuthVo;
import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.model.vo.RoleVo;
import com.paascloud.provider.model.vo.role.MenuCountVo;
import com.paascloud.provider.service.*;
import com.paascloud.provider.utils.TreeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * The class Uac role service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacRoleServiceImpl extends BaseService<UacRole> implements UacRoleService {
	@Resource
	private UacRoleMapper uacRoleMapper;
	@Resource
	private UacRoleUserService uacRoleUserService;
	@Resource
	private UacRoleMenuMapper uacRoleMenuMapper;
	@Resource
	private UacUserService uacUserService;
	@Resource
	private UacRoleMenuService uacRoleMenuService;
	@Resource
	private UacMenuService uacMenuService;
	@Resource
	private UacActionService uacActionService;
	@Resource
	private UacRoleActionService uacRoleActionService;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacRole findByRoleCode(String roleCode) {
		return uacRoleMapper.findByRoleCode(roleCode);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<RoleVo> queryRoleListWithPage(UacRole role) {
		return uacRoleMapper.queryRoleListWithPage(role);
	}

	@Override
	public int deleteRoleById(Long roleId) {
		//查询该角色下是否有用户绑定, 有的话提醒不能删除
		if (null == roleId) {
			throw new IllegalArgumentException(ErrorCodeEnum.UAC10012001.msg());
		}

		// 超级管理员不能删除
		if (Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			throw new UacBizException(ErrorCodeEnum.UAC10012003);
		}

		List<UacRoleUser> uruList = uacRoleUserService.listByRoleId(roleId);

		if (!uruList.isEmpty()) {
			uacRoleUserService.deleteByRoleId(roleId);
		}

		uacRoleActionService.deleteByRoleId(roleId);
		uacRoleMenuService.deleteByRoleId(roleId);
		return uacRoleMapper.deleteByPrimaryKey(roleId);
	}

	@Override
	public int saveRole(UacRole role, LoginAuthDto loginAuthDto) {
		int result = 0;
		role.setUpdateInfo(loginAuthDto);
		if (role.isNew()) {
			role.setId(super.generateId());
			uacRoleMapper.insertSelective(role);
		} else {
			result = uacRoleMapper.updateByPrimaryKeySelective(role);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Long> getAuthTreeNoCheckMenu(Long roleId) {
		//查询某个角色下一级菜单下的二级菜单个数, 去掉二级菜单个数为0的一级菜单选中状态
		List<MenuCountVo> menuCountVos = uacRoleMenuMapper.countChildMenuNum(roleId);
		List<Long> noCheckedMenu = Lists.newArrayList();
		for (MenuCountVo vo : menuCountVos) {
			noCheckedMenu.add(vo.getId());
		}

		return noCheckedMenu;
	}

	@Override
	public void bindAction(RoleBindActionDto grantAuthRole) {
		Long roleId = grantAuthRole.getRoleId();
		Set<Long> actionIdList = grantAuthRole.getActionIdList();

		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		if (Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			logger.error("越权操作, 超级管理员用户不允许操作");
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		UacRole uacRole = uacRoleMapper.selectByPrimaryKey(roleId);

		if (uacRole == null) {
			logger.error("找不到角色信息. roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// TODO 校验参数的合法性(这里不写了 累得慌 也就是校验菜单和权限是否存在)
		List<UacRoleAction> uacRoleActionList = uacRoleActionService.listByRoleId(roleId);

		if (PublicUtil.isNotEmpty(uacRoleActionList)) {
			uacRoleActionService.deleteByRoleId(roleId);
		}

		if (PublicUtil.isEmpty(actionIdList)) {
			logger.error("传入按钮权限Id为空, 取消所有按钮权限");
		} else {
			// 绑定权限
			uacRoleActionService.insert(roleId, actionIdList);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacRole getRoleById(Long roleId) {
		return uacRoleMapper.selectByPrimaryKey(roleId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public RoleBindUserDto getRoleBindUserDto(Long roleId, Long currentUserId) {
		RoleBindUserDto roleBindUserDto = new RoleBindUserDto();
		Set<Long> alreadyBindUserIdSet = Sets.newHashSet();
		UacRole uacRole = uacRoleMapper.selectByPrimaryKey(roleId);
		if (PublicUtil.isEmpty(uacRole)) {
			logger.error("找不到roleId={}, 的角色", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// 查询所有用户包括已禁用的用户
		List<BindUserDto> bindUserDtoList = uacRoleMapper.selectAllNeedBindUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID, currentUserId);
		// 该角色已经绑定的用户
		List<UacRoleUser> setAlreadyBindUserSet = uacRoleUserService.listByRoleId(roleId);
		Set<BindUserDto> allUserSet = new HashSet<>(bindUserDtoList);

		for (UacRoleUser uacRoleUser : setAlreadyBindUserSet) {
			alreadyBindUserIdSet.add(uacRoleUser.getUserId());
		}

		roleBindUserDto.setAllUserSet(allUserSet);
		roleBindUserDto.setAlreadyBindUserIdSet(alreadyBindUserIdSet);

		return roleBindUserDto;
	}

	@Override
	public void bindUser4Role(RoleBindUserReqDto roleBindUserReqDto, LoginAuthDto authResDto) {

		if (roleBindUserReqDto == null) {
			logger.error("参数不能为空");
			throw new IllegalArgumentException("参数不能为空");
		}

		Long roleId = roleBindUserReqDto.getRoleId();
		Long loginUserId = authResDto.getUserId();
		List<Long> userIdList = roleBindUserReqDto.getUserIdList();

		if (null == roleId) {
			throw new IllegalArgumentException(ErrorCodeEnum.UAC10012001.msg());
		}

		UacRole role = this.getRoleById(roleId);

		if (role == null) {
			logger.error("找不到角色信息 roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		if (PublicUtil.isNotEmpty(userIdList) && userIdList.contains(loginUserId)) {
			logger.error("不能操作当前登录用户 userId={}", loginUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 查询超级管理员用户Id集合
		List<Long> superUserList = uacRoleUserService.listSuperUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		List<Long> unionList = Collections3.intersection(userIdList, superUserList);
		if (PublicUtil.isNotEmpty(userIdList) && PublicUtil.isNotEmpty(unionList)) {
			logger.error("不能操作超级管理员用户 超级用户={}", unionList);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 1. 先取消对该角色的用户绑定(不包含超级管理员用户)
		List<UacRoleUser> userRoles = uacRoleUserService.listByRoleId(roleId);

		if (PublicUtil.isNotEmpty(userRoles)) {
			uacRoleUserService.deleteExcludeSuperMng(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		}

		if (PublicUtil.isEmpty(userIdList)) {
			// 取消该角色的所有用户的绑定
			logger.info("取消绑定所有非超级管理员用户成功");
			return;
		}

		// 绑定所选用户
		for (Long userId : userIdList) {
			UacUser uacUser = uacUserService.queryByUserId(userId);
			if (PublicUtil.isEmpty(uacUser)) {
				logger.error("找不到绑定的用户 userId={}", userId);
				throw new UacBizException(ErrorCodeEnum.UAC10011024, userId);
			}
			uacRoleUserService.saveRoleUser(userId, roleId);
		}

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacRole> findAllRoleInfoByUserId(Long userId) {
		return uacRoleMapper.selectAllRoleInfoByUserId(userId);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public BindAuthVo getActionTreeByRoleId(Long roleId) {
		BindAuthVo bindAuthVo = new BindAuthVo();
		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		UacRole roleById = this.getRoleById(roleId);
		if (roleById == null) {
			logger.error("找不到角色信息 roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		List<UacMenu> uacMenus = uacMenuService.listMenuListByRoleId(roleId);
		if (PublicUtil.isEmpty(uacMenus)) {
			throw new UacBizException(ErrorCodeEnum.UAC10013009);
		}
		// 查询所有的权限信息
		List<UacAction> uacActions = uacActionService.listActionList(uacMenus);
		// 合并菜单和按钮权限 递归生成树结构

		List<MenuVo> menuVoList = this.getAuthList(uacMenus, uacActions);

		List<MenuVo> tree = TreeUtil.getChildMenuVos(menuVoList, 0L);

		// 获取所有绑定的菜单和按钮权限Id集合
		List<Long> checkedAuthList = uacActionService.getCheckedActionList(roleId);

		bindAuthVo.setAuthTree(tree);
		bindAuthVo.setCheckedAuthList(checkedAuthList);

		return bindAuthVo;
	}

	private List<MenuVo> getAuthList(List<UacMenu> uacMenus, List<UacAction> uacActions) {
		List<MenuVo> menuVoList = Lists.newArrayList();
		MenuVo menuVo;
		for (UacMenu uacMenu : uacMenus) {
			menuVo = new MenuVo();
			BeanUtils.copyProperties(uacMenu, menuVo);
			menuVo.setRemark("menu");
			menuVoList.add(menuVo);
		}
		if (PublicUtil.isNotEmpty(uacActions)) {
			for (UacAction uacAction : uacActions) {
				menuVo = new MenuVo();
				menuVo.setId(uacAction.getId());
				menuVo.setMenuName(uacAction.getActionName());
				menuVo.setMenuCode(uacAction.getActionCode());
				menuVo.setPid(uacAction.getMenuId());
				menuVo.setUrl(uacAction.getUrl());
				menuVo.setRemark("action");
				menuVoList.add(menuVo);
			}
		}
		return menuVoList;
	}

	@Override
	public void batchDeleteByIdList(List<Long> roleIdList) {
		logger.info("批量删除角色. idList={}", roleIdList);
		Preconditions.checkArgument(PublicUtil.isNotEmpty(roleIdList), "删除角色ID不存在");

		List<UacRoleUser> uruList = uacRoleUserService.listByRoleIdList(roleIdList);
		if (!uruList.isEmpty()) {
			uacRoleUserService.deleteByRoleIdList(roleIdList);
		}

		uacRoleMenuService.deleteByRoleIdList(roleIdList);
		uacRoleActionService.deleteByRoleIdList(roleIdList);

		int result = uacRoleMapper.batchDeleteByIdList(roleIdList);
		if (result < roleIdList.size()) {
			throw new UacBizException(ErrorCodeEnum.UAC10012006, Joiner.on(GlobalConstant.Symbol.COMMA).join(roleIdList));
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getOwnAuthTree(Long userId) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		return uacMenuService.getMenuVoList(userId, GlobalConstant.Sys.OPER_APPLICATION_ID);
	}

	@Override
	public void bindMenu(RoleBindMenuDto roleBindMenuDto) {

		Long roleId = roleBindMenuDto.getRoleId();
		Set<Long> menuIdList = roleBindMenuDto.getMenuIdList();

		if (roleId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10012001);
		}

		if (Objects.equals(roleId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			logger.error("越权操作, 超级管理员用户不允许操作");
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		UacRole uacRole = uacRoleMapper.selectByPrimaryKey(roleId);

		if (uacRole == null) {
			logger.error("找不到角色信息. roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// TODO 校验参数的合法性(这里不写了 累得慌 也就是校验菜单和权限是否存在)
		List<UacRoleMenu> uacRoleMenuList = uacRoleMenuService.listByRoleId(roleId);

		if (PublicUtil.isNotEmpty(uacRoleMenuList)) {
			uacRoleMenuService.deleteByRoleId(roleId);
		}

		// menuSet actionIdList 如果为空则 取消该角色所有权限
		if (PublicUtil.isEmpty(menuIdList)) {
			logger.error("传入菜单权限Id为空, 取消菜单权限");
		} else {
			// 绑定菜单
			uacRoleMenuService.insert(roleId, menuIdList);

		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public BindAuthVo getMenuTreeByRoleId(Long roleId) {
		BindAuthVo bindAuthVo = new BindAuthVo();
		Preconditions.checkArgument(roleId != null, ErrorCodeEnum.UAC10012001.msg());

		UacRole roleById = this.getRoleById(roleId);
		if (roleById == null) {
			logger.error("找不到角色信息 roleId={}", roleId);
			throw new UacBizException(ErrorCodeEnum.UAC10012005, roleId);
		}

		// 查询所有的菜单信息
		List<UacMenu> uacMenus = uacMenuService.selectAll();
		// 合并菜单和按钮权限 递归生成树结构

		List<MenuVo> menuVoList = this.getAuthList(uacMenus, null);

		List<MenuVo> tree = TreeUtil.getChildMenuVos(menuVoList, 0L);

		// 获取所有绑定的菜单和按钮权限Id集合
		List<Long> checkedAuthList = uacActionService.getCheckedMenuList(roleId);

		bindAuthVo.setAuthTree(tree);
		bindAuthVo.setCheckedAuthList(checkedAuthList);

		return bindAuthVo;
	}
}
