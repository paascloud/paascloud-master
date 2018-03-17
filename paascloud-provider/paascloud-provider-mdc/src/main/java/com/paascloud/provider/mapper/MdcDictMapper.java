package com.paascloud.provider.mapper;

import com.paascloud.core.mybatis.MyMapper;
import com.paascloud.provider.model.domain.MdcDict;
import com.paascloud.provider.model.vo.MdcDictVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The interface Mdc dict mapper.
 *
 * @author paascloud.net @gmail.com
 */
@Mapper
@Component
public interface MdcDictMapper extends MyMapper<MdcDict> {
	/**
	 * List dict vo list.
	 *
	 * @return the list
	 */
	List<MdcDictVo> listDictVo();
}