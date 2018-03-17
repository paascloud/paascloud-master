package com.paascloud.provider.model.dto;


import lombok.Data;

/**
 * The class Message task query dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
public class MessageTaskQueryDto {
	private int yn;
	private Integer taskStatus;
	private int messageStatus;
	private int fetchNum;
	private int shardingItem;
	private int shardingTotalCount;
	private String producerGroup;
	private String createTimeBefore;

}
