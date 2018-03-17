package com.paascloud.provider.model.domain;

import com.paascloud.core.mybatis.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * The class Tpc mq tag.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "pc_tpc_mq_tag")
@Alias(value = "tpcMqTag")
public class TpcMqTag extends BaseEntity {
	private static final long serialVersionUID = -1063353649973385058L;

	/**
	 * 主题ID
	 */
	@Column(name = "topic_id")
	private Long topicId;

	/**
	 * 城市编码
	 */
	@Column(name = "tag_code")
	private String tagCode;

	/**
	 * 区域编码
	 */
	@Column(name = "tag_name")
	private String tagName;

	/**
	 * 状态, 0生效,10,失效
	 */
	private Integer status;

	/**
	 * 备注
	 */
	private String remark;
}