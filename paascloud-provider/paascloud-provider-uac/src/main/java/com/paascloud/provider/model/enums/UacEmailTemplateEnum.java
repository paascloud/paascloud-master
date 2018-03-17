package com.paascloud.provider.model.enums;

/**
 * The enum Uac email template enum.
 *
 * @author paascloud.net@gmail.com
 */
public enum UacEmailTemplateEnum {

	/**
	 * 重置密码-获取验证码
	 */
	RESET_PWD_GET_CODE("RESET_PWD_CODE", "重置密码-获取验证码", "email/sendRestPwdCodeTemplate.ftl"),
	/**
	 * 忘记密码-等待重置密码.
	 */
	RESET_PWD_SEND_MAIL("RESET_PWD_CODE", "忘记密码-等待重置密码", "email/sendRestLoginPwdTemplate.ftl"),
	/**
	 * 注册用户-等待激活用户.
	 */
	ACTIVE_USER("ACTIVE_USER", "注册用户-等待激活用户", "email/activeUserTemplate.ftl"),
	/**
	 * 注册用户-激活用户成功.
	 */
	ACTIVE_USER_SUCCESS("ACTIVE_USER_SUCCESS", "注册用户-激活用户成功", "email/activeUserSuccessTemplate.ftl"),
	/**
	 * 忘记密码-重置密码
	 */
	RESET_LOGIN_PWD("RESET_LOGIN_PWD", "忘记密码-重置密码", "email/sendRestLoginPwdSuccessTemplate.ftl"),
	/**
	 * 忘记密码-邮件验证码
	 */
	RESET_USER_EMAIL("RESET_USER_EMAIL", "修改邮箱-邮件验证码", "email/sendRestUserEmailTemplate.ftl"),;

	/**
	 * The Key.
	 */
	String key;
	/**
	 * The Subject.
	 */
	String subject;
	/**
	 * The Location.
	 */
	String location;

	UacEmailTemplateEnum(String key, String subject, String location) {
		this.key = key;
		this.subject = subject;
		this.location = location;
	}

	/**
	 * Gets key.
	 *
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Gets location.
	 *
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Gets subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

}
