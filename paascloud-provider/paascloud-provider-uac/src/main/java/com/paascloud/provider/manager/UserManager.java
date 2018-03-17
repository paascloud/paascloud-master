package com.paascloud.provider.manager;

import com.google.common.base.Preconditions;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.provider.annotation.MqProducerStore;
import com.paascloud.provider.mapper.UacGroupUserMapper;
import com.paascloud.provider.mapper.UacRoleUserMapper;
import com.paascloud.provider.mapper.UacUserMapper;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.domain.UacGroupUser;
import com.paascloud.provider.model.domain.UacRoleUser;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.enums.MqSendTypeEnum;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * The class User manager.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
@Component
@Transactional(rollbackFor = Exception.class)
public class UserManager {
	@Resource
	private UacUserMapper uacUserMapper;
	@Resource
	private UacRoleUserMapper uacRoleUserMapper;
	@Resource
	private UacGroupUserMapper uacGroupUserMapper;
	@Resource
	private RedisServiceImpl redisService;

	@MqProducerStore(sendType = MqSendTypeEnum.SAVE_AND_SEND)
	public void submitResetPwdEmail(final MqMessageData messageData) {
		log.info("重置密码发送邮件. messageData={}", messageData);
	}

	@MqProducerStore
	public void register(final MqMessageData mqMessageData, final UacUser uacUser) {
		log.info("注册用户. mqMessageData={}, user={}", mqMessageData, uacUser);
		uacUserMapper.insertSelective(uacUser);
	}

	@MqProducerStore
	public void resetLoginPwd(final MqMessageData mqMessageData, final UacUser update) {
		log.info("重置密码. mqMessageData={}, user={}", mqMessageData, update);
		int updateResult = uacUserMapper.updateByPrimaryKeySelective(update);
		if (updateResult < 1) {
			log.error("用户【 {} 】重置密码失败", update.getLoginName());
		} else {
			log.info("用户【 {} 】重置密码失败", update.getLoginName());
		}
	}

	@MqProducerStore
	public void activeUser(final MqMessageData mqMessageData, final UacUser uacUser, final String activeUserKey) {
		log.info("激活用户. mqMessageData={}, user={}", mqMessageData, uacUser);
		int result = uacUserMapper.updateByPrimaryKeySelective(uacUser);
		if (result < 1) {
			throw new UacBizException(ErrorCodeEnum.UAC10011038, uacUser.getId());
		}

		// 绑定一个访客角色默认值roleId=10000
		final Long userId = uacUser.getId();
		Preconditions.checkArgument(userId != null, "用戶Id不能爲空");

		final Long roleId = 10000L;

		UacRoleUser roleUser = new UacRoleUser();
		roleUser.setUserId(userId);
		roleUser.setRoleId(roleId);
		uacRoleUserMapper.insertSelective(roleUser);
		// 绑定一个组织
		UacGroupUser groupUser = new UacGroupUser();
		groupUser.setUserId(userId);
		groupUser.setGroupId(GlobalConstant.Sys.SUPER_MANAGER_GROUP_ID);
		uacGroupUserMapper.insertSelective(groupUser);
		// 删除 activeUserToken
		redisService.deleteKey(activeUserKey);
	}

	@MqProducerStore(sendType = MqSendTypeEnum.SAVE_AND_SEND)
	public void sendSmsCode(final MqMessageData mqMessageData) {
		log.info("发送短信验证码. mqMessageData={}", mqMessageData);
	}

	@MqProducerStore(sendType = MqSendTypeEnum.SAVE_AND_SEND)
	public void sendEmailCode(final MqMessageData mqMessageData) {
		log.info("发送邮件验证码. mqMessageData={}", mqMessageData);
	}
}
