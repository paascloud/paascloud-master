package com.paascloud.provider.model.dto.attachment;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * The class Opt attachment req dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "OptAttachmentReqDto")
public class OptAttachmentReqDto implements Serializable {

	private static final long serialVersionUID = -1727131719075160349L;
	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long id;

	/**
	 * 附件流水号
	 */
	@ApiModelProperty(value = "附件流水号")
	private String serialNo;

	/**
	 * 上传附件的相关业务流水号
	 */
	@ApiModelProperty(value = "上传附件的相关业务流水号")
	private String refNo;

}
