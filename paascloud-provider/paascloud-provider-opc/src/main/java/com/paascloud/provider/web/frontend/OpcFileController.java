/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcFileController.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.web.frontend;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.base.exception.BusinessException;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.attachment.OptAttachmentRespDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileReqDto;
import com.paascloud.provider.model.dto.oss.OptUploadFileRespDto;
import com.paascloud.provider.service.OpcAttachmentService;
import com.paascloud.provider.service.OpcOssService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * The class Opt file controller.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/file", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - OptFileRest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class OpcFileController extends BaseController {

	@Resource
	private OpcAttachmentService optAttachmentService;
	@Resource
	private OpcOssService opcOssService;

	/**
	 * 上传文件.
	 *
	 * @param request             the request
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(consumes = "multipart/form-data", value = "/uploadFile")
	@ApiOperation(httpMethod = "POST", value = "上传文件")
	public Wrapper<String> uploadFile(HttpServletRequest request, OptUploadFileReqDto optUploadFileReqDto) {
		StringBuilder temp = new StringBuilder();
		logger.info("uploadFile - 上传文件. optUploadFileReqDto={}", optUploadFileReqDto);
		Preconditions.checkArgument(StringUtils.isNotEmpty(optUploadFileReqDto.getFileType()), "文件类型为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(optUploadFileReqDto.getBucketName()), "存储地址为空");

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<OptUploadFileRespDto> optUploadFileRespDtos = optAttachmentService.uploadFile(multipartRequest, optUploadFileReqDto, getLoginAuthDto(), true);
		for (final OptUploadFileRespDto fileRespDto : optUploadFileRespDtos) {
			temp.append(fileRespDto.getAttachmentId()).append(",");
		}
		String attachmentIds = temp.toString();
		if (StringUtils.isNotEmpty(attachmentIds)) {
			attachmentIds = StringUtils.substringBeforeLast(attachmentIds, GlobalConstant.Symbol.COMMA);
		}
		return WrapMapper.ok(attachmentIds);
	}

	/**
	 * Upload picture with wang editor map.
	 *
	 * @param request             the request
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the map
	 */
	@PostMapping(consumes = "multipart/form-data", value = "/uploadPictureWithWangEditor")
	@ApiOperation(httpMethod = "POST", value = "上传文件")
	public Map<String, Object> uploadPictureWithWangEditor(HttpServletRequest request, OptUploadFileReqDto optUploadFileReqDto) {
		logger.info("uploadWangEditor - 上传文件. optUploadFileReqDto={}", optUploadFileReqDto);

		String fileType = optUploadFileReqDto.getFileType();
		String bucketName = optUploadFileReqDto.getBucketName();
		Preconditions.checkArgument(StringUtils.isNotEmpty(fileType), "文件类型为空");
		Preconditions.checkArgument(StringUtils.isNotEmpty(bucketName), "存储地址为空");
		optUploadFileReqDto.setFilePath("paascloud/picture/wangEditor/");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		List<OptUploadFileRespDto> optUploadFileRespDtos = optAttachmentService.uploadFile(multipartRequest, optUploadFileReqDto, getLoginAuthDto(), false);

		List<String> imgUrlList = Lists.newArrayList();
		for (final OptUploadFileRespDto fileRespDto : optUploadFileRespDtos) {
			imgUrlList.add(fileRespDto.getAttachmentUrl());
		}

		Map<String, Object> map = Maps.newHashMap();
		map.put("errno", 0);
		map.put("data", imgUrlList.toArray());
		return map;
	}

	/**
	 * 根据ID查询附件信息.
	 *
	 * @param id the id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryAttachmentById/{id}")
	@ApiOperation(httpMethod = "POST", value = "根据ID查询附件信息")
	public Wrapper<OptAttachmentRespDto> queryAttachment(@PathVariable Long id) {
		logger.info("queryAttachment -根据ID查询文件信息. id={}", id);

		OptAttachmentRespDto optAttachmentRespDto = optAttachmentService.queryAttachmentById(id);
		return WrapMapper.ok(optAttachmentRespDto);
	}

	/**
	 * 根据关联单号查询附件信息.
	 *
	 * @param refNo the ref no
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryAttachmentListByRefNo/{refNo}")
	@ApiOperation(httpMethod = "POST", value = "根据关联单号查询附件信息")
	public Wrapper<List<OptAttachmentRespDto>> queryAttachmentListByRefNo(@PathVariable String refNo) {
		logger.info("queryAttachment -查询附件信息. refNo={}", refNo);

		List<OptAttachmentRespDto> optAttachmentRespDtos = optAttachmentService.queryAttachmentListByRefNo(refNo);
		return WrapMapper.ok(optAttachmentRespDtos);
	}


	/**
	 * 删除附件信息.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/deleteAttachment/{attachmentId}")
	@ApiOperation(httpMethod = "POST", value = "删除附件信息")
	public Wrapper deleteAttachment(@PathVariable Long attachmentId) {
		logger.info("deleteAttachment - 删除文件. attachmentId={}", attachmentId);
		int result;
		try {
			result = optAttachmentService.deleteFile(attachmentId);
		} catch (BusinessException ex) {
			logger.error("删除文件, 出现异常={}", ex.getMessage(), ex);
			return WrapMapper.wrap(Wrapper.ERROR_CODE, "出现异常");
		} catch (Exception e) {
			logger.error("删除取文件, 出现异常={}", e.getMessage(), e);
			return WrapMapper.error();
		}
		return handleResult(result);
	}

}
