package com.paascloud.config.properties;

import lombok.Data;

/**
 * The class Qiniu oss properties.
 *
 * @author paascloud.net@gmail.com
 */
@Data
public class QiniuProperties {
	private QiniuKeyProperties key = new QiniuKeyProperties();
	private QiniuOssProperties oss = new QiniuOssProperties();

	@Data
	public class QiniuKeyProperties {
		private String accessKey;
		private String secretKey;
	}

	@Data
	public class QiniuOssProperties {
		private String privateHost;
		private String publicHost;
		private Long fileMaxSize;
	}
}
