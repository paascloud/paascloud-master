package com.paascloud.provider.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.paascloud.*;
import com.paascloud.base.constant.AliyunMqTopicConstants;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.enums.LogTypeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.core.utils.RequestUtil;
import com.paascloud.provider.manager.UserManager;
import com.paascloud.provider.mapper.UacActionMapper;
import com.paascloud.provider.mapper.UacMenuMapper;
import com.paascloud.provider.mapper.UacUserMapper;
import com.paascloud.provider.mapper.UacUserMenuMapper;
import com.paascloud.provider.model.domain.*;
import com.paascloud.provider.model.dto.menu.UserMenuChildrenDto;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.enums.UacEmailTemplateEnum;
import com.paascloud.provider.model.enums.UacUserSourceEnum;
import com.paascloud.provider.model.enums.UacUserStatusEnum;
import com.paascloud.provider.model.enums.UacUserTypeEnum;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.model.vo.UserBindRoleVo;
import com.paascloud.provider.mq.producer.EmailProducer;
import com.paascloud.provider.service.*;
import com.paascloud.provider.utils.Md5Util;
import com.paascloud.security.core.SecurityUser;
import com.xiaoleilu.hutool.date.DateUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * The class Uac user service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserServiceImpl extends BaseService<UacUser> implements UacUserService {
	@Resource
	private UacUserMapper uacUserMapper;
	@Resource
	private UacMenuService uacMenuService;
	@Resource
	private UacActionMapper uacActionMapper;
	@Resource
	private UacMenuMapper uacMenuMapper;
	@Resource
	private UacGroupUserService uacGroupUserService;
	@Resource
	private UacLogService uacLogService;
	@Resource
	private UacRoleService uacRoleService;
	@Resource
	private UacRoleUserService uacRoleUserService;
	@Resource
	private UacUserMenuMapper uacUserMenuMapper;
	@Resource
	private UacUserMenuService uacUserMenuService;
	@Resource
	private RedisService redisService;
	@Resource
	private EmailProducer emailProducer;
	@Value("${paascloud.auth.active-user-url}")
	private String activeUserUrl;
	@Resource
	private UacActionService uacActionService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private TaskExecutor taskExecutor;
	@Resource
	private UacUserTokenService uacUserTokenService;
	@Resource
	private OpcRpcService opcRpcService;
	@Resource
	private UserManager userManager;

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacUser findByLoginName(String loginName) {
		logger.info("findByLoginName - 根据用户名查询用户信息. loginName={}", loginName);

		return uacUserMapper.findByLoginName(loginName);
	}

	@Override
	public UacUser findByMobileNo(String mobileNo) {
		return uacUserMapper.findByMobileNo(mobileNo);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public void checkUserIsCorrect(LoginReqDto loginReqDto) {
		logger.info("用户【" + loginReqDto.getLoginName() + "】进行密码认证......");

		Map<String, String> loginNamePwdMap = Maps.newHashMap();
		loginNamePwdMap.put("loginName", loginReqDto.getLoginName());
		loginNamePwdMap.put("loginPwd", loginReqDto.getLoginPwd());

		UacUser uacUser = uacUserMapper.findByLoginNameAndLoginPwd(loginNamePwdMap);
		if (PublicUtil.isEmpty(uacUser)) {
			logger.info("用户【" + loginReqDto.getLoginName() + "】密码认证失败");
			throw new UacBizException(ErrorCodeEnum.UAC10011002, loginReqDto.getLoginName());
		}

		logger.info("用户【" + loginReqDto.getLoginName() + "】密码认证成功");
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<Perm> getAllPerms() {
		logger.info("获取全部的权限...");
		return uacActionMapper.findAllPerms();
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<String> getUserPerms(Long userId) {
		logger.info("获取用户权限列表userId={}", userId);

		//1.获取所有菜单权限
		List<String> menuPermList = uacMenuMapper.findMenuCodeListByUserId(userId);
		//2.获取所有的按钮权限
		List<String> buttonPermList = uacActionMapper.findActionCodeListByUserId(userId);
		//3.将菜单权限和按钮权限汇总去重返回
		Set<String> set = Sets.newHashSet();
		set.addAll(menuPermList);
		set.addAll(buttonPermList);
		return new ArrayList<>(set);
	}

	@Override
	public int updateUser(UacUser uacUser) {
		logger.info("更新用户信息 uacUser={}", uacUser);
		int updateResult = uacUserMapper.updateByPrimaryKeySelective(uacUser);
		if (updateResult < 1) {
			logger.info("用户【 {} 】修改用户信息失败", uacUser.getId());
		} else {
			logger.info("用户【 {} 】修改用户信息成功", uacUser.getId());
		}
		return updateResult;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public PageInfo queryUserListWithPage(UacUser uacUser) {
		PageHelper.startPage(uacUser.getPageNum(), uacUser.getPageSize());
		uacUser.setOrderBy("u.update_time desc");
		List<UacUser> uacUserList = uacUserMapper.selectUserList(uacUser);
		return new PageInfo<>(uacUserList);
	}

	@Override
	public int deleteUserById(Long userId) {
		return 0;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacUser findUserInfoByUserId(Long userId) {
		return uacUserMapper.selectUserInfoByUserId(userId);
	}

	@Override
	public void saveUacUser(UacUser user, LoginAuthDto loginAuthDto) {

		String loginName = user.getLoginName();

		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), "用户名不能为空");
		Preconditions.checkArgument(user.getGroupId() != null, "组织不能为空");

		user.setUpdateInfo(loginAuthDto);
		if (user.isNew()) {
			String loginPwd = user.getLoginPwd();
			Preconditions.checkArgument(!StringUtils.isEmpty(loginPwd), ErrorCodeEnum.UAC10011014.msg());

			user.setLoginPwd(Md5Util.encrypt(loginPwd));
			// 验证用户名是否存在
			UacUser query = new UacUser();
			query.setLoginName(loginName);
			int count = uacUserMapper.selectCount(query);
			if (count > 0) {
				throw new UacBizException(ErrorCodeEnum.UAC10011025, loginName);
			}
			Long userId = super.generateId();
			// 1.保存用户信息
			user.setId(userId);
			user.setLoginName(loginName);
			user.setType(UacUserTypeEnum.OPERATE.getKey());
			user.setUserSource(UacUserSourceEnum.INSERT.getKey());
			// TODO 校验状态是否合法
			uacUserMapper.insertSelective(user);

			// 2.添加组织关联
			UacGroupUser groupUser = new UacGroupUser();
			groupUser.setGroupId(user.getGroupId());
			groupUser.setUserId(userId);
			uacGroupUserService.save(groupUser);
		} else {
			UacUser uacUser = uacUserMapper.selectByPrimaryKey(user.getId());
			Preconditions.checkArgument(uacUser != null, "用户不存在");
			// 1.更新用户信息
			int updateInt = uacUserMapper.updateUacUser(user);
			if (updateInt < 1) {
				throw new UacBizException(ErrorCodeEnum.UAC10011026, user.getId());
			}
			// 2.绑定组织信息
			UacGroupUser uacGroupUser = uacGroupUserService.queryByUserId(user.getId());
			if (uacGroupUser == null) {
				// 添加组织关联
				UacGroupUser groupUser = new UacGroupUser();
				groupUser.setGroupId(user.getGroupId());
				groupUser.setUserId(user.getId());
				uacGroupUserService.save(groupUser);
			} else {
				//修改组织
				UacGroupUser groupUser = new UacGroupUser();
				groupUser.setUserId(user.getId());
				groupUser.setGroupId(user.getGroupId());
				uacGroupUserService.updateByUserId(groupUser);
			}
		}

	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UacLog> queryUserLogListWithUserId(Long userId) {
		if (PublicUtil.isEmpty(userId)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		return uacLogService.selectUserLogListByUserId(userId);
	}

	@Override
	public int modifyUserStatusById(UacUser uacUser, LoginAuthDto authResDto) {
		Long loginUserId = authResDto.getUserId();
		Long userId = uacUser.getId();
		if (loginUserId.equals(userId)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}
		UacUser u = uacUserMapper.selectByPrimaryKey(userId);
		if (u == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011011, userId);
		}

		// 更新用户最后修改人与修改时间
		uacUser.setVersion(u.getVersion() + 1);
		uacUser.setUpdateInfo(authResDto);
		return uacUserMapper.updateByPrimaryKeySelective(uacUser);
	}

	@Override
	public void bindUserRoles(BindUserRolesDto bindUserRolesDto, LoginAuthDto authResDto) {

		if (bindUserRolesDto == null) {
			logger.error("参数不能为空");
			throw new IllegalArgumentException("参数不能为空");
		}

		Long operUserId = bindUserRolesDto.getUserId();
		Long loginUserId = authResDto.getUserId();
		List<Long> roleIdList = bindUserRolesDto.getRoleIdList();

		if (null == operUserId) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}

		// 任何用户不能操作admin用户
		if (Objects.equals(operUserId, GlobalConstant.Sys.SUPER_MANAGER_USER_ID)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011034);
		}

		UacUser user = this.queryByUserId(operUserId);

		if (user == null) {
			logger.error("找不到用户信息 operUserId={}", operUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011003, operUserId);
		}

		if (PublicUtil.isNotEmpty(roleIdList) && roleIdList.contains(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID)) {
			logger.error("操作超级管理员角色 userId={}", loginUserId);
			throw new UacBizException(ErrorCodeEnum.UAC10011023);
		}

		// 1. 先取消对该角色的用户绑定(不包含超级管理员用户)
		List<UacRoleUser> userRoles = uacRoleUserService.listByUserId(operUserId);

		if (PublicUtil.isNotEmpty(userRoles)) {
			uacRoleUserService.deleteByUserId(operUserId);
		}

		// 更新用户的操作时间
		final UacUser updateUser = new UacUser();
		updateUser.setId(operUserId);
		updateUser.setUpdateInfo(authResDto);
		uacUserMapper.updateUacUser(updateUser);

		if (PublicUtil.isEmpty(roleIdList)) {
			// 取消该角色的所有用户的绑定
			logger.info("绑定角色成功");
			return;
		}

		// 绑定所选用户
		for (Long roleId : roleIdList) {
			UacRole uacRole = uacRoleService.getRoleById(roleId);
			if (uacRole == null) {
				logger.error("找不到绑定的角色. roleId={}", roleId);
				throw new UacBizException(ErrorCodeEnum.UAC10012008, roleId);
			}
			uacRoleUserService.saveRoleUser(operUserId, roleId);
		}
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<UserMenuDto> queryUserMenuDtoData(LoginAuthDto authResDto) {
		// 返回的结果集
		List<UserMenuDto> list = Lists.newArrayList();
		List<MenuVo> menuList; // 该用户下所有的菜单集合
		Long userId = authResDto.getUserId();
		List<Long> ownerMenuIdList = Lists.newArrayList();
		Preconditions.checkArgument(!PubUtils.isNull(authResDto, userId), "无访问权限");

		// 查询该用户下所有的菜单Id集合
		UacUserMenu query = new UacUserMenu();
		query.setUserId(userId);
		List<UacUserMenu> userMenus = uacUserMenuMapper.select(query);
		for (UacUserMenu userMenu : userMenus) {
			if (PublicUtil.isEmpty(userMenu.getUserId())) {
				continue;
			}
			ownerMenuIdList.add(userMenu.getMenuId());
		}


		//判断是否为admin用户 如果是则返回所有菜单, 如果不是走下面流程
		menuList = uacMenuService.getMenuVoList(userId, 1L);
		for (MenuVo menuVo : menuList) {
			// 一级菜单
			UserMenuDto userMenuDto = new UserMenuDto();
			userMenuDto.setFistMenuIcon(menuVo.getIcon());
			userMenuDto.setFistMenuName(menuVo.getMenuName());
			List<MenuVo> sub2Menu = menuVo.getSubMenu();
			List<UserMenuChildrenDto> children = Lists.newArrayList();
			for (MenuVo vo2 : sub2Menu) {
				// 二级菜单 如果没有子节直接放入
				if (!vo2.isHasMenu()) {
					UserMenuChildrenDto userMenuChildrenDto = new UserMenuChildrenDto(vo2);
					//判断是否含有该菜单, 含有为true, 不含有为false
					if (ownerMenuIdList.contains(vo2.getId())) {
						userMenuChildrenDto.setChecked(true);
					}
					children.add(userMenuChildrenDto);
					continue;
				}
				// 如果二级节点有子节继续循环三级菜单
				List<MenuVo> sub3Menu = vo2.getSubMenu();
				for (MenuVo vo3 : sub3Menu) {
					UserMenuChildrenDto dto = new UserMenuChildrenDto(vo3);
					//判断是否含有该菜单, 含有为true, 不含有为false
					if (ownerMenuIdList.contains(vo3.getId())) {
						dto.setChecked(true);
					}
					children.add(dto);
				}
			}
			userMenuDto.setChildren(children);
			list.add(userMenuDto);
		}

		return list;
	}

	@Override
	public int bindUserMenus(List<Long> menuIdList, LoginAuthDto authResDto) {
		// 1.1 如果menuIdList is null 则清空中间表
		// 1.2 判断越权操作(是否为该用户所属角色的菜单)
		// 1.3 判断该记录是否为子节点,如果不是子节点则不允许操作
		// 2 操作数据,先删除再插入, 这里主要考虑避免角色授权和用户修改角色要操作用户菜单中间表,在显示上做过滤,然后再操作中物理删除中间表信息,坐到数据一致性
		Long userId = authResDto.getUserId();
		List<UacUserMenu> uacUserMenuList = Lists.newArrayList();

		UacUserMenu uacUserMenu = new UacUserMenu();
		uacUserMenu.setUserId(userId);

		if (PublicUtil.isEmpty(menuIdList)) {
			return deleteUserMenuList(uacUserMenu);
		}

		// 校验数据
		this.checkUserMenuList(menuIdList, authResDto, uacUserMenuList);

		// 操作数据库
		return handleUserMenuList(uacUserMenuList, uacUserMenu);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UacUser queryByUserId(Long userId) {
		logger.info("queryByUserId - 根据用户查询用户信息接口. userId={}", userId);
		UacUser uacUser = uacUserMapper.selectByPrimaryKey(userId);
		if (PublicUtil.isNotEmpty(uacUser)) {
			uacUser.setLoginPwd("");
		}
		return uacUser;
	}

	@Override
	public int userModifyPwd(UserModifyPwdDto userModifyPwdDto, LoginAuthDto authResDto) {
		String loginName = userModifyPwdDto.getLoginName();
		String oldPassword = userModifyPwdDto.getOldPassword();
		String newPassword = userModifyPwdDto.getNewPassword();
		String confirmPwd = userModifyPwdDto.getConfirmPwd();

		Preconditions.checkArgument(!PublicUtil.isEmpty(loginName), ErrorCodeEnum.UAC10011007.msg());
		Preconditions.checkArgument(!PublicUtil.isEmpty(oldPassword), "原始密码不能为空");
		Preconditions.checkArgument(!PublicUtil.isEmpty(newPassword), "新密码不能为空");
		Preconditions.checkArgument(!PublicUtil.isEmpty(confirmPwd), ErrorCodeEnum.UAC10011009.msg());
		Preconditions.checkArgument(newPassword.equals(confirmPwd), "两次密码不一致, 请重新输入！");


		UacUser user = uacUserMapper.findByLoginName(loginName);
		if (PublicUtil.isEmpty(user)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011002, loginName);
		}

		String oldPwd = user.getLoginPwd();
		String newEncrypt = Md5Util.encrypt(newPassword);

		if (!Md5Util.matches(oldPassword, oldPwd)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011035);
		}

		UacUser uacUser = new UacUser();
		if (Md5Util.matches(newPassword, oldPwd)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011036);
		}

		uacUser.setLoginPwd(Md5Util.encrypt(newPassword));
		uacUser.setId(user.getId());
		uacUser.setLoginPwd(newEncrypt);
		// 该用户已经修改过密码
		uacUser.setIsChangedPwd(Short.valueOf("1"));
		uacUser.setUpdateInfo(authResDto);

		return uacUserMapper.updateByPrimaryKeySelective(uacUser);

		// TODO 发送重置密码成功的邮件
	}

	@Override
	public int userResetPwd(UserResetPwdDto userResetPwdDto) {
		String mobileNo = userResetPwdDto.getMobileNo();
		String newPassword = userResetPwdDto.getNewPassword();
		String confirmPwd = userResetPwdDto.getConfirmPwd();

		Preconditions.checkArgument(!StringUtils.isEmpty(mobileNo), "手机号码不能为空");
		Preconditions.checkArgument(!StringUtils.isEmpty(newPassword), "新密码不能为空");
		Preconditions.checkArgument(!StringUtils.isEmpty(confirmPwd), ErrorCodeEnum.UAC10011009.msg());
		Preconditions.checkArgument(newPassword.equals(confirmPwd), "两次密码不一致");

		UacUser query = new UacUser();
		query.setMobileNo(mobileNo);
		UacUser user = uacUserMapper.selectOne(query);
		if (user == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011027, mobileNo);
		}

		UacUser uacUser = new UacUser();
		uacUser.setLoginPwd(Md5Util.encrypt(newPassword));
		uacUser.setId(user.getId());

		// 更新用户最后修改人与修改时间
		uacUser.setVersion(user.getVersion() + 1);
		uacUser.setLastOperator(user.getLoginName());
		uacUser.setLastOperatorId(user.getId());
		uacUser.setUpdateTime(new Date());

		return uacUserMapper.updateByPrimaryKeySelective(uacUser);
	}

	@Override
	public void register(UserRegisterDto registerDto) {
		// 校验注册信息
		validateRegisterInfo(registerDto);
		String mobileNo = registerDto.getMobileNo();
		String email = registerDto.getEmail();
		Date row = new Date();
		String salt = String.valueOf(generateId());
		// 封装注册信息
		long id = generateId();
		UacUser uacUser = new UacUser();
		uacUser.setLoginName(registerDto.getLoginName());
		uacUser.setSalt(salt);
		uacUser.setLoginPwd(Md5Util.encrypt(registerDto.getLoginPwd()));
		uacUser.setMobileNo(mobileNo);
		uacUser.setStatus(UacUserStatusEnum.DISABLE.getKey());
		uacUser.setUserSource(UacUserSourceEnum.REGISTER.getKey());
		uacUser.setCreatedTime(row);
		uacUser.setUpdateTime(row);
		uacUser.setEmail(email);
		uacUser.setId(id);
		uacUser.setCreatorId(id);
		uacUser.setCreator(registerDto.getLoginName());
		uacUser.setLastOperatorId(id);
		uacUser.setUserName(registerDto.getLoginName());
		uacUser.setLastOperator(registerDto.getLoginName());

		// 发送激活邮件
		String activeToken = PubUtils.uuid() + super.generateId();
		redisService.setKey(RedisKeyUtil.getActiveUserKey(activeToken), email, 1, TimeUnit.DAYS);

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", registerDto.getLoginName());
		param.put("email", registerDto.getEmail());
		param.put("activeUserUrl", activeUserUrl + activeToken);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(registerDto.getEmail());

		MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.ACTIVE_USER, AliyunMqTopicConstants.MqTagEnum.ACTIVE_USER, param);
		userManager.register(mqMessageData, uacUser);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkLoginName(String loginName) {
		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), ErrorCodeEnum.UAC10011007.msg());

		UacUser uacUser = new UacUser();
		uacUser.setLoginName(loginName);
		int result = 1;
		try {
			result = uacUserMapper.selectCount(uacUser);
		} catch (Exception e) {
			logger.error(" 验证用户名是否存在,出现异常={}", e.getMessage(), e);
		}
		return result < 1;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkEmail(String email) {
		Preconditions.checkArgument(!StringUtils.isEmpty(email), "email不能为空");

		UacUser uacUser = new UacUser();
		uacUser.setEmail(email);
		int result = 1;
		try {
			result = uacUserMapper.selectCount(uacUser);
		} catch (Exception e) {
			logger.error(" 验证email是否存在,出现异常={}", e.getMessage(), e);
		}
		return result < 1;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public boolean checkMobileNo(String mobileNo) {
		Preconditions.checkArgument(!StringUtils.isEmpty(mobileNo), "手机号码不能为空");

		UacUser uacUser = new UacUser();
		uacUser.setMobileNo(mobileNo);
		int result = 1;
		try {
			result = uacUserMapper.selectCount(uacUser);
		} catch (Exception e) {
			logger.error(" 验证email是否存在,出现异常={}", e.getMessage(), e);
		}
		return result < 1;
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public int countUserByLoginNameAndEmail(String loginName, String email) {
		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), ErrorCodeEnum.UAC10011007.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(email), ErrorCodeEnum.UAC10011018.msg());

		UacUser uacUser = new UacUser();
		uacUser.setLoginName(loginName);
		uacUser.setEmail(email);

		return uacUserMapper.selectCount(uacUser);
	}

	@Override
	public int userEmailResetPwd(ForgetResetPasswordDto forgetResetPasswordDto) {
		this.validateEmailResetPwd(forgetResetPasswordDto);
		String loginName = forgetResetPasswordDto.getLoginName();

		UacUser user = this.findByLoginName(loginName);
		UacUser uacUser = new UacUser();
		uacUser.setLoginPwd(Md5Util.encrypt(forgetResetPasswordDto.getLoginPwd()));
		uacUser.setId(user.getId());
		LoginAuthDto authUser = new LoginAuthDto();
		authUser.setLoginName(loginName);
		authUser.setUserId(user.getId());
		authUser.setUserName(user.getUserName());
		uacUser.setUpdateInfo(authUser);

		return uacUserMapper.updateByPrimaryKeySelective(uacUser);


	}

	@Override
	public void modifyUserEmail(String email, String emailCode, LoginAuthDto loginAuthDto) {
		// 校验用户信息
		Preconditions.checkArgument(!StringUtils.isEmpty(email), ErrorCodeEnum.UAC10011018.msg());
		Preconditions.checkArgument(PubUtils.isEmail(email), "邮箱格式不正确");

		UacUser uacUser = new UacUser();
		uacUser.setId(loginAuthDto.getUserId());
		uacUser.setEmail(email);
		uacUser.setUpdateInfo(loginAuthDto);
		int result = this.updateUser(uacUser);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011037, loginAuthDto.getUserId());
		}
	}

	@Override
	public void resetLoginPwd(Long userId, LoginAuthDto loginAuthDto) {
		if (userId == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011001);
		}
		UacUser uacUser = this.queryByUserId(userId);
		if (uacUser == null) {
			logger.error("找不到用户. userId={}", userId);
			throw new UacBizException(ErrorCodeEnum.UAC10011003, userId);
		}

		Preconditions.checkArgument(!StringUtils.isEmpty(uacUser.getEmail()), "邮箱地址不能为空");

		String newLoginPwd = RandomUtil.createComplexCode(8);
		UacUser update = new UacUser();
		update.setId(uacUser.getId());
		update.setLoginPwd(Md5Util.encrypt(newLoginPwd));
		short isChangedPwd = 0;
		update.setIsChangedPwd(isChangedPwd);

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", uacUser.getLoginName());
		param.put("newLoginPwd", newLoginPwd);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(uacUser.getEmail());

		final MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.RESET_LOGIN_PWD, AliyunMqTopicConstants.MqTagEnum.RESET_LOGIN_PWD, param);
		userManager.resetLoginPwd(mqMessageData, update);


	}

	@Override
	public void resetLoginPwd(ResetLoginPwdDto resetLoginPwdDto) {
		String confirmPwd = resetLoginPwdDto.getConfirmPwd();
		String newPassword = resetLoginPwdDto.getNewPassword();
		String resetPwdKey = resetLoginPwdDto.getResetPwdKey();

		Preconditions.checkArgument(!StringUtils.isEmpty(newPassword), ErrorCodeEnum.UAC10011014.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(confirmPwd), ErrorCodeEnum.UAC10011009.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(resetPwdKey), "链接已失效");
		Preconditions.checkArgument(newPassword.equals(confirmPwd), "两次输入密码不一致");

		String resetPwdTokenKey = RedisKeyUtil.getResetPwdTokenKey(resetPwdKey);
		UacUser uacUser = (UacUser) redisTemplate.opsForValue().get(resetPwdTokenKey);

		if (StringUtils.isEmpty(uacUser)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011028);
		}

		LoginAuthDto loginAuthDto = new LoginAuthDto();
		loginAuthDto.setUserName(uacUser.getUserName());
		loginAuthDto.setLoginName(uacUser.getLoginName());
		loginAuthDto.setUserId(uacUser.getId());

		UacUser update = new UacUser();
		String salt = generateId() + "";
		update.setLoginPwd(Md5Util.encrypt(newPassword));
		update.setSalt(salt);
		update.setId(uacUser.getId());
		// 该用户已经修改过密码
		update.setIsChangedPwd((Short.valueOf("1")));
		update.setUpdateInfo(loginAuthDto);

		int result = uacUserMapper.updateByPrimaryKeySelective(update);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011029);
		}
		redisTemplate.delete(resetPwdTokenKey);
	}

	@Override
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public UserBindRoleVo getUserBindRoleDto(Long userId) {
		UserBindRoleVo userBindRoleVo = new UserBindRoleVo();
		Set<Long> alreadyBindRoleIdSet = Sets.newHashSet();
		UacUser uacUser = this.queryByUserId(userId);
		if (uacUser == null) {
			logger.error("找不到userId={}, 的用户", userId);
			throw new UacBizException(ErrorCodeEnum.UAC10011003, userId);
		}

		// 查询所有角色包括该用户拥有的角色
		List<BindRoleDto> bindRoleDtoList = uacUserMapper.selectAllNeedBindRole(GlobalConstant.Sys.SUPER_MANAGER_ROLE_ID);
		// 该角色已经绑定的用户
		List<UacRoleUser> setAlreadyBindRoleSet = uacRoleUserService.listByUserId(userId);

		Set<BindRoleDto> allUserSet = new HashSet<>(bindRoleDtoList);

		for (UacRoleUser uacRoleUser : setAlreadyBindRoleSet) {
			alreadyBindRoleIdSet.add(uacRoleUser.getRoleId());
		}

		userBindRoleVo.setAllRoleSet(allUserSet);
		userBindRoleVo.setAlreadyBindRoleIdSet(alreadyBindRoleIdSet);

		return userBindRoleVo;
	}

	@Override
	public void activeUser(String activeUserToken) {
		Preconditions.checkArgument(!StringUtils.isEmpty(activeUserToken), "激活用户失败");

		String activeUserKey = RedisKeyUtil.getActiveUserKey(activeUserToken);

		String email = redisService.getKey(activeUserKey);

		if (StringUtils.isEmpty(email)) {
			throw new UacBizException(ErrorCodeEnum.UAC10011030);
		}
		// 修改用户状态, 绑定访客角色
		UacUser uacUser = new UacUser();
		uacUser.setEmail(email);

		uacUser = uacUserMapper.selectOne(uacUser);
		if (uacUser == null) {
			logger.error("找不到用户信息. email={}", email);
			throw new UacBizException(ErrorCodeEnum.UAC10011004, email);
		}

		UacUser update = new UacUser();
		update.setId(uacUser.getId());
		update.setStatus(UacUserStatusEnum.ENABLE.getKey());
		LoginAuthDto loginAuthDto = new LoginAuthDto();
		loginAuthDto.setUserId(uacUser.getId());
		loginAuthDto.setUserName(uacUser.getLoginName());
		loginAuthDto.setLoginName(uacUser.getLoginName());
		update.setUpdateInfo(loginAuthDto);

		UacUser user = this.queryByUserId(uacUser.getId());

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", user.getLoginName());
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(user.getEmail());


		MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.ACTIVE_USER_SUCCESS, AliyunMqTopicConstants.MqTagEnum.ACTIVE_USER_SUCCESS, param);
		userManager.activeUser(mqMessageData, update, activeUserKey);
	}

	@Override
	public Collection<GrantedAuthority> loadUserAuthorities(Long userId) {

		List<UacAction> ownAuthList = uacActionService.getOwnActionListByUserId(userId);
		List<GrantedAuthority> authList = Lists.newArrayList();
		for (UacAction action : ownAuthList) {
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(action.getUrl());
			authList.add(grantedAuthority);
		}
		return authList;
	}

	@Override
	public void handlerLoginData(OAuth2AccessToken token, final SecurityUser principal, HttpServletRequest request) {

		final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		//获取客户端操作系统
		final String os = userAgent.getOperatingSystem().getName();
		//获取客户端浏览器
		final String browser = userAgent.getBrowser().getName();
		final String remoteAddr = RequestUtil.getRemoteAddr(request);
		// 根据IP获取位置信息
		final String remoteLocation = opcRpcService.getLocationById(remoteAddr);
		final String requestURI = request.getRequestURI();

		UacUser uacUser = new UacUser();
		Long userId = principal.getUserId();
		uacUser.setLastLoginIp(remoteAddr);
		uacUser.setId(userId);
		uacUser.setLastLoginTime(new Date());
		uacUser.setLastLoginLocation(remoteLocation);
		LoginAuthDto loginAuthDto = new LoginAuthDto(userId, principal.getLoginName(), principal.getNickName(), principal.getGroupId(), principal.getGroupName());
		// 记录token日志
		String accessToken = token.getValue();
		String refreshToken = token.getRefreshToken().getValue();
		uacUserTokenService.saveUserToken(accessToken, refreshToken, loginAuthDto, request);
		// 记录最后登录信息
		taskExecutor.execute(() -> this.updateUser(uacUser));
		// 记录操作日志

		UacLog log = new UacLog();
		log.setGroupId(principal.getGroupId());
		log.setGroupName(principal.getGroupName());
		log.setIp(remoteAddr);
		log.setLocation(remoteLocation);
		log.setOs(os);
		log.setBrowser(browser);
		log.setRequestUrl(requestURI);
		log.setLogType(LogTypeEnum.LOGIN_LOG.getType());
		log.setLogName(LogTypeEnum.LOGIN_LOG.getName());

		taskExecutor.execute(() -> uacLogService.saveLog(log, loginAuthDto));
	}

	@Override
	public UacUser findUserInfoByLoginName(final String loginName) {
		return uacUserMapper.findUserInfoByLoginName(loginName);
	}

	private void validateEmailResetPwd(ForgetResetPasswordDto forgetResetPasswordDto) {
		//校验注册信息
		String forgetToken = forgetResetPasswordDto.getForgetToken();
		String loginPwd = forgetResetPasswordDto.getLoginPwd();
		String loginName = forgetResetPasswordDto.getLoginName();
		String email = forgetResetPasswordDto.getEmail();
		String emailCode = forgetResetPasswordDto.getEmailCode();

		Preconditions.checkArgument(!StringUtils.isEmpty(loginName), ErrorCodeEnum.UAC10011007.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(email), ErrorCodeEnum.UAC10011018.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(loginPwd), ErrorCodeEnum.UAC10011014.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(forgetToken), "非法操作");
		Preconditions.checkArgument(!StringUtils.isEmpty(emailCode), "验证码不能为空");

		// 验证token
		String key = RedisKeyUtil.getResetPwdTokenKey(email);
		String forgetKey = redisService.getKey(key);

		try {
			HttpAesUtil.decrypt(forgetToken, forgetKey, false, forgetKey);
		} catch (Exception e) {
			throw new UacBizException(ErrorCodeEnum.UAC10011031);
		}

		int count = this.countUserByLoginNameAndEmail(loginName, email);
		// 校验token
		if (count < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011032, loginName, email);
		}
	}

	/**
	 * 删除用户菜单表
	 */
	private int deleteUserMenuList(UacUserMenu uacUserMenu) {
		int selCount = uacUserMenuMapper.selectCount(uacUserMenu);
		// 如果查询结果为空, 默认认为已删除成功
		if (selCount < 1) {
			return 1;
		}

		int delCount = uacUserMenuMapper.delete(uacUserMenu);
		if (delCount < selCount) {
			logger.error("清空该用户常用菜单失败 delCount = {} selCount = {}", delCount, selCount);
			throw new UacBizException(ErrorCodeEnum.UAC10011033);
		}

		return delCount;
	}

	/**
	 * 校验数据是否合法
	 *
	 * @param menuIdList      需要操作的菜单Id集合
	 * @param authResDto      登录用户
	 * @param uacUserMenuList 需要插入的记录
	 */
	private void checkUserMenuList(List<Long> menuIdList, LoginAuthDto authResDto, List<UacUserMenu> uacUserMenuList) {

		List<MenuVo> currentUserMenuVoList = uacMenuService.findAllMenuListByAuthResDto(authResDto);
		List<Long> currentUserMenuIdList = Lists.newArrayList();
		for (MenuVo menuVo : currentUserMenuVoList) {
			Long menuId = menuVo.getId();

			if (PublicUtil.isEmpty(menuId)) {
				continue;
			}
			currentUserMenuIdList.add(menuId);
		}

		Preconditions.checkArgument(currentUserMenuIdList.containsAll(menuIdList), "参数异常");

		// TODO 预留一个过滤已失效菜单的接口
		for (Long menuId : menuIdList) {
			if (uacMenuService.checkMenuHasChildMenu(menuId)) {
				logger.error(" 选择菜单不是根目录 menuId= {}", menuId);
				throw new UacBizException(ErrorCodeEnum.UAC10013010, menuId);
			}
			UacUserMenu uacUserMenu = new UacUserMenu();
			uacUserMenu.setUserId(authResDto.getUserId());
			uacUserMenu.setMenuId(menuId);
			uacUserMenuList.add(uacUserMenu);
		}
	}

	private int handleUserMenuList(List<UacUserMenu> uacUserMenuList, UacUserMenu uacUserMenu) {
		// 如果存在记录则清空数据表
		deleteUserMenuList(uacUserMenu);

		return uacUserMenuService.batchSave(uacUserMenuList);
	}

	private void validateRegisterInfo(UserRegisterDto registerDto) {
		String mobileNo = registerDto.getMobileNo();

		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getLoginName()), ErrorCodeEnum.UAC10011007.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getEmail()), ErrorCodeEnum.UAC10011018.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(mobileNo), "手机号不能为空");
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getLoginPwd()), ErrorCodeEnum.UAC10011014.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getConfirmPwd()), ErrorCodeEnum.UAC10011009.msg());
		Preconditions.checkArgument(!StringUtils.isEmpty(registerDto.getRegisterSource()), "验证类型错误");
		Preconditions.checkArgument(registerDto.getLoginPwd().equals(registerDto.getConfirmPwd()), "两次密码不一致");

		UacUser uacUser = new UacUser();
		uacUser.setLoginName(registerDto.getLoginName());
		int count = uacUserMapper.selectCount(uacUser);
		if (count > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011012);
		}

		uacUser = new UacUser();
		uacUser.setMobileNo(registerDto.getMobileNo());
		count = uacUserMapper.selectCount(uacUser);
		if (count > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011013);
		}

		uacUser = new UacUser();
		uacUser.setEmail(registerDto.getEmail());
		count = uacUserMapper.selectCount(uacUser);
		if (count > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011019);
		}

	}
}
