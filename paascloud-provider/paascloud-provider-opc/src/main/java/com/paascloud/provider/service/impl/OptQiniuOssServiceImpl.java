/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptQiniuOssServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;


import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.paascloud.PublicUtil;
import com.paascloud.RedisKeyUtil;
import com.paascloud.UrlUtil;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.core.generator.UniqueIdGenerator;
import com.paascloud.provider.exceptions.OpcBizException;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;
import com.paascloud.provider.service.OpcOssService;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.xiaoleilu.hutool.io.FileTypeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * The class Opt qiniu oss service.
 *
 * @author paascloud.net@gmail.com
 */
@Slf4j
@Service
public class OptQiniuOssServiceImpl implements OpcOssService {
	@Resource
	private BucketManager bucketManager;
	@Resource
	private Auth auth;
	@Resource
	private PaascloudProperties paascloudProperties;
	@Resource
	private UploadManager uploadManager;
	@Resource
	private StringRedisTemplate srt;

	private static final String OPEN_IMG_BUCKET = "open-img-bucket";

	@Override
	@Retryable(value = Exception.class, backoff = @Backoff(delay = 5000, multiplier = 2))
	public void deleteFile(String fileName, String bucketName) throws QiniuException {
		log.info("deleteFile - 删除OSS文件. fileName={}, bucketName={}", fileName, bucketName);

		Preconditions.checkArgument(StringUtils.isNotEmpty(fileName), ErrorCodeEnum.OPC10040010.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(bucketName), "存储空间不能为空");


		Response response = bucketManager.delete(bucketName, fileName);
		log.info("deleteFile - 删除OSS文件. [OK] response={}", response);
	}

	@Override
	public Set<String> batchDeleteFile(String[] fileNameList, String bucketName) throws QiniuException {
		log.info("batchDeleteFile - 删除OSS文件. fileNameList={}, bucketName={}", fileNameList, bucketName);
		BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		batchOperations.addDeleteOp(bucketName, fileNameList);

		Response response = bucketManager.batch(batchOperations);
		BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);

		Set<String> failSet = Sets.newHashSet();
		for (int i = 0; i < fileNameList.length; i++) {
			BatchStatus status = batchStatusList[i];
			String fileName = fileNameList[i];
			if (status.code != 200) {
				failSet.add(fileName);
				log.error("batchDeleteFile - 删除OSS文件. [FAIL] fileName={}, error={}", fileName, status.data.error);
			} else {
				log.info("batchDeleteFile - 删除OSS文件. [OK] fileName={}, bucketName={}", fileName, bucketName);
			}
		}
		return failSet;
	}

	@Override
	public String getFileUrl(String domainOfBucket, String fileName, Long expires) {
		String downloadUrl;
		String encodedFileName = UrlUtil.getURLEncoderString(fileName);
		String url = String.format("%s/%s", domainOfBucket, encodedFileName);
		log.info("getFileUrl - 获取文件全路径. url={}", url);

		if (null == expires) {
			downloadUrl = auth.privateDownloadUrl(url);
		} else {
			downloadUrl = auth.privateDownloadUrl(url, expires);
		}
		log.info("getFileUrl - 获取文件全路径. [OK] downloadUrl={}", downloadUrl);
		return downloadUrl;
	}

	@Override
	public String getFileUrl(String domainOfBucket, String fileName) {
		return this.getFileUrl(domainOfBucket, fileName, null);
	}

	@Override
	public OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String filePath, String bucketName) throws IOException {
		log.info("uploadFile - 上传文件. fileName={}, bucketName={}", fileName, bucketName);

		Preconditions.checkArgument(uploadBytes != null, "读取文件失败");
		Preconditions.checkArgument(StringUtils.isNotEmpty(fileName), ErrorCodeEnum.OPC10040010.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(filePath), "文件路径不能为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(bucketName), "存储节点不能为空");

		InputStream is = new ByteArrayInputStream(uploadBytes);
		String inputStreamFileType = FileTypeUtil.getType(is);
		String newFileName = UniqueIdGenerator.generateId() + "." + inputStreamFileType;

		// 检查数据大小
		this.checkFileSize(uploadBytes);

		Response response = uploadManager.put(uploadBytes, filePath + newFileName, getUpToken(bucketName));
		DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
		log.info("uploadFile - 上传文件. [OK] putRet={}", putRet);
		if (PublicUtil.isEmpty(putRet) || StringUtils.isEmpty(putRet.key)) {
			throw new OpcBizException(ErrorCodeEnum.OPC10040009);
		}
		String fileUrl;
		// 获取图片路径
		if (StringUtils.equals(OPEN_IMG_BUCKET, bucketName)) {
			fileUrl = paascloudProperties.getQiniu().getOss().getPublicHost() + "/" + filePath + newFileName;
		} else {
			String domainUrl = paascloudProperties.getQiniu().getOss().getPrivateHost();
			fileUrl = this.getFileUrl(domainUrl, fileName);
		}
		OptUploadFileRespDto result = new OptUploadFileRespDto();
		result.setAttachmentUrl(fileUrl);
		result.setAttachmentName(newFileName);
		result.setAttachmentPath(filePath);
		return result;
	}

	private String getUpToken(String bucketName) {
		return auth.uploadToken(bucketName);
	}

	private void checkFileSize(byte[] uploadFileByte) {
		long redisFileSize;
		Long fileMaxSize = paascloudProperties.getQiniu().getOss().getFileMaxSize();
		Preconditions.checkArgument(fileMaxSize != null, "每天上传文件最大值没有配置");

		String fileSizeKey = RedisKeyUtil.getFileSizeKey();
		long fileSize = uploadFileByte.length;

		String redisFileSizeStr = srt.opsForValue().get(fileSizeKey);

		if(StringUtils.isEmpty(redisFileSizeStr)) {
			redisFileSizeStr = "0";
		}
		redisFileSize = Long.valueOf(redisFileSizeStr);
		if (fileSize + redisFileSize > fileMaxSize) {
			throw new OpcBizException(ErrorCodeEnum.OPC10040011);
		}

		srt.opsForValue().set(fileSizeKey, String.valueOf(redisFileSize + fileSize), 1, TimeUnit.DAYS);
	}
}
