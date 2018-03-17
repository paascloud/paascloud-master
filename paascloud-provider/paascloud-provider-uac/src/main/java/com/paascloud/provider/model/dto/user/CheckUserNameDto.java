package com.paascloud.provider.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * The class Check user name dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "校验真实姓名唯一性Dto ")
public class CheckUserNameDto implements Serializable {

	private static final long serialVersionUID = 3802825234063017559L;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Long userId;

	/**
	 * 用户姓名
	 */
	@ApiModelProperty(value = "用户姓名")
	private String userName;
}
