package com.paascloud.provider.service;

import com.github.pagehelper.PageInfo;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.UacLog;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.menu.UserMenuDto;
import com.paascloud.provider.model.dto.user.*;
import com.paascloud.provider.model.vo.UserBindRoleVo;
import com.paascloud.security.core.SecurityUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * The interface Uac user service.
 *
 * @author paascloud.net @gmail.com
 */
public interface UacUserService extends IService<UacUser> {
	/**
	 * 根据登录名查询用户信息
	 *
	 * @param loginName the login name
	 *
	 * @return the uac user
	 */
	UacUser findByLoginName(String loginName);

	/**
	 * 根据手机号查询用户信息.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the uac user
	 */
	UacUser findByMobileNo(String mobileNo);

	/**
	 * 校验用户是否合法
	 *
	 * @param loginReqDto the login req dto
	 */
	void checkUserIsCorrect(LoginReqDto loginReqDto);

	/**
	 * 获得项目下所有的资源权限列表, 用于程序启动时的初始化工作
	 * String[0] = 资源
	 * String[1] = 权限
	 *
	 * @return all perms
	 */
	List<Perm> getAllPerms();

	/**
	 * 获得用户拥有的权限列表, 在首次验证用户对某个资源是否有权限时, 会调用此方法, 初始化用户权限
	 *
	 * @param userId the user id
	 *
	 * @return the user perms
	 */
	List<String> getUserPerms(Long userId);

	/**
	 * 更新用户信息
	 *
	 * @param uacUser the uac user
	 *
	 * @return the int
	 */
	int updateUser(UacUser uacUser);

	/**
	 * Query user list with page list.
	 *
	 * @param uacUser the uac user
	 *
	 * @return the list
	 */
	PageInfo queryUserListWithPage(UacUser uacUser);

	/**
	 * 根据用户ID删除用户.
	 *
	 * @param userId the user id
	 *
	 * @return the int
	 */
	int deleteUserById(Long userId);

	/**
	 * 根据用户ID查询用户信息.
	 *
	 * @param userId the user id
	 *
	 * @return the uac user
	 */
	UacUser findUserInfoByUserId(Long userId);

	/**
	 * 保存用户.
	 *
	 * @param user         the user
	 * @param loginAuthDto the login auth dto
	 */
	void saveUacUser(UacUser user, LoginAuthDto loginAuthDto);

	/**
	 * 根据用户ID查询用户日志集合.
	 *
	 * @param userId the user id
	 *
	 * @return the list
	 */
	List<UacLog> queryUserLogListWithUserId(Long userId);

	/**
	 * 根据用户ID修改用户状态.
	 *
	 * @param uacUser      the uac user
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int modifyUserStatusById(UacUser uacUser, LoginAuthDto loginAuthDto);

	/**
	 * 绑定用户角色信息.
	 *
	 * @param bindUserRolesDto the bind user roles dto
	 * @param loginAuthDto     the login auth dto
	 */
	void bindUserRoles(BindUserRolesDto bindUserRolesDto, LoginAuthDto loginAuthDto);

	/**
	 * 查询用户菜单.
	 *
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the list
	 */
	List<UserMenuDto> queryUserMenuDtoData(LoginAuthDto loginAuthDto);

	/**
	 * 用户绑定菜单.
	 *
	 * @param menuIdList   the menu id list
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the int
	 */
	int bindUserMenus(List<Long> menuIdList, LoginAuthDto loginAuthDto);

	/**
	 * 根据用户ID查询用户信息.
	 *
	 * @param userId the user id
	 *
	 * @return the uac user
	 */
	UacUser queryByUserId(Long userId);

	/**
	 * 用户修改密码
	 *
	 * @param userModifyPwdDto the user modify pwd dto
	 * @param authResDto       the auth res dto
	 *
	 * @return the int
	 */
	int userModifyPwd(UserModifyPwdDto userModifyPwdDto, LoginAuthDto authResDto);

	/**
	 * 用户忘记密码
	 *
	 * @param userResetPwdDto the user reset pwd dto
	 *
	 * @return the int
	 */
	int userResetPwd(UserResetPwdDto userResetPwdDto);

	/**
	 * 注册用户.
	 *
	 * @param registerDto the register dto
	 */
	void register(UserRegisterDto registerDto);

	/**
	 * 校验登录名是否存在.
	 *
	 * @param loginName the login name
	 *
	 * @return the boolean
	 */
	boolean checkLoginName(String loginName);

	/**
	 * 校验邮箱是否存在.
	 *
	 * @param loginName the login name
	 *
	 * @return the boolean
	 */
	boolean checkEmail(String loginName);

	/**
	 * 校验手机号是否存在.
	 *
	 * @param validValue the valid value
	 *
	 * @return the boolean
	 */
	boolean checkMobileNo(String validValue);

	/**
	 * 根据邮箱和登录名查询用户数量.
	 *
	 * @param loginName the login name
	 * @param email     the email
	 *
	 * @return the int
	 */
	int countUserByLoginNameAndEmail(String loginName, String email);

	/**
	 * 重置密码.
	 *
	 * @param forgetResetPasswordDto 忘记密码实体
	 *
	 * @return the int
	 */
	int userEmailResetPwd(ForgetResetPasswordDto forgetResetPasswordDto);

	/**
	 * 修改用户邮箱.
	 *
	 * @param email        the email
	 * @param emailCode    the email code
	 * @param loginAuthDto the login auth dto
	 */
	void modifyUserEmail(String email, String emailCode, LoginAuthDto loginAuthDto);

	/**
	 * 重置登录密码.
	 *
	 * @param userId       the user id
	 * @param loginAuthDto the login auth dto
	 */
	void resetLoginPwd(Long userId, LoginAuthDto loginAuthDto);

	/**
	 * 重置登录密码.
	 *
	 * @param resetLoginPwdDto the reset login pwd dto
	 */
	void resetLoginPwd(ResetLoginPwdDto resetLoginPwdDto);

	/**
	 * 获取绑定的角色信息.
	 *
	 * @param userId the user id
	 *
	 * @return the user bind role dto
	 */
	UserBindRoleVo getUserBindRoleDto(Long userId);

	/**
	 * 激活用户.
	 *
	 * @param activeUserToken the active user token
	 */
	void activeUser(String activeUserToken);

	/**
	 * 获取用户拥有的所有权限编码.
	 *
	 * @param userId the user id
	 *
	 * @return the collection
	 */
	Collection<GrantedAuthority> loadUserAuthorities(Long userId);

	/**
	 * Handler login data.
	 *
	 * @param token     the token
	 * @param principal the principal
	 * @param request   the request
	 */
	void handlerLoginData(OAuth2AccessToken token, final SecurityUser principal, final HttpServletRequest request);

	/**
	 * Find user info by login name uac user.
	 *
	 * @param loginName the login name
	 *
	 * @return the uac user
	 */
	UacUser findUserInfoByLoginName(String loginName);
}
