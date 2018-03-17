package com.paascloud.provider;

import com.paascloud.base.constant.GlobalConstant;
import com.paascloud.provider.model.domain.UacRole;
import com.paascloud.provider.service.UacRoleService;
import org.junit.Test;

import javax.annotation.Resource;


/**
 * The class Uac role service test.
 * @author paascloud.net@gmail.com
 */
public class UacRoleServiceTest extends PaasCloudUacApplicationTests {
	@Resource
	private UacRoleService uacRoleService;

	/**
	 * Find by login name test.
	 */
	@Test
	public void findByLoginNameTest() {
		UacRole admin = uacRoleService.findByRoleCode(GlobalConstant.Sys.SUPER_MANAGER_LOGIN_NAME);
		logger.info("findByLoginNameTest = {}", admin);
	}
}
