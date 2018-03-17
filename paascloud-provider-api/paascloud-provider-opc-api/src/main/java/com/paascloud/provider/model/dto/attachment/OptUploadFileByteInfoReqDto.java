package com.paascloud.provider.model.dto.attachment;

import lombok.Data;

import java.io.Serializable;

/**
 * The class Opt upload file byte info req dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class OptUploadFileByteInfoReqDto implements Serializable {
	private static final long serialVersionUID = 4695223041316258658L;

	/**
	 * 文件字节码
	 */
	private byte[] fileByteArray;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 文件类型
	 */
	private String fileType;
}
