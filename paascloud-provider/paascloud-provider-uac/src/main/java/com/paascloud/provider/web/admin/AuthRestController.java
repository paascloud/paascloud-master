package com.paascloud.provider.web.admin;

import com.google.common.base.Preconditions;
import com.paascloud.base.dto.CheckValidDto;
import com.paascloud.core.annotation.OperationLogDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.constant.UacApiConstant;
import com.paascloud.provider.model.domain.UacUser;
import com.paascloud.provider.model.dto.user.ResetLoginPwdDto;
import com.paascloud.provider.model.dto.user.UserRegisterDto;
import com.paascloud.provider.model.enums.UacUserStatusEnum;
import com.paascloud.provider.service.EmailService;
import com.paascloud.provider.service.SmsService;
import com.paascloud.provider.service.UacLogService;
import com.paascloud.provider.service.UacUserService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 不认证的URL请求.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/auth")
@Api(value = "Web-AuthRestController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AuthRestController extends BaseController {
	@Resource
	private UacUserService uacUserService;
	@Resource
	private SmsService smsService;
	@Resource
	private EmailService emailService;
	@Resource
	private UacLogService uacLogService;

	/**
	 * 校验手机号码.
	 *
	 * @param mobileNo the mobile no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkPhoneActive/{mobileNo}")
	@ApiOperation(httpMethod = "POST", value = "校验手机号码")
	public Wrapper<Boolean> checkPhoneActive(@PathVariable String mobileNo) {
		UacUser uacUser = new UacUser();
		uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
		uacUser.setMobileNo(mobileNo);
		int count = uacUserService.selectCount(uacUser);
		return WrapMapper.ok(count > 0);
	}

	/**
	 * 校验邮箱.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkEmailActive/{email:.+}")
	@ApiOperation(httpMethod = "POST", value = "校验邮箱")
	public Wrapper<Boolean> checkEmailActive(@PathVariable("email") String email) {
		UacUser uacUser = new UacUser();
		uacUser.setStatus(UacUserStatusEnum.ENABLE.getKey());
		uacUser.setEmail(email);
		int count = uacUserService.selectCount(uacUser);
		return WrapMapper.ok(count > 0);
	}

	/**
	 * 校验数据.
	 *
	 * @param checkValidDto the check valid dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/checkValid")
	@ApiOperation(httpMethod = "POST", value = "校验数据")
	public Wrapper checkValid(@RequestBody CheckValidDto checkValidDto) {
		String type = checkValidDto.getType();
		String validValue = checkValidDto.getValidValue();

		Preconditions.checkArgument(StringUtils.isNotEmpty(validValue), "参数错误");
		String message = null;
		boolean result = false;
		//开始校验
		if (UacApiConstant.Valid.LOGIN_NAME.equals(type)) {
			result = uacUserService.checkLoginName(validValue);
			if (!result) {
				message = "用户名已存在";
			}
		}
		if (UacApiConstant.Valid.EMAIL.equals(type)) {
			result = uacUserService.checkEmail(validValue);
			if (!result) {
				message = "邮箱已存在";
			}
		}

		if (UacApiConstant.Valid.MOBILE_NO.equals(type)) {
			result = uacUserService.checkMobileNo(validValue);
			if (!result) {
				message = "手机号码已存在";
			}
		}

		return WrapMapper.wrap(Wrapper.SUCCESS_CODE, message, result);
	}


	/**
	 * 重置密码-邮箱-提交.
	 *
	 * @param email the email
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdEmail")
	@ApiOperation(httpMethod = "POST", value = "重置密码-邮箱-提交")
	public Wrapper<String> submitResetPwdEmail(@RequestParam("email") String email) {
		logger.info("重置密码-邮箱-提交, email={}", email);
		emailService.submitResetPwdEmail(email);
		return WrapMapper.ok();
	}


	/**
	 * 重置密码-手机-提交.
	 *
	 * @param mobile   the mobile
	 * @param response the response
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/submitResetPwdPhone")
	@ApiOperation(httpMethod = "POST", value = "重置密码-手机-提交")
	public Wrapper<String> submitResetPwdPhone(@RequestParam("mobile") String mobile, HttpServletResponse response) {
		logger.info("重置密码-手机-提交, mobile={}", mobile);
		String token = smsService.submitResetPwdPhone(mobile, response);
		return WrapMapper.ok(token);
	}

	/**
	 * 重置密码-最终提交.
	 *
	 * @param resetLoginPwdDto the reset login pwd dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/resetLoginPwd")
	@ApiOperation(httpMethod = "POST", value = "重置密码-最终提交")
	public Wrapper<Boolean> checkResetSmsCode(ResetLoginPwdDto resetLoginPwdDto) {
		uacUserService.resetLoginPwd(resetLoginPwdDto);
		return WrapMapper.ok();
	}

	/**
	 * 注册用户.
	 *
	 * @param user the user
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/register")
	@ApiOperation(httpMethod = "POST", value = "注册用户")
	public Wrapper registerUser(UserRegisterDto user) {
		uacUserService.register(user);
		return WrapMapper.ok();
	}

	/**
	 * 激活用户.
	 *
	 * @param activeUserToken the active user token
	 *
	 * @return the wrapper
	 */
	@GetMapping(value = "/activeUser/{activeUserToken}")
	@ApiOperation(httpMethod = "POST", value = "激活用户")
	public Wrapper activeUser(@PathVariable String activeUserToken) {
		uacUserService.activeUser(activeUserToken);
		return WrapMapper.ok("激活成功");
	}

	/**
	 * 查询日志.
	 *
	 * @param operationLogDto the operation log dto
	 *
	 * @return the integer
	 */
	@PostMapping(value = "/saveLog")
	@ApiOperation(httpMethod = "POST", value = "查询日志")
	public Integer saveLog(@RequestBody OperationLogDto operationLogDto) {
		logger.info("saveLog - 保存操作日志. operationLogDto={}", operationLogDto);
		return uacLogService.saveOperationLog(operationLogDto);
	}

	@PostMapping(value = "/callback/qq")
	public void callbackQQ(HttpServletRequest request) {
		logger.info("callback - callback qq. request={}", request);
	}
}
