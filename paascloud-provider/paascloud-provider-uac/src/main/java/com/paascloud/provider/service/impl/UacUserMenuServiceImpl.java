package com.paascloud.provider.service.impl;


import com.paascloud.core.support.BaseService;
import com.paascloud.provider.model.domain.UacUserMenu;
import com.paascloud.provider.service.UacUserMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The class Uac user menu service.
 *
 * @author paascloud.net@gmail.com
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UacUserMenuServiceImpl extends BaseService<UacUserMenu> implements UacUserMenuService {
}
