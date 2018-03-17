package com.paascloud.provider.model.dto.user;

import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.provider.model.vo.MenuVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The class Login resp dto.
 *
 * @author paascloud.net@gmail.com
 */
@Data
@ApiModel(value = "发送短信参数Dto")
public class LoginRespDto implements Serializable {
	private static final long serialVersionUID = -8992761897550131632L;
	@ApiModelProperty(value = "登陆信息")
	private LoginAuthDto loginAuthDto;
	@ApiModelProperty(value = "菜单集合")
	private List<MenuVo> menuList;
}
