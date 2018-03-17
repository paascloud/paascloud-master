package com.paascloud.provider.model.dto.attachment;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Opt attachment query req dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OptAttachmentQueryReqDto implements Serializable {

	private static final long serialVersionUID = 4781783608243094328L;
	private Long id;

	/**
	 * 附件流水号
	 */
	private String serialNo;

	/**
	 * 上传附件的相关业务流水号
	 */
	private String refNo;
}
