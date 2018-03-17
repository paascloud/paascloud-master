package com.paascloud.provider.model.dto.oss;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The class Opt batch get url request.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
@NoArgsConstructor
public class OptBatchGetUrlRequest {

	public OptBatchGetUrlRequest(String refNo) {
		this.refNo = refNo;
	}

	@ApiModelProperty(value = "文件流水号")
	private String refNo;

	@ApiModelProperty(value = "超时时间")
	private Long expires;

	@ApiModelProperty(value = "是否需要解密")
	private boolean encrypt;

}
