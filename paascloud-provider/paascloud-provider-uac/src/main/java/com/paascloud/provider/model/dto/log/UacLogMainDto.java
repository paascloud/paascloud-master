package com.paascloud.provider.model.dto.log;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.paascloud.base.dto.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * The class Uac log main dto.
 *
 * @author paascloud.net@gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel
public class UacLogMainDto extends BaseQuery {

	private static final long serialVersionUID = 3967075132487249652L;
	/**
	 * 日志类型
	 */
	@ApiModelProperty(value = "日志类型")
	private String logType;
	/**
	 * 操作用户名称
	 */
	@ApiModelProperty(value = "操作用户名称")
	private String creator;
	/**
	 * 权限名称
	 */
	@ApiModelProperty(value = "权限名称")
	private String actionName;

	/**
	 * 权限编码
	 */
	@ApiModelProperty(value = "权限编码")
	private String actionCode;

	/**
	 * 菜单ID集合
	 */
	@ApiModelProperty(value = "菜单ID集合")
	private List<Long> menuIdList;

	/**
	 * 菜单ID集合
	 */
	@ApiModelProperty(value = "菜单ID")
	private Long menuId;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startQueryTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endQueryTime;

	@ApiModelProperty(value = "登录IP")
	private String ip;

	@ApiModelProperty(value = "登录位置")
	private String location;

}
