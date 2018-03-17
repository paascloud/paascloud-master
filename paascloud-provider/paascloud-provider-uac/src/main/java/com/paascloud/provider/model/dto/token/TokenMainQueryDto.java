package com.paascloud.provider.model.dto.token;


import com.paascloud.base.dto.BaseQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The class Token main query dto.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
@EqualsAndHashCode(callSuper = true)
public class TokenMainQueryDto extends BaseQuery {
	private static final long serialVersionUID = -4003383211817581110L;
	private String loginName;

	private String userName;

	private Integer status;
}
