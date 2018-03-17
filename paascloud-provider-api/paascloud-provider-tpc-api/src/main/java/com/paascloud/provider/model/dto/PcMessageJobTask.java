package com.paascloud.provider.model.dto;

import lombok.Data;

import java.io.Serializable;


/**
 * The class Pc message job task.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class PcMessageJobTask implements Serializable {

	private static final long serialVersionUID = -1689940882253489536L;

	/**
	 * 自增ID
	 */
	private String id;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 消息key
	 */
	private Long messageKey;

	/**
	 * topic
	 */
	private String messageTopic;

	/**
	 * tag
	 */
	private String messageTag;
}