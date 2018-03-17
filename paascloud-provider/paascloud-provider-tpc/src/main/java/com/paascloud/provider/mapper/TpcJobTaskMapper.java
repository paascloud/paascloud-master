package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.TpcJobTask;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Tpc job task mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface TpcJobTaskMapper extends MyMapper<TpcJobTask> {
}