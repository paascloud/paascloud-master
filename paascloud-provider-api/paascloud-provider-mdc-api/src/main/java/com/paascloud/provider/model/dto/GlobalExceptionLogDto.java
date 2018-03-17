package com.paascloud.provider.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paascloud.ThreadLocalMap;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;

/**
 * The class Global exception log dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@NoArgsConstructor
@Slf4j
public class GlobalExceptionLogDto {

	/**
	 * 运行环境
	 */
	private String profile;

	/**
	 * 应用名称
	 */
	private String applicationName;

	/**
	 * 异常信息(通过exception.getMessage()获取到的内容)
	 */
	private String exceptionMessage;

	/**
	 * 异常原因(通过exception.getCause()获取到的内容)
	 */
	private String exceptionCause;

	/**
	 * 异常类型
	 */
	private String exceptionSimpleName;

	/**
	 * 异常堆栈信息
	 */
	private String exceptionStack;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 创建人ID
	 */
	private Long creatorId;

	/**
	 * 创建时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createdTime;

	/**
	 * Gets global exception log dto.
	 *
	 * @param ex the ex
	 *
	 * @return the global exception log dto
	 */
	public GlobalExceptionLogDto getGlobalExceptionLogDto(Exception ex, String profile, String applicationName) {
		String message = ex.getMessage();
		if (StringUtils.isNotBlank(message) && message.length() > GlobalConstant.EXCEPTION_MESSAGE_MAX_LENGTH) {
			this.exceptionMessage = StringUtils.substring(message, 0, GlobalConstant.EXCEPTION_MESSAGE_MAX_LENGTH) + "...";
		}
		this.exceptionSimpleName = ex.getClass().getSimpleName();
		String cause = ex.getCause() == null ? null : ex.getCause().toString();
		if (StringUtils.isNotBlank(cause) && cause.length() > GlobalConstant.EXCEPTION_CAUSE_MAX_LENGTH) {
			this.exceptionCause = StringUtils.substring(cause, 0, GlobalConstant.EXCEPTION_CAUSE_MAX_LENGTH) + "...";
		}
		this.exceptionStack = Arrays.toString(ex.getStackTrace());

		this.profile = profile;
		LoginAuthDto loginAuthDto = null;

		try {
			loginAuthDto = (LoginAuthDto) ThreadLocalMap.get(GlobalConstant.Sys.TOKEN_AUTH_DTO);
		} catch (Exception e) {
			log.error("获取登陆人信息, 出现异常={}", e.getMessage(), e);
		}

		if (loginAuthDto == null) {
			loginAuthDto = new LoginAuthDto(-1L, "SYSTEM_TASK", "系统任务");
		}
		this.creatorId = loginAuthDto.getUserId();
		this.creator = loginAuthDto.getUserName();
		this.applicationName = applicationName;
		return this;
	}
}