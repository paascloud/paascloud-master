/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OptAttachmentServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.paascloud.PublicUtil;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.core.support.BaseService;
import com.paascloud.provider.exceptions.OpcBizException;
import com.paascloud.provider.mapper.OptAttachmentMapper;
import com.paascloud.provider.model.domain.OptAttachment;
import com.paascloud.provider.model.dto.UpdateAttachmentDto;
import com.paascloud.provider.model.dto.attachment.OptAttachmentReqDto;
import com.paascloud.provider.model.dto.attachment.OptAttachmentRespDto;
import com.paascloud.provider.model.dto.attachment.OptUploadFileByteInfoReqDto;
import com.paascloud.provider.model.dto.oss.*;
import com.paascloud.provider.service.OpcAttachmentService;
import com.paascloud.provider.service.OpcOssService;
import com.paascloud.provider.utils.CheckFileUtil;
import com.qiniu.common.QiniuException;
import com.xiaoleilu.hutool.io.FileTypeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * The class Opt attachment service.
 *
 * @author paascloud.net @gmail.com
 */
@Service
public class OptAttachmentServiceImpl extends BaseService<OptAttachment> implements OpcAttachmentService {
	@Resource
	private OptAttachmentMapper optAttachmentMapper;
	@Resource
	private OpcOssService optOssService;
	@Resource
	private PaascloudProperties paascloudProperties;

	@Override
	public List<OptUploadFileRespDto> uploadFile(MultipartHttpServletRequest multipartRequest, OptUploadFileReqDto optUploadFileReqDto, LoginAuthDto loginAuthDto, boolean storeDbFlag) {
		String fileType = optUploadFileReqDto.getFileType();
		String filePath = optUploadFileReqDto.getFilePath();
		String bucketName = optUploadFileReqDto.getBucketName();
		List<OptUploadFileRespDto> result = Lists.newArrayList();
		if (PublicUtil.isEmpty(filePath)) {
			filePath = GlobalConstant.Sys.DEFAULT_FILE_PATH;
		}
		try {
			List<MultipartFile> fileList = multipartRequest.getFiles("file");
			if (fileList.isEmpty()) {
				return result;
			}

			for (MultipartFile multipartFile : fileList) {
				String fileName = multipartFile.getOriginalFilename();
				if (PublicUtil.isEmpty(fileName)) {
					continue;
				}
				Preconditions.checkArgument(multipartFile.getSize() <= GlobalConstant.FILE_MAX_SIZE, "上传文件不能大于5M");
				InputStream inputStream = multipartFile.getInputStream();

				String inputStreamFileType = FileTypeUtil.getType(inputStream);
				CheckFileUtil.checkFileType(fileType, inputStreamFileType);
				OptUploadFileRespDto fileInfo;
				if (storeDbFlag) {
					fileInfo = this.uploadFile(multipartFile.getBytes(), fileName, fileType, filePath, bucketName, loginAuthDto);
				} else {
					fileInfo = optOssService.uploadFile(multipartFile.getBytes(), fileName, filePath, bucketName);
				}
				result.add(fileInfo);
			}
		} catch (IOException e) {
			logger.error("上传文件失败={}", e.getMessage(), e);
		}
		return result;
	}

	@Override
	public OptAttachmentRespDto queryAttachmentById(Long id) {
		Preconditions.checkArgument(id != null, "ID不能为空");
		OptAttachmentReqDto optAttachmentReqDto = new OptAttachmentReqDto();
		optAttachmentReqDto.setId(id);
		List<OptAttachmentRespDto> optAttachmentRespDtos = optAttachmentMapper.queryAttachment(optAttachmentReqDto);
		return optAttachmentRespDtos == null ? null : optAttachmentRespDtos.get(0);
	}

