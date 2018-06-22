/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcOssService.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service;

import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.dto.oss.OptUploadFileReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;

/**
 * The interface Opc oss service.
 *
 * @author paascloud.net@gmail.com
 */
public interface OpcOssService {
	/**
	 * Upload file opt upload file resp dto.
	 *
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the opt upload file resp dto
	 */
	OptUploadFileRespDto uploadFile(OptUploadFileReqDto optUploadFileReqDto);

	/**
	 * Gets file url.
	 *
	 * @param optGetUrlRequest the opt get url request
	 *
	 * @return the file url
	 */
	String getFileUrl(OptGetUrlRequest optGetUrlRequest);
}
