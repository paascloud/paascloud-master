package com.paascloud.provider.service;


import com.paascloud.provider.model.dto.oss.ElementImgUrlDto;
import com.paascloud.provider.model.dto.oss.OptBatchGetUrlRequest;
import com.paascloud.provider.model.dto.oss.OptGetUrlRequest;
import com.paascloud.provider.model.dto.robot.ChatRobotMsgDto;
import com.paascloud.wrapper.Wrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * The class Opc rpc service.
 *
 * @author paascloud.net @gmail.com
 */
@Component
public class OpcRpcService {

	@Resource
	private DingtalkFeignApi dingtalkFeignApi;
	@Resource
	private OpcOssFeignApi opcOssFeignApi;

	public boolean sendChatRobotMsg(ChatRobotMsgDto chatRobotMsgDto) {
		Wrapper<Boolean> result = dingtalkFeignApi.sendChatRobotMsg(chatRobotMsgDto);
		return result.getResult();
	}

	public List<ElementImgUrlDto> listFileUrl(OptBatchGetUrlRequest urlRequest) {
		Wrapper<List<ElementImgUrlDto>> result = opcOssFeignApi.listFileUrl(urlRequest);
		return result.getResult();
	}

	public String getFileUrl(final OptGetUrlRequest request) {
		Wrapper<String> result = opcOssFeignApi.getFileUrl(request);
		return result.getResult();
	}
}