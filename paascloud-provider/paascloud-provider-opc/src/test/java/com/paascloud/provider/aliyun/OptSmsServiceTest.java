package com.paascloud.provider.aliyun;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.paascloud.provider.PaasCloudOmcApplicationTests;
import com.paascloud.provider.service.OptSmsService;
import org.junit.Test;

import javax.annotation.Resource;

public class OptSmsServiceTest extends PaasCloudOmcApplicationTests {

	@Resource
	private OptSmsService optSmsService;

	@Test
	public void sendMessageProducerTest() throws InterruptedException {


		SendSmsRequest request = new SendSmsRequest();

		//必填:待发送手机号
		request.setPhoneNumbers("13718891700");
		//必填:短信签名-可在短信控制台中找到
		request.setSignName("快乐学习网");
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode("SMS_78725128");
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam("{\"code\":\"963852\"}");

		//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		//request.setSmsUpExtendCode("90997");

		//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");

		SendSmsResponse sendSmsResponse = optSmsService.sendSms(request);
		logger.info("发送 生产数据 sendSmsResponse={}", sendSmsResponse);
	}


}
