package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Tpc mq topic.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_tpc_mq_topic")
@Alias(value = "tpcMqTopic")
public class TpcMqTopic extends BaseEntity {
	private static final long serialVersionUID = 5642946024630652202L;

	/**
	 * 城市编码
	 */
	@Column(name = "topic_code")
	private String topicCode;

	/**
	 * 区域编码
	 */
	@Column(name = "topic_name")
	private String topicName;

	/**
	 * MQ类型, 10 rocketmq 20 kafka
	 */
	@Column(name = "mq_type")
	private Integer mqType;

	/**
	 * 消息类型, 10 无序消息, 20 无序消息
	 */
	@Column(name = "msg_type")
	private Integer msgType;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remarks;
}