package com.paascloud.provider.model.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * The class At dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel(value = "AtDto")
public class AtDto implements Serializable {
	private static final long serialVersionUID = 2344037651462081640L;
	/**
	 * 被@人的手机号
	 */
	@ApiModelProperty(value = "被@人的手机号")
	private String[] atMobiles;
	/**
	 * \@所有人时:true,否则为:false
	 */
	@ApiModelProperty(value = "@所有人时:true,否则为:false")
	private boolean isAtAll;
}
