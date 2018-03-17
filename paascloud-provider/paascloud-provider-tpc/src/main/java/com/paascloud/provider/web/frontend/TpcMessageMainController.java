package com.paascloud.provider.web.frontend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.MessageQueryDto;
import com.paascloud.core.support.BaseController;
import com.paascloud.provider.model.vo.TpcMessageVo;
import com.paascloud.provider.service.TpcMqMessageService;
import com.paascloud.wrapper.WrapMapper;
import com.paascloud.wrapper.Wrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 异常管理.
 *
 * @author paascloud.net @gmail.com
 */
@RestController
@RequestMapping(value = "/message", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "Web - TpcMessageMainController", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TpcMessageMainController extends BaseController {
	@Resource
	private TpcMqMessageService tpcMqMessageService;

	/**
	 * 异常日志列表.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryRecordListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询各中心落地消息记录")
	public Wrapper queryRecordListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
		logger.info("分页查询各中心落地消息记录. messageQueryDto={}", messageQueryDto);
		return tpcMqMessageService.queryRecordListWithPage(messageQueryDto);
	}

	/**
	 * Resend message by id wrapper.
	 *
	 * @param messageId the message id
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/resendMessageById/{messageId}")
	@ApiOperation(httpMethod = "POST", value = "重发消息")
	public Wrapper resendMessageById(@PathVariable Long messageId) {
		logger.info("重发消息. messageId={}", messageId);
		tpcMqMessageService.resendMessageByMessageId(messageId);
		return WrapMapper.ok();
	}

	/**
	 * Query reliable list with page wrapper.
	 *
	 * @param messageQueryDto the message query dto
	 *
	 * @return the wrapper
	 */
	@PostMapping(value = "/queryReliableListWithPage")
	@ApiOperation(httpMethod = "POST", value = "分页查询可靠消息")
	public Wrapper queryReliableListWithPage(@ApiParam(name = "tpcMessageQueryDto") @RequestBody MessageQueryDto messageQueryDto) {
		logger.info("分页查询可靠消息. tpcMessageQueryDto={}", messageQueryDto);
		PageHelper.startPage(messageQueryDto.getPageNum(), messageQueryDto.getPageSize());
		messageQueryDto.setOrderBy("update_time desc");
		List<TpcMessageVo> list = tpcMqMessageService.listReliableMessageVo(messageQueryDto);
		PageInfo<TpcMessageVo> pageInfo = new PageInfo<>(list);
		if (PublicUtil.isNotEmpty(list)) {
			Map<Long, TpcMessageVo> messageVoMap = this.trans2Map(list);
			List<Long> messageIdList = new ArrayList<>(messageVoMap.keySet());

			List<TpcMessageVo> mqConfirmVoList = tpcMqMessageService.listReliableMessageVo(messageIdList);
			for (TpcMessageVo vo : mqConfirmVoList) {
				Long subscribeId = vo.getId();
				if (!messageVoMap.containsKey(subscribeId)) {
					continue;
				}
				TpcMessageVo tpcMessageVo = messageVoMap.get(subscribeId);
				tpcMessageVo.setMqConfirmVoList(vo.getMqConfirmVoList());
			}
			pageInfo.setList(new ArrayList<>(messageVoMap.values()));
		}
		return WrapMapper.ok(pageInfo);
	}

	private Map<Long, TpcMessageVo> trans2Map(List<TpcMessageVo> tpcMessageVoList) {
		Map<Long, TpcMessageVo> resultMap = new TreeMap<>((o1, o2) -> {
			o1 = o1 == null ? 0 : o1;
			o2 = o2 == null ? 0 : o2;
			return o2.compareTo(o1);
		});
		for (TpcMessageVo vo : tpcMessageVoList) {
			resultMap.put(vo.getId(), vo);
		}
		return resultMap;
	}
}
