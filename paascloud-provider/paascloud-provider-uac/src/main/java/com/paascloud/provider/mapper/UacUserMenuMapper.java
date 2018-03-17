package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacUserMenu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Uac user menu mapper.
 *
 * @author paascloud.net@gmail.com
 */
@Mapper
@Component
public interface UacUserMenuMapper extends MyMapper<UacUserMenu> {
}