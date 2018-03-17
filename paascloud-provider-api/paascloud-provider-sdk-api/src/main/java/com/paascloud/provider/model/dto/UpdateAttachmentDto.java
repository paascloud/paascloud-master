package com.paascloud.provider.model.dto;


import com.paascloud.base.dto.LoginAuthDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 更新福建表.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@AllArgsConstructor
public class UpdateAttachmentDto implements Serializable {
	private static final long serialVersionUID = -768471033009336091L;

	public UpdateAttachmentDto() {

	}

	private String refNo;
	/**
	 * 商品图片流水号集合
	 */
	private List<Long> attachmentIdList;

	/**
	 * 操作人信息
	 */
	private LoginAuthDto loginAuthDto;
}
