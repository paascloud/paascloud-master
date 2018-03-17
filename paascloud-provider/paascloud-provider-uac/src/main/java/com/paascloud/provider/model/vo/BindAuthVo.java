package com.paascloud.provider.model.vo;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * The class Bind auth vo.
 *
 * @author paascloud.net @gmail.com
 */
@Data
@ApiModel
public class BindAuthVo {
	/**
	 * 包含按钮权限和菜单权限
	 */
	private List<MenuVo> authTree;
	/**
	 * 该角色含有的权限ID
	 */
	private List<Long> checkedAuthList;
}
