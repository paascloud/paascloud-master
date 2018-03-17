package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.paascloud.HttpAesUtil;
import com.paascloud.PubUtils;
import com.paascloud.RandomUtil;
import com.paascloud.RedisKeyUtil;
import com.paascloud.base.constant.AliyunMqTopicConstants;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.generator.UniqueIdGenerator;
import com.paascloud.provider.manager.UserManager;
import com.paascloud.provider.model.domain.MqMessageData;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.email.SendEmailMessage;
import com.paascloud.provider.model.enums.UacEmailTemplateEnum;
import com.paascloud.provider.model.exceptions.UacBizException;
import com.paascloud.provider.mq.producer.EmailProducer;
import com.paascloud.provider.service.EmailService;
import com.paascloud.provider.service.RedisService;
import com.paascloud.provider.service.UacUserService;
import com.xiaoleilu.hutool.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The class Email service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
	@Resource
	private EmailProducer emailProducer;
	@Resource
	private UacUserService uacUserService;
	@Value("${paascloud.auth.rest-pwd-url}")
	private String resetPwdUrl;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Resource
	private UserManager userManager;
	@Resource
	private RedisService redisService;

	private static final String KEY_STR = "om8q6fq#A0Yl@qJy";
	private static final String IV_STR = "0#86gzOcsv1bXyIx";

	@Override
	public void submitResetPwdEmail(String email) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(email), ErrorCodeEnum.UAC10011018.msg());

		// 获取用户名
		UacUser uacUser = new UacUser();
		uacUser.setEmail(email);
		uacUser = uacUserService.selectOne(uacUser);
		if (uacUser == null) {
			throw new UacBizException(ErrorCodeEnum.UAC10011004, email);
		}

		String resetPwdKey = PubUtils.uuid() + UniqueIdGenerator.generateId();
		redisTemplate.opsForValue().set(RedisKeyUtil.getResetPwdTokenKey(resetPwdKey), uacUser, 7 * 24, TimeUnit.HOURS);

		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", uacUser.getLoginName());
		param.put("email", email);
		param.put("resetPwdUrl", resetPwdUrl + resetPwdKey);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(email);
		MqMessageData messageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.RESET_PWD_SEND_MAIL, AliyunMqTopicConstants.MqTagEnum.FORGOT_PASSWORD_AUTH_CODE, param);
		userManager.submitResetPwdEmail(messageData);
	}

	@Override
	public void sendEmailCode(SendEmailMessage sendEmailMessage, String loginName) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), "用户名不能为空");
		String email = sendEmailMessage.getEmail();

		Preconditions.checkArgument(StringUtils.isNotEmpty(email), ErrorCodeEnum.UAC10011018.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(loginName), ErrorCodeEnum.UAC10011007.msg());

		// 解密
		email = decryptEmail(loginName, email);

		Example example = new Example(UacUser.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("email", email);
		criteria.andNotEqualTo("loginName", loginName);
		int result = uacUserService.selectCountByExample(example);
		if (result > 0) {
			throw new UacBizException(ErrorCodeEnum.UAC10011019);
		}

		String emailCode = RandomUtil.createNumberCode(6);
		String key = RedisKeyUtil.getSendEmailCodeKey(loginName, email);
		// 在redis中绑定验证码
		redisService.setKey(key, emailCode, 7 * 24, TimeUnit.HOURS);

		// 先写死 类型多了再抽方法
		Map<String, Object> param = Maps.newHashMap();
		param.put("loginName", loginName);
		param.put("email", email);
		param.put("emailCode", emailCode);
		param.put("dateTime", DateUtil.formatDateTime(new Date()));

		Set<String> to = Sets.newHashSet();
		to.add(email);

		MqMessageData mqMessageData = emailProducer.sendEmailMq(to, UacEmailTemplateEnum.RESET_USER_EMAIL, AliyunMqTopicConstants.MqTagEnum.RESET_LOGIN_PWD, param);
		userManager.sendEmailCode(mqMessageData);
	}

	@Override
	public void checkEmailCode(SendEmailMessage sendEmailMessage, String loginName) {

		String email = sendEmailMessage.getEmail();
		String emailCode = sendEmailMessage.getEmailCode();

		Preconditions.checkArgument(StringUtils.isNotEmpty(email), ErrorCodeEnum.UAC10011018.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(emailCode), "验证码不能为空");

		// 解密用户名密码
		email = decryptEmail(loginName, email);
		String key = RedisKeyUtil.getSendEmailCodeKey(loginName, email);
		String emailCodeRedis = redisService.getKey(key);
		Preconditions.checkArgument(StringUtils.isNotEmpty(emailCodeRedis), "验证码已过期");
		Preconditions.checkArgument(StringUtils.equals(emailCode, emailCodeRedis), "验证码错误");
	}

	private String decryptEmail(final String loginName, String email) {
		try {
			email = HttpAesUtil.decrypt(email, KEY_STR, false, IV_STR);
			log.info("发送短信 解密loginName={}", loginName);
			log.info("发送短信 解密email={}", email);
		} catch (Exception ex) {
			log.info("发送短信 解密手机号码失败 密文loginName={}, email={}", loginName, email);
			throw new UacBizException(ErrorCodeEnum.UAC10011031);
		}
		return email;
	}
}
