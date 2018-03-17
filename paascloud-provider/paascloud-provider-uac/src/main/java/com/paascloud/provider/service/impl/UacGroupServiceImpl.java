package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.paascloud.Collections3;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.mapper.UacGroupMapper;
import com.paascloud.provider.mapper.UacGroupUserMapper;
import com.paascloud.provider.mapper.UacRoleMapper;
import com.paascloud.provider.mapper.UacRoleUserMapper;
import com.paascloud.provider.model.domain.UacGroup;
import com.paascloud.provider.model.domain.UacGroupUser;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.group.GroupBindUserDto;
import com.paascloud.provider.model.dto.group.GroupBindUserReqDto;
import com.paascloud.provider.model.dto.role.BindUserDto;
import com.paascloud.provider.model.dto.user.IdStatusDto;
import com.paascloud.provider.model.enums.UacGroupStatusEnum;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.vo.GroupZtreeVo;
import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.service.MdcAddressService;
import com.paascloud.provider.service.UacGroupService;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.provider.utils.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * The class Uac group service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacGroupServiceImpl extends BaseService<UacGroup> implements UacGroupService {

	@Resource
	private UacGroupMapper uacGroupMapper;
	@Resource
	private UacGroupUserMapper uacGroupUserMapper;
	@Resource
	private UacRoleUserMapper uacRoleUserMapper;
	@Resource
	private UacRoleMapper uacRoleMapper;
	@Resource
	private UacUserService uacUserService;
	@Resource
	private MdcAddressService mdcAddressService;

	private int addUacGroup(UacGroup group) {
		if (StringUtils.isEmpty(group.getStatus())) {
			group.setStatus(UacGroupStatusEnum.ENABLE.getStatus());
		}
		return uacGroupMapper.insertSelective(group);
	}

	private int editUacGroup(UacGroup group) {
		return uacGroupMapper.updateByPrimaryKeySelective(group);
	}

	@Override
	public int updateUacGroupStatusById(IdStatusDto idStatusDto, LoginAuthDto loginAuthDto) {

		Long groupId = idStatusDto.getId();
		Integer status = idStatusDto.getStatus();

		UacGroup uacGroup = new UacGroup();
		uacGroup.setId(groupId);
		uacGroup.setStatus(status);

		UacGroup group = uacGroupMapper.selectByPrimaryKey(groupId);
		if (PublicUtil.isEmpty(group)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015001, groupId);
		}
		if (!UacGroupStatusEnum.contains(status)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015002);
		}

		//查询所有的组织
		List<UacGroup> totalGroupList = uacGroupMapper.selectAll();
		List<GroupZtreeVo> totalList = Lists.newArrayList();
		GroupZtreeVo zTreeVo;
		for (UacGroup vo : totalGroupList) {
			zTreeVo = new GroupZtreeVo();
			zTreeVo.setId(vo.getId());
			totalList.add(zTreeVo);
		}

		UacGroupUser uacGroupUser = new UacGroupUser();
		uacGroupUser.setUserId(loginAuthDto.getUserId());
		UacGroupUser groupUser = uacGroupUserMapper.selectOne(uacGroupUser);
		// 查询当前登陆人所在的组织信息
		UacGroup currentUserUacGroup = uacGroupMapper.selectByPrimaryKey(groupUser.getGroupId());
		// 查询当前登陆人能禁用的所有子节点
		List<GroupZtreeVo> childGroupList = this.getGroupTree(currentUserUacGroup.getId());
		// 计算不能禁用的组织= 所有的组织 - 禁用的所有子节点
		totalList.removeAll(childGroupList);
		// 判断所选的组织是否在不能禁用的列表里
		GroupZtreeVo zTreeVo1 = new GroupZtreeVo();
		zTreeVo1.setId(group.getId());
		if (totalList.contains(zTreeVo1)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}
		if (groupUser.getGroupId().equals(uacGroup.getId()) && UacGroupStatusEnum.ENABLE.getStatus() == group.getStatus()) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}
		uacGroup.setGroupName(group.getGroupName());
		uacGroup.setGroupCode(group.getGroupCode());
		uacGroup.setVersion(group.getVersion() + 1);
		int result = uacGroupMapper.updateByPrimaryKeySelective(uacGroup);
		// 获取当前所选组织的所有子节点
		List<GroupZtreeVo> childUacGroupList = this.getGroupTree(uacGroup.getId());
		// 批量修改组织状态
		if (PublicUtil.isNotEmpty(childUacGroupList)) {
			UacGroup childGroup;
			for (GroupZtreeVo uacGroup1 : childUacGroupList) {
				if (UacGroupStatusEnum.ENABLE.getStatus() == status) {
					UacGroup parentGroup = uacGroupMapper.selectByPrimaryKey(uacGroup1.getpId());
					if (parentGroup.getStatus() == UacGroupStatusEnum.DISABLE.getStatus()) {
						throw new UacBizException(ErrorCodeEnum.UAC10015003);
					}
				}
				childGroup = new UacGroup();
				childGroup.setStatus(uacGroup.getStatus());
				childGroup.setId(uacGroup1.getId());
				result = uacGroupMapper.updateByPrimaryKeySelective(childGroup);
				if (result < 1) {
					throw new UacBizException(ErrorCodeEnum.UAC10015006, uacGroup1.getId());
				}
			}
		}
		return result;
	}

	@Override
	public int deleteUacGroupById(Long id) {

		Preconditions.checkArgument(id != null, "组织id为空");
		Preconditions.checkArgument(!Objects.equals(id, GlobalConstant.Sys.SUPER_MANAGER_GROUP_ID), "该组织不能删除");

		// 根据前台传入的组织参数校验该组织是否存在
		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(id);
		if (PublicUtil.isEmpty(uacGroup)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015004, id);
		}
		//判断该组织下是否存在子节点
		UacGroup childGroup = new UacGroup();
		childGroup.setPid(id);
		List<UacGroup> childGroupList = uacGroupMapper.select(childGroup);
		if (PublicUtil.isNotEmpty(childGroupList)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015007, id);
		}
		//判断该组织下是否存在用户
		UacGroupUser uacGroupUser = new UacGroupUser();
		uacGroupUser.setGroupId(id);
		List<UacGroupUser> uacGroupUserList = uacGroupUserMapper.select(uacGroupUser);
		if (PublicUtil.isNotEmpty(uacGroupUserList)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015008, id);
		}

		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacGroup queryById(Long groupId) {
		Preconditions.checkArgument(PublicUtil.isNotEmpty(groupId), "组织Id不能为空");
		UacGroup query = new UacGroup();
		query.setId(groupId);
		return uacGroupMapper.selectOne(query);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<GroupZtreeVo> getGroupTree(Long groupId) {
		// 1. 如果是仓库则 直接把仓库信息封装成ztreeVo返回
		List<GroupZtreeVo> tree = Lists.newArrayList();

		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(groupId);

		GroupZtreeVo zTreeMenuVo = buildGroupZTreeVoByGroup(uacGroup);
		if (0L == uacGroup.getPid()) {
			zTreeMenuVo.setOpen(true);
		}

		tree.add(zTreeMenuVo);

		// 2.如果是组织id则遍历组织+仓库的树结构

		// 如果是组织 则查询父id为
		UacGroup uacGroupQuery = new UacGroup();
		uacGroupQuery.setPid(groupId);
		List<UacGroup> groupList = uacGroupMapper.select(uacGroupQuery);
		if (PublicUtil.isNotEmpty(groupList)) {
			tree = buildNode(groupList, tree);
		}

		return tree;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<MenuVo> getGroupTreeListByUserId(Long userId) {
		UacGroupUser groupUser = uacGroupUserMapper.getByUserId(userId);
		Long groupId = groupUser.getGroupId();
		//查询当前登陆人所在的组织信息
		UacGroup currentUserUacGroup = uacGroupMapper.selectByPrimaryKey(groupId);
		//获取当前所选组织的所有子节点
		List<GroupZtreeVo> childUacGroupList = this.getGroupTree(currentUserUacGroup.getId());
		return this.buildGroupTree(childUacGroupList, groupId);
	}

	private List<MenuVo> buildGroupTree(List<GroupZtreeVo> childUacGroupList, Long currentGroupId) {
		List<MenuVo> listVo = Lists.newArrayList();
		MenuVo menuVo;
		for (GroupZtreeVo group : childUacGroupList) {
			menuVo = new MenuVo();
			menuVo.setId(group.getId());
			if (currentGroupId.equals(group.getId())) {
				menuVo.setPid(0L);
			} else {
				menuVo.setPid(group.getpId());
			}
			menuVo.setMenuCode(group.getGroupCode());
			menuVo.setMenuName(group.getGroupName());
			listVo.add(menuVo);
		}

		return TreeUtil.getChildMenuVos(listVo, 0L);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public GroupBindUserDto getGroupBindUserDto(Long groupId, Long currentUserId) {
		GroupBindUserDto groupBindUserDto = new GroupBindUserDto();
		Set<Long> alreadyBindUserIdSet = Sets.newHashSet();
		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(groupId);
		if (PublicUtil.isEmpty(uacGroup)) {
			logger.error("找不到uacGroup={}, 的组织", uacGroup);
			throw new UacBizException(ErrorCodeEnum.UAC10015001, groupId);
		}

		// 查询所有用户包括已禁用的用户
		List<BindUserDto> bindUserDtoList = uacRoleMapper.selectAllNeedBindUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID, currentUserId);
		// 该组织已经绑定的用户
		List<UacGroupUser> setAlreadyBindUserSet = uacGroupUserMapper.listByGroupId(groupId);

		Set<BindUserDto> allUserSet = new HashSet<>(bindUserDtoList);

		for (UacGroupUser uacGroupUser : setAlreadyBindUserSet) {
			alreadyBindUserIdSet.add(uacGroupUser.getUserId());
		}

		groupBindUserDto.setAllUserSet(allUserSet);
		groupBindUserDto.setAlreadyBindUserIdSet(alreadyBindUserIdSet);

		return groupBindUserDto;
	}

	/**
	 * Bind uac user 4 group int.
	 *
	 * @param groupBindUserReqDto the group bind user req dto
	 * @param authResDto          the auth res dto
	 */
	@Override
	public void bindUacUser4Group(GroupBindUserReqDto groupBindUserReqDto, LoginAuthDto authResDto) {
		if (groupBindUserReqDto == null) {
			logger.error("参数不能为空");
			throw new IllegalArgumentException("参数不能为空");
		}

		Long groupId = groupBindUserReqDto.getGroupId();
		Long loginUserId = authResDto.getUserId();
		List<Long> userIdList = groupBindUserReqDto.getUserIdList();

		if (null == groupId) {
			throw new IllegalArgumentException("組織ID不能为空");
		}

		UacGroup group = uacGroupMapper.selectByPrimaryKey(groupId);

		if (group == null) {
			logger.error("找不到角色信息 groupId={}", groupId);
			throw new UacBizException(ErrorCodeEnum.UAC10015001, groupId);
		}

		if (PublicUtil.isNotEmpty(userIdList) && userIdList.contains(loginUserId)) {
			logger.error("不能操作当前登录用户 userId={}", loginUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 查询超级管理员用户Id集合
		List<Long> superUserList = uacRoleUserMapper.listSuperUser(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		List<Long> unionList = Collections3.intersection(userIdList, superUserList);
		if (PublicUtil.isNotEmpty(userIdList) && PublicUtil.isNotEmpty(unionList)) {
			logger.error("不能操作超级管理员用户 超级用户={}", unionList);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 1. 先取消对该角色的用户绑定(不包含超级管理员用户)
		List<UacGroupUser> groupUsers = uacGroupUserMapper.listByGroupId(groupId);

		if (PublicUtil.isNotEmpty(groupUsers)) {
			uacGroupUserMapper.deleteExcludeSuperMng(groupId, GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
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
			UacGroupUser uacGroupUser = new UacGroupUser();
			uacGroupUser.setUserId(userId);
			uacGroupUser.setGroupId(groupId);
			uacGroupUserMapper.insertSelective(uacGroupUser);
		}
	}

	@Override
	public int saveUacGroup(UacGroup group, LoginAuthDto loginAuthDto) {

		int result;
		Preconditions.checkArgument(!StringUtils.isEmpty(group.getPid()), "上级节点不能为空");

		UacGroup parenGroup = uacGroupMapper.selectByPrimaryKey(group.getPid());
		if (PublicUtil.isEmpty(parenGroup)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015009, group.getPid());
		}
		setGroupAddress(group);
		group.setUpdateInfo(loginAuthDto);

		if (group.isNew()) {
			Long groupId = super.generateId();
			group.setId(groupId);
			group.setLevel(parenGroup.getLevel() + 1);
			result = this.addUacGroup(group);
		} else {
			result = this.editUacGroup(group);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacGroup getById(Long id) {
		UacGroup uacGroup = uacGroupMapper.selectByPrimaryKey(id);
		if (PublicUtil.isEmpty(uacGroup)) {
			throw new UacBizException(ErrorCodeEnum.UAC10015001, id);
		}
		UacGroup parentGroup = uacGroupMapper.selectByPrimaryKey(uacGroup.getPid());
		if (parentGroup != null) {
			uacGroup.setParentGroupCode(parentGroup.getGroupCode());
			uacGroup.setParentGroupName(parentGroup.getGroupName());
		}
		// 处理饿了吗级联菜单回显地址
		Long provinceId = uacGroup.getProvinceId();
		Long cityId = uacGroup.getCityId();
		Long areaId = uacGroup.getAreaId();
		Long streetId = uacGroup.getStreetId();
		List<Long> addressList = Lists.newArrayList();
		if (provinceId != null) {
			addressList.add(provinceId);
		}
		if (cityId != null) {
			addressList.add(cityId);
		}
		if (areaId != null) {
			addressList.add(areaId);
		}
		if (streetId != null) {
			addressList.add(streetId);
		}
		uacGroup.setAddressList(addressList);
		return uacGroup;
	}

	private void setGroupAddress(UacGroup uacGroup) {
		List<Long> addressList = uacGroup.getAddressList();
		Preconditions.checkArgument(!PublicUtil.isEmpty(addressList), "地址不能为空");
		Preconditions.checkArgument(addressList.size() >= GlobalConstant.TWO_INT, "地址至少选两级");

		StringBuilder groupAddress = new StringBuilder();
		int level = 0;
		for (Long addressId : addressList) {
			// 根据地址ID获取地址名称
			String addressName = mdcAddressService.getAddressById(addressId).getName();
			if (level == 0) {
				uacGroup.setProvinceId(addressId);
				uacGroup.setProvinceName(addressName);
			} else if (level == 1) {
				uacGroup.setCityId(addressId);
				uacGroup.setCityName(addressName);
			} else if (level == 2) {
				uacGroup.setAreaId(addressId);
				uacGroup.setAreaName(addressName);
			} else {
				uacGroup.setStreetId(addressId);
				uacGroup.setStreetName(addressName);
			}
			groupAddress.append(addressName);
			level++;
		}
		uacGroup.setGroupAddress(groupAddress.toString());
	}

	private GroupZtreeVo buildGroupZTreeVoByGroup(UacGroup group) {
		GroupZtreeVo vo = new GroupZtreeVo();

		vo.setId(group.getId());
		vo.setpId(group.getPid());
		vo.setName(group.getGroupName());
		vo.setType(group.getType());
		vo.setStatus(group.getStatus());
		vo.setLeaf(group.getLevel());
		vo.setLevel(group.getLevel());
		vo.setGroupCode(group.getGroupCode());

		vo.setContact(group.getContact());
		vo.setContactPhone(group.getContactPhone());
		vo.setCreatedTime(group.getCreatedTime() == null ? new Date() : group.getCreatedTime());
		vo.setCreator(group.getCreator());
		vo.setGroupAddress(group.getGroupAddress());
		vo.setGroupName(group.getGroupName());

		return vo;
	}

	private List<GroupZtreeVo> buildNode(List<UacGroup> groupList, List<GroupZtreeVo> tree) {

		for (UacGroup group : groupList) {

			GroupZtreeVo groupZTreeVo = buildGroupZTreeVoByGroup(group);

			if (0L == group.getPid()) {
				groupZTreeVo.setOpen(true);
			}
			// 设置根节点
			tree.add(groupZTreeVo);

			UacGroup query = new UacGroup();
			query.setPid(group.getId());

			List<UacGroup> groupChildrenList = uacGroupMapper.select(query);
			// 有子节点 递归查询
			if (PublicUtil.isNotEmpty(groupChildrenList)) {
				buildNode(groupChildrenList, tree);
			}

		}
		return tree;
	}
}