	@Override
	public List<OptAttachmentRespDto> queryAttachmentListByRefNo(String refNo) {
		Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "关联单号不能为空");
		OptAttachmentReqDto optAttachmentReqDto = new OptAttachmentReqDto();
		optAttachmentReqDto.setRefNo(refNo);
		return optAttachmentMapper.queryAttachment(optAttachmentReqDto);
	}

	@Override
	public List<Long> queryAttachmentByRefNo(final String refNo) {
		return optAttachmentMapper.queryAttachmentByRefNo(refNo);
	}

	@Override
	public int deleteFile(String fileName, String bucketName, Long attachmentId) throws QiniuException {
		Preconditions.checkArgument(StringUtils.isNotEmpty(fileName), ErrorCodeEnum.OPC10040010.msg());
		Preconditions.checkArgument(StringUtils.isNotEmpty(bucketName), "存储空间不能为空");

		optOssService.deleteFile(fileName, bucketName);
		return optAttachmentMapper.deleteByPrimaryKey(attachmentId);
	}

	@Override
	public int deleteFile(final Long attachmentId) throws QiniuException {
		OptAttachment optAttachment = optAttachmentMapper.selectByPrimaryKey(attachmentId);
		if (optAttachment != null) {
			optOssService.deleteFile(optAttachment.getPath() + optAttachment.getName(), optAttachment.getBucketName());
			return optAttachmentMapper.deleteByPrimaryKey(attachmentId);
		}
		return 1;
	}

	@Override
	public void saveAttachment(OptAttachment optAttachment, LoginAuthDto loginAuthDto) {
		optAttachment.setUpdateInfo(loginAuthDto);
		if (optAttachment.isNew()) {
			optAttachmentMapper.insertSelective(optAttachment);
		} else {
			int result = optAttachmentMapper.updateByPrimaryKeySelective(optAttachment);
			if (result < 1) {
				throw new OpcBizException(ErrorCodeEnum.OPC10040007, optAttachment.getId());
			}
		}
	}

	@Override
	public OptUploadFileRespDto rpcUploadFile(OptUploadFileReqDto optUploadFileReqDto) throws IOException {
		String fileType = optUploadFileReqDto.getFileType();
		String filePath = optUploadFileReqDto.getFilePath();
		String bucketName = optUploadFileReqDto.getBucketName();
		OptUploadFileByteInfoReqDto uploadFileByteInfoReqDto = optUploadFileReqDto.getUploadFileByteInfoReqDto();
		LoginAuthDto authResDto = new LoginAuthDto();
		authResDto.setUserId(optUploadFileReqDto.getUserId());
		authResDto.setUserName(optUploadFileReqDto.getUserName());

		if (PublicUtil.isEmpty(filePath)) {
			filePath = GlobalConstant.Oss.DEFAULT_FILE_PATH;
		}
		InputStream is = null;
		try {
			Preconditions.checkArgument(uploadFileByteInfoReqDto != null, "上传数据不能为空");
			byte[] fileByteArray = uploadFileByteInfoReqDto.getFileByteArray();
			Preconditions.checkArgument(fileByteArray.length / GlobalConstant.M_SIZE <= GlobalConstant.FILE_MAX_SIZE, "上传文件不能大于5M");

			String fileName = uploadFileByteInfoReqDto.getFileName();
			is = new ByteArrayInputStream(fileByteArray);
			String type = FileTypeUtil.getType(is);
			Preconditions.checkArgument(type.equals(fileType), "文件类型不符");
			fileName = filePath + fileName;
			OptUploadFileRespDto optUploadFileRespDto = this.uploadFile(fileByteArray, fileName, fileType, filePath, bucketName, authResDto);
			optUploadFileRespDto.setFileType(fileType);
			return optUploadFileRespDto;
		} catch (IOException e) {
			logger.error("上传文件失败={}", e.getMessage(), e);
		} finally {
			if (null != is) {
				is.close();
			}
		}
		return null;
	}

	/**
	 * Rpc get file url string.
	 *
	 * @param optGetUrlRequest the opt get url request
	 *
	 * @return the string
	 */
	@Override
	public String rpcGetFileUrl(OptGetUrlRequest optGetUrlRequest) {
		Long attachmentId = optGetUrlRequest.getAttachmentId();
		Long expires = optGetUrlRequest.getExpires();
		boolean encrypt = optGetUrlRequest.isEncrypt();
		if (null == attachmentId) {
			throw new IllegalArgumentException("参数异常, 请检查参数");
		}

		OptAttachment optAttachment = this.getById(attachmentId);
		String fileName = optAttachment.getPath() + optAttachment.getName();

		return getUrl(expires, encrypt, fileName);
	}

	private String getUrl(final Long expires, final boolean encrypt, final String fileName) {
		final String domainOfBucket;
		if (encrypt) {
			domainOfBucket = paascloudProperties.getQiniu().getOss().getPrivateHost();
			return optOssService.getFileUrl(domainOfBucket, fileName, expires);
		} else {
			domainOfBucket = paascloudProperties.getQiniu().getOss().getPublicHost();
			return domainOfBucket + "/" + fileName;
		}
	}

	@Override
	public OptAttachment getById(Long attachmentId) {
		Preconditions.checkArgument(attachmentId != null, "文件流水号不能为空");
		OptAttachment optAttachment = optAttachmentMapper.selectByPrimaryKey(attachmentId);
		if (PublicUtil.isEmpty(optAttachment)) {
			throw new OpcBizException(ErrorCodeEnum.OPC10040008, attachmentId);
		}
		return optAttachment;
	}

	@Override
	public OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String fileType, String filePath, String bucketName, LoginAuthDto loginAuthDto) throws IOException {
		OptUploadFileRespDto fileInfo = optOssService.uploadFile(uploadBytes, fileName, filePath, bucketName);
		insertAttachment(fileType, bucketName, loginAuthDto, fileInfo);
		return fileInfo;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateAttachment(final UpdateAttachmentDto attachmentDto) throws QiniuException {
		List<Long> attachmentIdList = attachmentDto.getAttachmentIdList();
		LoginAuthDto loginAuthDto = attachmentDto.getLoginAuthDto();
		String refNo = attachmentDto.getRefNo();
		List<Long> idList = optAttachmentMapper.queryAttachmentByRefNo(refNo);
		if (PublicUtil.isNotEmpty(idList)) {
			idList.removeAll(attachmentIdList);
			for (final Long id : idList) {
				this.deleteFile(id);
			}
		}
		for (final Long id : attachmentIdList) {
			OptAttachment optAttachment = new OptAttachment();
			optAttachment.setId(id);
			optAttachment.setRefNo(refNo);
			optAttachment.setUpdateInfo(loginAuthDto);
			optAttachmentMapper.updateByPrimaryKeySelective(optAttachment);
		}
	}

	@Override
	public List<ElementImgUrlDto> listFileUrl(final OptBatchGetUrlRequest urlRequest) {
		List<ElementImgUrlDto> result = Lists.newArrayList();
		String refNo = urlRequest.getRefNo();
		Long expires = urlRequest.getExpires();
		boolean encrypt = urlRequest.isEncrypt();
		Preconditions.checkArgument(StringUtils.isNotEmpty(refNo), "业务单号不能为空");

		List<OptAttachment> list = this.listByRefNo(refNo);
		for (final OptAttachment optAttachment : list) {
			String fileName = optAttachment.getPath() + optAttachment.getName();
			String url = getUrl(expires, encrypt, fileName);
			if (StringUtils.isNotEmpty(url)) {
				ElementImgUrlDto dto = new ElementImgUrlDto(url, optAttachment.getName(), optAttachment.getId());
				result.add(dto);
			}
		}
		return result;
	}

	@Override
	public List<OptAttachment> listByRefNo(final String refNo) {
		OptAttachment optAttachment = new OptAttachment();
		optAttachment.setRefNo(refNo);
		return optAttachmentMapper.select(optAttachment);
	}

	@Override
	public List<OptAttachment> listExpireFile() {
		return optAttachmentMapper.listExpireFile();
	}

	private void insertAttachment(String fileType, String bucketName, LoginAuthDto loginAuthDto, OptUploadFileRespDto fileInfo) {
		String attachmentName = fileInfo.getAttachmentName();
		long id = generateId();
		OptAttachment optAttachment = new OptAttachment();
		optAttachment.setBucketName(bucketName);
		optAttachment.setFormat(fileInfo.getFileType());
		optAttachment.setName(attachmentName);
		optAttachment.setType(fileType);
		optAttachment.setFormat(StringUtils.substringAfterLast(attachmentName, "."));
		optAttachment.setPath(fileInfo.getAttachmentPath());
		optAttachment.setId(id);
		optAttachment.setCenterName(bucketName);
		fileInfo.setAttachmentId(id);
		optAttachment.setUpdateInfo(loginAuthDto);
		optAttachmentMapper.insertSelective(optAttachment);
	}
}
