package com.paascloud.provider.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.util.Date;

/**
 * The class Tpc mq confirm.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@Alias("tpcMqConfirm")
@Table(name = "pc_tpc_mq_confirm")
@NoArgsConstructor
public class TpcMqConfirm {
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 任务ID
	 */
	@Column(name = "message_id")
	private Long messageId;

	/**
	 * 消息唯一标识
	 */
	@Column(name = "message_key")
	private String messageKey;

	/**
	 * 消费者组编码
	 */
	@Column(name = "consumer_code")
	private String consumerCode;

	/**
	 * 消费的数次
	 */
	@Column(name = "consume_count")
	private Integer consumeCount;

	/**
	 * 状态, 10 - 未确认 ; 20 - 已确认; 30 已消费
	 */
	private Integer status;

	/**
	 * 创建时间
	 */
	@Column(name = "created_time")
	private Date createdTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private Date updateTime;

	/**
	 * Instantiates a new Tpc mq confirm.
	 *
	 * @param id           the id
	 * @param messageId    the message id
	 * @param messageKey   the message key
	 * @param consumerCode the consumer code
	 */
	public TpcMqConfirm(final Long id, final Long messageId, final String messageKey, final String consumerCode) {
		this.id = id;
		this.messageId = messageId;
		this.messageKey = messageKey;
		this.consumerCode = consumerCode;
	}
}