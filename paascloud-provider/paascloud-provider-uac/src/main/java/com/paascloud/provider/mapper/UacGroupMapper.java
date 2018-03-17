package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.UacGroup;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Uac group mapper.
 *
 * @author paascloud.net@gmail.com
 */
@Mapper
@Component
public interface UacGroupMapper extends MyMapper<UacGroup> {
}