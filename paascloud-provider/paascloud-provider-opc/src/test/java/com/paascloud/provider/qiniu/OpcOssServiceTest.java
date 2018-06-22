/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：OpcOssServiceTest.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.qiniu;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.provider.PaasCloudOmcApplicationTests;
import com.paascloud.provider.service.OpcOssService;
import com.qiniu.common.QiniuException;
import org.junit.Test;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class OpcOssServiceTest extends PaasCloudOmcApplicationTests {

	@Resource
	private OpcOssService optOssService;
	@Resource
	private PaascloudProperties paascloudProperties;

	@Test
	public void uploadFileTest() throws InterruptedException, IOException {

		File file = new File("C:\\Users\\Lenovo\\Pictures\\love\\love6.jpg");

		FileInputStream inputStream = new FileInputStream(file);

//		DefaultPutRet defaultPutRet = optOssService.uploadFile(inputStream, "love6.jpg", "picture", "paascloud-oss-bucket");
//		logger.info("发送 生产数据 defaultPutRet={}", defaultPutRet);
	}

	@Test
	public void deleteFileTest() throws QiniuException {

		optOssService.deleteFile("picture/2017-08-03/2a55cf5b4fab42d0984c7fa4c3d1f95f.jpg", "paascloud-oss-bucket");
		logger.info("删除成功");
	}

	@Test
	public void batchDeleteFileTest() throws QiniuException {
		String[] arr = new String[]{"love1.jpg", "image/jpg/love3.jpg"};

		optOssService.batchDeleteFile(arr, "paascloud-oss-bucket");
		logger.info("删除成功");
	}


	@Test
	public void getFileUrlTest() throws QiniuException {

		String fileUrl = optOssService.getFileUrl(paascloudProperties.getQiniu().getOss().getPrivateHost(), "alipay/QRCode14e5d60fb30d4f7ea83ed3ec936ec54d.png", 3600L);
		logger.info("获取File url成功 fileUrl={}", fileUrl);
	}

	@Test
	public void getFileUrlTest2() throws QiniuException {

		String fileUrl = optOssService.getFileUrl(paascloudProperties.getQiniu().getOss().getPrivateHost(),
				"alipay/QRCode/5be9c68b531f471bb85948a4954d5e6f.png");
		logger.info("获取File url成功 fileUrl={}", fileUrl);
	}


}
