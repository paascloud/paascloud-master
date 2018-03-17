package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Tpc mq producer.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_tpc_mq_producer")
@Alias(value = "tpcMqProducer")
public class TpcMqProducer extends BaseEntity {
	private static final long serialVersionUID = -4064061704648362318L;

	/**
	 * 微服务名称
	 */
	@Column(name = "application_name")
	private String applicationName;

	/**
	 * PID 生产者组编码
	 */
	@Column(name = "producer_code")
	private String producerCode;

	/**
	 * PID 生产者组名称
	 */
	@Column(name = "producer_name")
	private String producerName;

	@Column(name = "query_message_url")
	private String queryMessageUrl;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}