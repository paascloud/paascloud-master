package com.paascloud.provider.model.factory;


import com.paascloud.provider.model.dto.robot.*;
import com.paascloud.provider.model.enums.RobotMsgTypeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


/**
 * 钉钉消息对象创建工厂.
 *
 * @author paascloud.net @gmail.com
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRobotMsgFactory {

	/**
	 * 创建钉钉机器人Markdown消息
	 *
	 * @param webhookToken token
	 * @param text         消息内容
	 * @param title        标题
	 * @param isAtAll      是否@所有人:true,否则为:false
	 * @param atMobiles    被@人的手机号(在text内容里要有@手机号)
	 *
	 * @return chat robot msg dto
	 */
	public static ChatRobotMsgDto createChatRobotMarkdownMsg(String webhookToken, String text, String title,
	                                                         boolean isAtAll, String[] atMobiles) {
		ChatRobotMsgDto entity = new ChatRobotMsgDto();
		entity.setMsgType(RobotMsgTypeEnum.MARKDOWN.getType());
		entity.setWebhookToken(webhookToken);

		MarkdownDto markdownDto = new MarkdownDto();
		markdownDto.setTitle(title);
		markdownDto.setText(text);
		entity.setMarkdown(markdownDto);

		AtDto atDto = new AtDto();
		atDto.setAtAll(isAtAll);
		if (atMobiles != null && atMobiles.length > 0) {
			atDto.setAtMobiles(atMobiles);
		}
		entity.setAt(atDto);
		return entity;
	}

	/**
	 * 创建钉钉机器人Link消息
	 *
	 * @param webhookToken token
	 * @param text         消息内容。如果太长只会部分展示
	 * @param title        消息标题
	 * @param messageUrl   点击消息跳转的URL
	 * @param picUrl       图片URL
	 *
	 * @return chat robot msg dto
	 */
	public static ChatRobotMsgDto createChatRobotLinkMsg(String webhookToken, String text, String title,
	                                                     String messageUrl, String picUrl) {
		ChatRobotMsgDto entity = new ChatRobotMsgDto();
		entity.setMsgType(RobotMsgTypeEnum.LINK.getType());
		entity.setWebhookToken(webhookToken);
		LinkDto linkDto = new LinkDto();
		linkDto.setText(text);
		linkDto.setTitle(title);
		linkDto.setPicUrl(picUrl);
		linkDto.setMessageUrl(messageUrl);
		entity.setLink(linkDto);
		return entity;
	}

	/**
	 * 创建钉钉机器人文本消息
	 *
	 * @param webhookToken token
	 * @param text         消息内容
	 * @param isAtAll      是否@所有人:true,否则为:false
	 * @param atMobiles    被@人的手机号(在text内容里要有@手机号)
	 *
	 * @return chat robot msg dto
	 */
	public static ChatRobotMsgDto createChatRobotTextMsg(String webhookToken, String text,
	                                                     boolean isAtAll, String[] atMobiles) {
		ChatRobotMsgDto entity = new ChatRobotMsgDto();
		entity.setMsgType(RobotMsgTypeEnum.TEXT.getType());
		entity.setWebhookToken(webhookToken);

		TextDto textDto = new TextDto();
		textDto.setContent(text);
		entity.setText(textDto);

		AtDto atDto = new AtDto();
		atDto.setAtAll(isAtAll);
		if (atMobiles != null && atMobiles.length > 0) {
			atDto.setAtMobiles(atMobiles);
		}
		entity.setAt(atDto);
		return entity;
	}

}
