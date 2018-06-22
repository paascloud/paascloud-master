/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：UacMenuServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.UacMenuMapper;
import com.paascloud.provider.model.constant.MenuConstant;
import com.paascloud.provider.model.domain.UacMenu;
import com.paascloud.provider.model.domain.UacRoleMenu;
import com.paascloud.provider.model.dto.menu.UacMenuStatusDto;
import com.paascloud.provider.model.enums.UacMenuStatusEnum;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.model.vo.ViewMenuVo;
import com.paascloud.provider.service.UacActionService;
import com.paascloud.provider.service.UacMenuService;
import com.paascloud.provider.service.UacRoleMenuService;
import com.paascloud.provider.utils.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;


/**
 * The class Uac menu service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacMenuServiceImpl extends BaseService<UacMenu> implements UacMenuService {
	@Resource
	private UacMenuMapper uacMenuMapper;
	@Resource
	private UacRoleMenuService uacRoleMenuService;
	@Resource
	private UacActionService uacActionService;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getMenuVoList(Long userId, Long applicationId) {
		// 1.查询该用户下所有的菜单列表
		List<MenuVo> menuVoList = Lists.newArrayList();
		List<UacMenu> menuList;
		Set<UacMenu> menuSet = Sets.newHashSet();
		// 如果是admin则返回所有的菜单
		if (userId == 1L) {
			// 1.1 查询该用户下所有的菜单列表
			UacMenu uacMenuQuery = new UacMenu();
			uacMenuQuery.setStatus(UacMenuStatusEnum.ENABLE.getType());
			uacMenuQuery.setApplicationId(applicationId);
			uacMenuQuery.setOrderBy(" level asc,number asc");
			menuList = uacMenuMapper.selectMenuList(uacMenuQuery);
		} else {
			// 1.2查询该用户下所有的菜单列表
			menuVoList = uacMenuMapper.findMenuVoListByUserId(userId);
			if (PublicUtil.isEmpty(menuVoList)) {
				return null;
			}
			Set<Long> ids = Sets.newHashSet();
			for (final MenuVo menuVo : menuVoList) {
				ids.add(menuVo.getId());
			}

			List<UacMenu> ownMenuList = this.getMenuList(ids);

			// 查出所有含有菜单的菜单信息
			UacMenu uacMenu = new UacMenu();
			uacMenu.setStatus(UacMenuStatusEnum.ENABLE.getType());
			List<UacMenu> allMenuList = this.selectMenuList(uacMenu);
			Map<Long, UacMenu> map = Maps.newHashMap();
			for (final UacMenu menu : allMenuList) {
				map.put(menu.getId(), menu);
			}

			for (final UacMenu menu : ownMenuList) {
				getPid(menuSet, menu, map);
			}
			menuList = new ArrayList<>(menuSet);
		}
		List<MenuVo> list = getMenuVo(menuList);
		if (PublicUtil.isNotEmpty(menuVoList)) {
			list.addAll(menuVoList);
		}
		// 2.递归成树
		return TreeUtil.getChildMenuVos(list, 0L);
	}

	private void getPid(Set<UacMenu> menuSet, UacMenu menu, Map<Long, UacMenu> map) {
		UacMenu parent = map.get(menu.getPid());
		if (parent != null && parent.getId() != 0L) {
			menuSet.add(parent);
			getPid(menuSet, parent, map);
		}
	}

	private List<MenuVo> getMenuVo(List<UacMenu> list) {
		List<MenuVo> menuVoList = Lists.newArrayList();
		for (UacMenu uacMenu : list) {
			MenuVo menuVo = new MenuVo();
			BeanUtils.copyProperties(uacMenu, menuVo);
			menuVo.setUrl(uacMenu.getUrl());
			menuVo.setMenuName(uacMenu.getMenuName());
			menuVoList.add(menuVo);
		}
		return menuVoList;
	}

	@Override
	public int saveUacMenu(UacMenu menu, LoginAuthDto loginAuthDto) {
		Long pid = menu.getPid();
		menu.setUpdateInfo(loginAuthDto);
		UacMenu parentMenu = mapper.selectByPrimaryKey(pid);
		if (PublicUtil.isEmpty(parentMenu)) {
			throw new UacBizException(ErrorCodeEnum.UAC10013001, pid);
		}
		if (menu.isNew()) {

			UacMenu updateMenu = new UacMenu();
			menu.setLevel(parentMenu.getLevel() + 1);
			updateMenu.setLeaf(MenuConstant.MENU_LEAF_NO);
			updateMenu.setId(pid);
			Long menuId = super.generateId();
			menu.setId(menuId);
			int result = mapper.updateByPrimaryKeySelective(updateMenu);
			if (result < 1) {
				throw new UacBizException(ErrorCodeEnum.UAC10013002, menuId);
			}

			menu.setStatus(UacMenuStatusEnum.ENABLE.getType());
			menu.setCreatorId(loginAuthDto.getUserId());
			menu.setCreator(loginAuthDto.getUserName());
			menu.setLastOperatorId(loginAuthDto.getUserId());
			menu.setLastOperator(loginAuthDto.getUserName());
			// 新增的菜单是叶子节点
			menu.setLeaf(MenuConstant.MENU_LEAF_YES);
			return uacMenuMapper.insertSelective(menu);
		} else {
			return uacMenuMapper.updateByPrimaryKeySelective(menu);
		}
	}

	@Override
	public int deleteUacMenuById(Long id, LoginAuthDto loginAuthDto) {
		Preconditions.checkArgument(id != null, "菜单id不能为空");
		int result;
		// 获取当前菜单信息
		UacMenu uacMenuQuery = new UacMenu();
		uacMenuQuery.setId(id);
		uacMenuQuery = mapper.selectOne(uacMenuQuery);
		if (PublicUtil.isEmpty(uacMenuQuery)) {
			throw new UacBizException(ErrorCodeEnum.UAC10013003, id);
		}

		// 删除菜单与角色的关联关系
		UacRoleMenu uacRoleMenu = new UacRoleMenu();
		uacRoleMenu.setMenuId(id);
		uacRoleMenuService.delete(uacRoleMenu);


		// 删除菜单
		result = uacMenuMapper.deleteByPrimaryKey(id);
		if (result < 1) {
			logger.error("删除菜单失败 menuId={}", id);
			throw new UacBizException(ErrorCodeEnum.UAC10013008, id);
		}

		// 删除权限
		// TODO 应该先查询再删除
		uacActionService.deleteByMenuId(id);

		// 修改当前删除菜单的父菜单是否是叶子节点
		UacMenu updateParentUacMenu = new UacMenu();
		updateParentUacMenu.setId(uacMenuQuery.getPid());
		updateParentUacMenu.setLeaf(MenuConstant.MENU_LEAF_YES);
		// 是二三级
		if (Objects.equals(MenuConstant.MENU_LEVEL_TWO, uacMenuQuery.getLevel()) || Objects.equals(MenuConstant.MENU_LEVEL_THREE, uacMenuQuery.getLevel())) {
			// 查询是否是叶子节点
			int count = uacMenuMapper.selectMenuChildCountByPid(uacMenuQuery.getPid());
			if (count == 0) {
				uacMenuMapper.updateByPrimaryKeySelective(updateParentUacMenu);
			}
		}
		return result;
	}

	@Override
	public int enableMenuList(List<UacMenu> menuList, LoginAuthDto loginAuthDto) {
		UacMenu uacMenuUpdate = new UacMenu();
		int sum = 0;
		for (UacMenu menu : menuList) {
			uacMenuUpdate.setId(menu.getId());
			uacMenuUpdate.setVersion(menu.getVersion() + 1);
			uacMenuUpdate.setStatus(UacMenuStatusEnum.ENABLE.getType());
			uacMenuUpdate.setLastOperator(loginAuthDto.getLoginName());
			uacMenuUpdate.setLastOperatorId(loginAuthDto.getUserId());
			uacMenuUpdate.setUpdateTime(new Date());
			int result = mapper.updateByPrimaryKeySelective(uacMenuUpdate);
			if (result > 0) {
				sum += 1;
			} else {
				throw new UacBizException(ErrorCodeEnum.UAC10013004, menu.getId());
			}
		}
		return sum;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacMenu> getAllParentMenuByMenuId(Long menuId) {
		UacMenu uacMenuQuery = new UacMenu();
		uacMenuQuery.setId(menuId);
		uacMenuQuery = mapper.selectOne(uacMenuQuery);
		List<UacMenu> uacMenuList = Lists.newArrayList();
		uacMenuList = buildParentNote(uacMenuList, uacMenuQuery);
		return uacMenuList;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacMenu> getAllChildMenuByMenuId(Long menuId, String menuStatus) {
		UacMenu uacMenuQuery = new UacMenu();
		uacMenuQuery.setId(menuId);
		uacMenuQuery = mapper.selectOne(uacMenuQuery);
		List<UacMenu> uacMenuList = Lists.newArrayList();
		uacMenuList = buildNode(uacMenuList, uacMenuQuery, menuStatus);
		return uacMenuList;
	}

	@Override
	public int disableMenuList(List<UacMenu> menuList, LoginAuthDto loginAuthDto) {
		UacMenu uacMenuUpdate = new UacMenu();
		int sum = 0;
		for (UacMenu menu : menuList) {
			uacMenuUpdate.setId(menu.getId());
			uacMenuUpdate.setVersion(menu.getVersion() + 1);
			uacMenuUpdate.setStatus(UacMenuStatusEnum.DISABLE.getType());
			uacMenuUpdate.setLastOperator(loginAuthDto.getLoginName());
			uacMenuUpdate.setLastOperatorId(loginAuthDto.getUserId());
			uacMenuUpdate.setUpdateTime(new Date());
			int result = mapper.updateByPrimaryKeySelective(uacMenuUpdate);
			if (result > 0) {
				sum += 1;
			} else {
				throw new UacBizException(ErrorCodeEnum.UAC10013005, menu.getId());
			}
		}
		return sum;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacMenu> selectMenuList(UacMenu uacMenu) {
		return uacMenuMapper.selectMenuList(uacMenu);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> findAllMenuListByAuthResDto(LoginAuthDto authResDto) {
		List<MenuVo> voList = Lists.newArrayList();
		Preconditions.checkArgument(authResDto != null, "无权访问");

		if (!GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME.equals(authResDto.getLoginName())) {
			voList = uacMenuMapper.findMenuVoListByUserId(authResDto.getUserId());
		} else {
			UacMenu uacMenuQuery = new UacMenu();
			// 查询启用的菜单
			uacMenuQuery.setStatus(UacMenuStatusEnum.ENABLE.getType());
			uacMenuQuery.setOrderBy(" level asc,number asc");
			List<UacMenu> list = uacMenuMapper.select(uacMenuQuery);
			for (UacMenu uacMenu : list) {
				MenuVo menuVo = new MenuVo();
				BeanUtils.copyProperties(uacMenu, menuVo);
				voList.add(menuVo);
			}
		}
		return voList;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public ViewMenuVo getViewVoById(Long id) {
		Preconditions.checkArgument(id != null, "菜单ID不能为空");
		UacMenu menu = uacMenuMapper.selectByPrimaryKey(id);

		if (menu == null) {
			logger.error("找不到菜单信息id={}", id);
			throw new UacBizException(ErrorCodeEnum.UAC10013003, id);
		}

		// 获取父级菜单信息
		UacMenu parentMenu = uacMenuMapper.selectByPrimaryKey(menu.getPid());

		ModelMapper modelMapper = new ModelMapper();
		ViewMenuVo menuVo = modelMapper.map(menu, ViewMenuVo.class);

		if (parentMenu != null) {
			menuVo.setParentMenuName(parentMenu.getMenuName());
		}

		return menuVo;
	}

	@Override
	public void updateUacMenuStatusById(UacMenuStatusDto uacMenuStatusDto, LoginAuthDto loginAuthDto) {
		Long id = uacMenuStatusDto.getId();
		String status = uacMenuStatusDto.getStatus();
		Preconditions.checkArgument(id != null, "菜单ID不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(status), "菜单状态不能为空");

		UacMenu uacMenuQuery = this.selectByKey(id);
		if (MenuConstant.MENU_LEVEL_ROOT.equals(uacMenuQuery.getLevel())) {
			throw new UacBizException(ErrorCodeEnum.UAC10013007);
		}
		// 要处理的菜单集合
		List<UacMenu> menuList = Lists.newArrayList();

		int result;
		if (status.equals(UacMenuStatusEnum.DISABLE.getType())) {
			// 获取菜单以及子菜单
			menuList = this.getAllChildMenuByMenuId(id, UacMenuStatusEnum.ENABLE.getType());
			// 禁用菜单以及子菜单
			result = this.disableMenuList(menuList, loginAuthDto);
		} else {
			// 获取菜单、其子菜单以及父菜单
			UacMenu uacMenu = new UacMenu();
			uacMenu.setPid(id);
			result = this.selectCount(uacMenu);
			// 此菜单含有子菜单
			if (result > 0) {
				menuList = this.getAllChildMenuByMenuId(id, UacMenuStatusEnum.DISABLE.getType());
			}
			List<UacMenu> menuListTemp = this.getAllParentMenuByMenuId(id);
			for (UacMenu menu : menuListTemp) {
				if (!menuList.contains(menu)) {
					menuList.add(menu);
				}
			}
			// 启用菜单、其子菜单以及父菜单
			result = this.enableMenuList(menuList, loginAuthDto);
		}
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10013006, id);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkMenuHasChildMenu(Long pid) {
		Preconditions.checkArgument(pid != null, "菜单pid不能为空");

		UacMenu uacMenu = new UacMenu();
		uacMenu.setStatus(UacMenuStatusEnum.ENABLE.getType());
		uacMenu.setPid(pid);

		return mapper.selectCount(uacMenu) > 0;
	}

	@Override
	public List<UacMenu> listMenuListByRoleId(Long roleId) {
		List<UacMenu> menuList = uacMenuMapper.listMenuListByRoleId(roleId);
		List<UacMenu> addMenuList = Lists.newArrayList();

		if (PublicUtil.isNotEmpty(menuList)) {
			for (UacMenu uacMenu : menuList) {
				getMenuList(addMenuList, uacMenu.getPid());
			}
		}
		menuList.addAll(addMenuList);
		return new ArrayList<>(new HashSet<>(menuList));
	}

	@Override
	public List<UacMenu> getMenuList(final Set<Long> menuIdList) {
		return uacMenuMapper.listMenu(menuIdList);
	}

	private List<UacMenu> getMenuList(List<UacMenu> uacMenuList, Long menuId) {
		UacMenu uacMenu = uacMenuMapper.selectByPrimaryKey(menuId);
		if (uacMenu != null) {
			Long pid = uacMenu.getPid();
			if (pid != null) {
				uacMenuList.add(uacMenu);
				getMenuList(uacMenuList, pid);
			}
		}
		return uacMenuList;
	}

	/**
	 * 递归获取菜单的子菜单
	 */
	private List<UacMenu> buildNode(List<UacMenu> uacMenuList, UacMenu uacMenu, String menuStatus) {
		List<UacMenu> uacMenuQueryList = mapper.select(uacMenu);
		UacMenu uacMenuQuery;
		for (UacMenu menu : uacMenuQueryList) {
			// 启用状态
			if (menuStatus.equals(menu.getStatus()) && !MenuConstant.MENU_LEVEL_ROOT.equals(menu.getLevel())) {
				uacMenuList.add(menu);
			}
			uacMenuQuery = new UacMenu();
			uacMenuQuery.setPid(menu.getId());
			buildNode(uacMenuList, uacMenuQuery, menuStatus);
		}
		return uacMenuList;
	}

	/**
	 * 递归获取菜单的父菜单
	 */
	private List<UacMenu> buildParentNote(List<UacMenu> uacMenuList, UacMenu uacMenu) {
		List<UacMenu> uacMenuQueryList = mapper.select(uacMenu);
		UacMenu uacMenuQuery;
		for (UacMenu menu : uacMenuQueryList) {
			if (UacMenuStatusEnum.DISABLE.getType().equals(menu.getStatus()) && !MenuConstant.MENU_LEVEL_ROOT.equals(menu.getLevel())) {
				// 禁用状态
				uacMenuList.add(menu);
			}
			uacMenuQuery = new UacMenu();
			uacMenuQuery.setId(menu.getPid());
			buildParentNote(uacMenuList, uacMenuQuery);
		}
		return uacMenuList;
	}
}
