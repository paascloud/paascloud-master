package com.paascloud.provider.web.rpc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.paascloud.PublicUtil;
import com.paascloud.ValidateUtil;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.dto.robot.*;
import com.paascloud.provider.model.enums.RobotMsgTypeEnum;
import com.paascloud.provider.service.DingtalkFeignApi;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The class Opc attachment feign client.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@Api(value = "API - DingtalkFeignClient", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DingtalkFeignClient extends BaseController implements DingtalkFeignApi {

	@Override
	@ApiOperation(httpMethod = "POST", value = "发送钉钉消息")
	public Wrapper<Boolean> sendChatRobotMsg(@RequestBody ChatRobotMsgDto chatRobotMsgDto) {
		logger.info("sendChatRobotMsg - 钉钉机器人开始发送消息. chatRobotMsgDto = {}", chatRobotMsgDto);
		boolean result;
		try {
			checkChatReBotMsg(chatRobotMsgDto);
			HttpClient httpclient = HttpClients.createDefault();
			String webhookToken = "https://oapi.dingtalk.com/robot/send?access_token=" + chatRobotMsgDto.getWebhookToken();
			HttpPost httpPost = new HttpPost(webhookToken);
			ObjectMapper mapper = new ObjectMapper();
			String robotJson = mapper.writeValueAsString(chatRobotMsgDto);
			logger.info("robotJson = {}", robotJson);
			httpPost.addHeader("Content-Type", "application/json; charset=utf-8");
			StringEntity se = new StringEntity(robotJson, "utf-8");
			httpPost.setEntity(se);
			logger.info("robotJson={}", robotJson);
			logger.info("httpPost={}", httpPost);
			HttpResponse response;
			response = httpclient.execute(httpPost);
			if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
				logger.info("钉钉机器人发送消息成功 response={}", response);
				result = true;
			} else {
				logger.error("钉钉机器人发送消息失败 response={}", response);
				result = false;
			}
		} catch (IOException e) {
			logger.error("发送钉钉消息,出现异常 ex={}", e.getMessage(), e);
			return WrapMapper.error("发送钉钉消息失败");
		}
		return WrapMapper.ok(result);
	}

	/**
	 * 校验消息体
	 */
	private void checkChatReBotMsg(ChatRobotMsgDto chatRobotMsgDto) {

		String webhookToken = chatRobotMsgDto.getWebhookToken();
		String msgType = chatRobotMsgDto.getMsgType();
		Preconditions.checkArgument(PublicUtil.isNotEmpty(webhookToken), "钉钉机器人token为空");
		Preconditions.checkArgument(PublicUtil.isNotEmpty(msgType), "钉钉机器人消息类型为空");

		if (RobotMsgTypeEnum.MARKDOWN.getName().equals(msgType)) {
			MarkdownDto markdown = chatRobotMsgDto.getMarkdown();
			String text = markdown.getText();
			String title = markdown.getTitle();

			Preconditions.checkArgument(PublicUtil.isNotEmpty(markdown), "markdown类型消息体为空");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(text), "markdown文档内容为空");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(title), "markdown文档标题为空");
		} else if (RobotMsgTypeEnum.LINK.getName().equals(msgType)) {
			LinkDto link = chatRobotMsgDto.getLink();
			String text = link.getText();
			String title = link.getTitle();
			String messageUrl = link.getMessageUrl();

			Preconditions.checkArgument(PublicUtil.isNotEmpty(link), "link类型消息体空");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(text), "link文档内容为空");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(title), "link文档标题为空");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(messageUrl), "link文档点击消息跳转的URL空");
		} else if (RobotMsgTypeEnum.TEXT.getName().equals(msgType)) {
			TextDto text = chatRobotMsgDto.getText();
			AtDto at = chatRobotMsgDto.getAt();
			String content = text.getContent();

			Preconditions.checkArgument(PublicUtil.isNotEmpty(text), "text类型消息体空");
			Preconditions.checkArgument(PublicUtil.isNotEmpty(content), "text类型消息体内容为空");

			if (PublicUtil.isNotEmpty(at)) {
				String[] atMobiles = at.getAtMobiles();
				if (PublicUtil.isNotEmpty(atMobiles)) {
					for (String atMobile : atMobiles) {
						Preconditions.checkArgument(ValidateUtil.isMobileNumber(atMobile), "手机号码:" + atMobile + "格式错误");
					}
				}
			}

		} else {
			throw new IllegalArgumentException("钉钉机器人消息类型错误");
		}
	}
}
