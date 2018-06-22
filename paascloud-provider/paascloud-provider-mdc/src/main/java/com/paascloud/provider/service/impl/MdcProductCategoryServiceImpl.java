/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：MdcProductCategoryServiceImpl.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.paascloud.PublicUtil;
import com.paascloud.base.dto.LoginAuthDto;
import com.paascloud.base.dto.UpdateStatusDto;
import com.paascloud.base.enums.ErrorCodeEnum;
import com.paascloud.core.support.BaseService;
import com.paascloud.core.support.TreeUtils;
import com.paascloud.provider.exceptions.MdcBizException;
import com.paascloud.provider.mapper.MdcProductCategoryMapper;
import com.paascloud.provider.model.domain.MdcProductCategory;
import com.paascloud.provider.model.dto.ProductCategoryDto;
import com.paascloud.provider.model.enums.MdcCategoryStatusEnum;
import com.paascloud.provider.model.vo.MdcCategoryVo;
import com.paascloud.provider.service.MdcProductCategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * The class Mdc product category service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class MdcProductCategoryServiceImpl extends BaseService<MdcProductCategory> implements MdcProductCategoryService {
	@Resource
	private MdcProductCategoryMapper mdcProductCategoryMapper;

	@Override
	public List<ProductCategoryDto> getCategoryDtoList(Long categoryId) {
		return mdcProductCategoryMapper.selectCategoryDtoList(categoryId);
	}

	@Override
	public List<Long> selectCategoryAndChildrenById(Long categoryId) {
		Set<MdcProductCategory> categorySet = Sets.newHashSet();
		findChildCategory(categorySet, categoryId);


		List<Long> categoryIdList = Lists.newArrayList();
		if (categoryId != null) {
			for (MdcProductCategory categoryItem : categorySet) {
				categoryIdList.add(categoryItem.getId());
			}
		}
		return categoryIdList;
	}

	@Override
	public MdcProductCategory getByCategoryId(Long categoryId) {
		Preconditions.checkArgument(categoryId != null, "分类ID不能为空");

		MdcProductCategory query = new MdcProductCategory();
		query.setId(categoryId);
		return mdcProductCategoryMapper.selectOne(query);
	}

	@Override
	public List<MdcCategoryVo> getCategoryTreeList() {
		List<MdcCategoryVo> list = mdcProductCategoryMapper.listCategoryVo();
		return new TreeUtils().getChildTreeObjects(list, 0L);
	}

	@Override
	public MdcCategoryVo getMdcCategoryVoById(final Long categoryId) {
		MdcProductCategory category = mdcProductCategoryMapper.selectByPrimaryKey(categoryId);

		if (category == null) {
			logger.error("找不到数据字典信息id={}", categoryId);
			throw new MdcBizException(ErrorCodeEnum.MDC10023001, categoryId);
		}

		// 获取父级菜单信息
		MdcProductCategory parentCategory = mdcProductCategoryMapper.selectByPrimaryKey(category.getPid());

		ModelMapper modelMapper = new ModelMapper();
		MdcCategoryVo categoryVo = modelMapper.map(category, MdcCategoryVo.class);
		categoryVo.setId(category.getId());
		categoryVo.setPid(category.getPid());
		if (parentCategory != null) {
			categoryVo.setParentCategoryName(parentCategory.getName());
		}

		return categoryVo;
	}

	@Override
	public void updateMdcCategoryStatusById(final UpdateStatusDto updateStatusDto, final LoginAuthDto loginAuthDto) {
		Long id = updateStatusDto.getId();
		Integer status = updateStatusDto.getStatus();
		// 要处理的菜单集合
		List<MdcProductCategory> mdcCategoryList = Lists.newArrayList();

		int result;
		if (status.equals(MdcCategoryStatusEnum.DISABLE.getType())) {
			// 获取菜单以及子菜单
			mdcCategoryList = this.getAllCategoryFolder(id, MdcCategoryStatusEnum.ENABLE.getType());
		} else {
			// 获取菜单、其子菜单以及父菜单
			MdcProductCategory uacMenu = new MdcProductCategory();
			uacMenu.setPid(id);
			result = this.selectCount(uacMenu);
			// 此菜单含有子菜单
			if (result > 0) {
				mdcCategoryList = this.getAllCategoryFolder(id, MdcCategoryStatusEnum.DISABLE.getType());
			}
			List<MdcProductCategory> categoryListTemp = this.getAllParentCategoryFolderByMenuId(id);
			for (MdcProductCategory category : categoryListTemp) {
				if (!mdcCategoryList.contains(category)) {
					mdcCategoryList.add(category);
				}
			}
		}

		this.updateCategoryStatus(mdcCategoryList, loginAuthDto, status);
	}

	@Override
	public void saveMdcCategory(final MdcProductCategory mdcCategory, final LoginAuthDto loginAuthDto) {
		Long pid = mdcCategory.getPid();
		mdcCategory.setUpdateInfo(loginAuthDto);
		MdcProductCategory parentMenu = mapper.selectByPrimaryKey(pid);
		if (PublicUtil.isEmpty(parentMenu)) {
			throw new MdcBizException(ErrorCodeEnum.MDC10023002, pid);
		}
		if (mdcCategory.isNew()) {
			MdcProductCategory updateMenu = new MdcProductCategory();
			updateMenu.setId(pid);
			Long categoryId = super.generateId();
			mdcCategory.setId(categoryId);
			mapper.insertSelective(mdcCategory);
		} else {
			mapper.updateByPrimaryKeySelective(mdcCategory);
		}
	}

	@Override
	public boolean checkCategoryHasChildCategory(final Long categoryId) {
		logger.info("检查数据字典id={}是否存在生效节点", categoryId);
		MdcProductCategory uacMenu = new MdcProductCategory();
		uacMenu.setStatus(MdcCategoryStatusEnum.ENABLE.getType());
		uacMenu.setPid(categoryId);

		return mapper.selectCount(uacMenu) > 0;
	}

	/**
	 * 递归算法,算出子节点
	 */
	private Set<MdcProductCategory> findChildCategory(Set<MdcProductCategory> categorySet, Long categoryId) {
		MdcProductCategory category = mdcProductCategoryMapper.selectByPrimaryKey(categoryId);
		if (category != null) {
			categorySet.add(category);
		}
		//查找子节点,递归算法一定要有一个退出的条件
		List<MdcProductCategory> categoryList = this.getProductCategoryListByPid(categoryId);
		for (MdcProductCategory categoryItem : categoryList) {
			findChildCategory(categorySet, categoryItem.getId());
		}
		return categorySet;
	}

	@Override
	public List<MdcProductCategory> getProductCategoryListByPid(Long pid) {
		Preconditions.checkArgument(pid != null, "pid is null");
		MdcProductCategory query = new MdcProductCategory();
		query.setPid(pid);

		return mdcProductCategoryMapper.select(query);
	}

	private void updateCategoryStatus(List<MdcProductCategory> mdcCategoryList, LoginAuthDto loginAuthDto, int status) {
		MdcProductCategory update = new MdcProductCategory();
		for (MdcProductCategory category : mdcCategoryList) {
			update.setId(category.getId());
			update.setVersion(category.getVersion() + 1);
			update.setStatus(status);
			update.setUpdateInfo(loginAuthDto);
			int result = mapper.updateByPrimaryKeySelective(update);
			if (result < 1) {
				throw new MdcBizException(ErrorCodeEnum.MDC10023003, category.getId());
			}
		}
	}

	private List<MdcProductCategory> getAllCategoryFolder(Long id, int categoryStatus) {
		MdcProductCategory mdcCategory = new MdcProductCategory();
		mdcCategory.setId(id);
		mdcCategory = mapper.selectOne(mdcCategory);
		List<MdcProductCategory> mdcCategoryList = Lists.newArrayList();
		mdcCategoryList = buildNode(mdcCategoryList, mdcCategory, categoryStatus);
		return mdcCategoryList;
	}

	private List<MdcProductCategory> getAllParentCategoryFolderByMenuId(Long categoryId) {
		MdcProductCategory mdcCategoryQuery = new MdcProductCategory();
		mdcCategoryQuery.setId(categoryId);
		mdcCategoryQuery = mapper.selectOne(mdcCategoryQuery);
		List<MdcProductCategory> mdcCategoryList = Lists.newArrayList();
		mdcCategoryList = buildParentNote(mdcCategoryList, mdcCategoryQuery);
		return mdcCategoryList;
	}

	/**
	 * 递归获取菜单的子节点
	 */
	private List<MdcProductCategory> buildNode(List<MdcProductCategory> mdcCategoryList, MdcProductCategory uacMenu, int categoryStatus) {
		List<MdcProductCategory> uacMenuQueryList = mapper.select(uacMenu);
		MdcProductCategory uacMenuQuery;
		for (MdcProductCategory category : uacMenuQueryList) {
			if (categoryStatus == category.getStatus()) {
				mdcCategoryList.add(category);
			}
			uacMenuQuery = new MdcProductCategory();
			uacMenuQuery.setPid(category.getId());
			buildNode(mdcCategoryList, uacMenuQuery, categoryStatus);
		}
		return mdcCategoryList;
	}

	/**
	 * 递归获取菜单的父菜单
	 */
	private List<MdcProductCategory> buildParentNote(List<MdcProductCategory> mdcCategoryList, MdcProductCategory mdcCategory) {
		List<MdcProductCategory> mdcCategoryQueryList = mapper.select(mdcCategory);
		MdcProductCategory uacMenuQuery;
		for (MdcProductCategory category : mdcCategoryQueryList) {
			if (MdcCategoryStatusEnum.DISABLE.getType() == category.getStatus()) {
				mdcCategoryList.add(category);
			}
			uacMenuQuery = new MdcProductCategory();
			uacMenuQuery.setId(category.getPid());
			buildParentNote(mdcCategoryList, uacMenuQuery);
		}
		return mdcCategoryList;
	}
}
