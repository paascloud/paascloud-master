package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.util.List;

/**
 * The class Tpc mq message.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TpcMessageVo extends BaseVo {
	private static final long serialVersionUID = 7353473261430862023L;

	private Integer version;

	/**
	 * 消息key
	 */
	private String messageKey;

	/**
	 * topic
	 */
	private String messageTopic;

	/**
	 * tag
	 */
	private String messageTag;

	/**
	 * 消息类型: 10 - 生产者 ; 20 - 消费者
	 */
	private Integer messageType;

	/**
	 * 生产者PID
	 */
	private String producerGroup;

	/**
	 * 延时级别 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
	 */
	private Integer delayLevel;

	/**
	 * 顺序类型 0有序 1无序
	 */
	private Integer orderType;

	/**
	 * 消息状态
	 */
	private Integer messageStatus;

	/**
	 * 消息内容
	 */
	private String messageBody;

	/**
	 * 状态
	 */
	private Integer taskStatus;

	/**
	 * 执行次数
	 */
	private Integer resendTimes;

	/**
	 * 是否死亡 0 - 活着; 1-死亡
	 */
	private Integer dead;

	/**
	 * 执行时间
	 */
	private Integer nextExeTime;

	/**
	 * 是否删除 -0 未删除 -1 已删除
	 */
	private Integer yn;

	@Transient
	private List<TpcMqConfirmVo> mqConfirmVoList;
}