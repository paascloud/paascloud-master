package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.PtcPayInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * The interface Ptc pay info mapper.
 *
 * @author paascloud.net@gmail.com
 */
@Mapper
@Component
public interface PtcPayInfoMapper extends MyMapper<PtcPayInfo> {
}