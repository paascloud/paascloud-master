package com.paascloud.provider.service;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.core.support.IService;
import com.paascloud.provider.model.domain.OptAttachment;
import com.paascloud.provider.model.dto.UpdateAttachmentDto;
import com.paascloud.provider.model.dto.attachment.OptAttachmentRespDto;
import com.paascloud.provider.model.dto.oss.*;
import com.qiniu.common.QiniuException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

/**
 * The interface Opc attachment service.
 *
 * @author paascloud.net @gmail.com
 */
public interface OpcAttachmentService extends IService<OptAttachment> {
	/**
	 * Upload file string.
	 *
	 * @param multipartRequest    the multipart request
	 * @param optUploadFileReqDto the opt upload file req dto
	 * @param loginAuthDto        the login auth dto
	 * @param storeDbFlag         the store db flag
	 *
	 * @return the string
	 */
	List<OptUploadFileRespDto> uploadFile(MultipartHttpServletRequest multipartRequest, OptUploadFileReqDto optUploadFileReqDto, LoginAuthDto loginAuthDto, boolean storeDbFlag);

	/**
	 * 根据ID查询附件信息.
	 *
	 * @param id the id
	 *
	 * @return the opt attachment resp dto
	 */
	OptAttachmentRespDto queryAttachmentById(Long id);

	/**
	 * 根据关联单号查询附件信息.
	 *
	 * @param refNo the ref no
	 *
	 * @return the list
	 */
	List<OptAttachmentRespDto> queryAttachmentListByRefNo(String refNo);

	/**
	 * Query attachment by ref no list.
	 *
	 * @param refNo the ref no
	 *
	 * @return the list
	 */
	List<Long> queryAttachmentByRefNo(String refNo);

	/**
	 * Delete file int.
	 *
	 * @param fileName     the file name
	 * @param bucketName   the bucket name
	 * @param attachmentId the attachment id
	 *
	 * @return the int
	 *
	 * @throws QiniuException the qiniu exception
	 */
	int deleteFile(String fileName, String bucketName, Long attachmentId) throws QiniuException;

	/**
	 * Delete file int.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the int
	 *
	 * @throws QiniuException the qiniu exception
	 */
	int deleteFile(Long attachmentId) throws QiniuException;

	/**
	 * Save attachment.
	 *
	 * @param optAttachment the opt attachment
	 * @param loginAuthDto  the login auth dto
	 */
	void saveAttachment(OptAttachment optAttachment, LoginAuthDto loginAuthDto);

	/**
	 * RPC上传附件信息.
	 *
	 * @param optUploadFileReqDto the opt upload file req dto
	 *
	 * @return the opt upload file resp dto
	 *
	 * @throws IOException the io exception
	 */
	OptUploadFileRespDto rpcUploadFile(OptUploadFileReqDto optUploadFileReqDto) throws IOException;

	/**
	 * 获取附件完整路径.
	 *
	 * @param optGetUrlRequest the opt get url request
	 *
	 * @return the string
	 */
	String rpcGetFileUrl(OptGetUrlRequest optGetUrlRequest);

	/**
	 * Gets by id.
	 *
	 * @param attachmentId the attachment id
	 *
	 * @return the by id
	 */
	OptAttachment getById(Long attachmentId);

	/**
	 * Upload file opt upload file resp dto.
	 *
	 * @param uploadBytes  the upload bytes
	 * @param fileName     the file name
	 * @param fileType     the file type
	 * @param filePath     the file path
	 * @param bucketName   the bucket name
	 * @param loginAuthDto the login auth dto
	 *
	 * @return the opt upload file resp dto
	 *
	 * @throws IOException the io exception
	 */
	OptUploadFileRespDto uploadFile(byte[] uploadBytes, String fileName, String fileType, String filePath, String bucketName, LoginAuthDto loginAuthDto) throws IOException;

	/**
	 * 更新附件.
	 *
	 * @param attachmentDto the attachment dto
	 *
	 * @throws QiniuException the qiniu exception
	 */
	void updateAttachment(UpdateAttachmentDto attachmentDto) throws QiniuException;

	/**
	 * List file url list.
	 *
	 * @param urlRequest the url request
	 *
	 * @return the list
	 */
	List<ElementImgUrlDto> listFileUrl(OptBatchGetUrlRequest urlRequest);

	/**
	 * List by ref no list.
	 *
	 * @param refNo the ref no
	 *
	 * @return the list
	 */
	List<OptAttachment> listByRefNo(final String refNo);

	/**
	 * 查询已过期的文件.
	 *
	 * @return the list
	 */
	List<OptAttachment> listExpireFile();
}
