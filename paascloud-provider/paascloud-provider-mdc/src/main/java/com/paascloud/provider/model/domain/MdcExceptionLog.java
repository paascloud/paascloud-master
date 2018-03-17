package com.paascloud.provider.model.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * The class Mdc exception log.
 *
 * @author paascloud.net @gmail.com
 */
@Alias("mdcExceptionLog")
@Table(name = "pc_mdc_exception_log")
@Data
@NoArgsConstructor
public class MdcExceptionLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 系统应用名
	 */
	@Column(name = "application_name")
	private String applicationName;

	/**
	 * 异常类型
	 */
	@Column(name = "exception_simple_name")
	private String exceptionSimpleName;

	/**
	 * 异常信息(通过exception.getMessage()获取到的内容)
	 */
	@Column(name = "exception_message")
	private String exceptionMessage;

	/**
	 * 异常原因(通过exception.getCause()获取到的内容)
	 */
	@Column(name = "exception_cause")
	private String exceptionCause;

	/**
	 * 异常堆栈信息
	 */
	@Column(name = "exception_stack")
	private String exceptionStack;

	/**
	 * 操作者姓名
	 */
	private String creator;

	/**
	 * 操作者id
	 */
	@Column(name = "creator_id")
	private String creatorId;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
}