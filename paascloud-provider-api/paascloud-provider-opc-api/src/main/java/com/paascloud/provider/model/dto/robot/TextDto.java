package com.paascloud.provider.model.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * The class Text dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel(value = "text类型")
public class TextDto implements Serializable {
	private static final long serialVersionUID = 8825625125019746717L;
	@ApiModelProperty(value = "消息内容", required = true)
	private String content;
}
