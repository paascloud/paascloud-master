package com.paascloud.provider.model.vo;

import com.paascloud.base.dto.BaseVo;
import com.paascloud.provider.model.dto.oss.ElementImgUrlDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.List;

/**
 * The class Product vo.
 *
 * @author paascloud.net @gmail.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProductVo extends BaseVo {
	private static final long serialVersionUID = 5719854343599455196L;

	/**
	 * 商品名称
	 */
	private String name;

	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 商品副标题
	 */
	private String subtitle;

	/**
	 * 产品主图,url相对地址
	 */
	@Column(name = "main_image")
	private String mainImage;

	/**
	 * 价格,单位-元保留两位小数
	 */
	private BigDecimal price;

	/**
	 * 库存数量
	 */
	private Integer stock;

	/**
	 * 商品状态.1-在售 2-下架 3-删除
	 */
	private Integer status;

	/**
	 * 图片地址,json格式,扩展用
	 */
	@Column(name = "sub_images")
	private String subImages;

	/**
	 * 商品详情
	 */
	private String detail;

	/**
	 * 分类ID
	 */
	private List<Long> categoryIdList;

	/**
	 * 图片集合
	 */
	private List<ElementImgUrlDto> imgUrlList;
}
