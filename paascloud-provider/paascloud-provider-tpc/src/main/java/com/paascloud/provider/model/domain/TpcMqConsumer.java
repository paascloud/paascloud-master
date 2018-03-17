package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Tpc mq consumer.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_tpc_mq_consumer")
@Alias(value = "tpcMqConsumer")
public class TpcMqConsumer extends BaseEntity {

	private static final long serialVersionUID = 9104188440392558541L;

	/**
	 * 微服务名称
	 */
	@Column(name = "application_name")
	private String applicationName;

	/**
	 * 城市编码
	 */
	@Column(name = "consumer_code")
	private String consumerCode;

	/**
	 * 区域编码
	 */
	@Column(name = "consumer_name")
	private String consumerName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;

}