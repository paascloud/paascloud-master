package com.paascloud.provider;

import com.paascloud.provider.model.vo.MenuVo;
import com.paascloud.provider.service.UacMenuService;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

public class UacMenuServiceTest extends PaasCloudUacApplicationTests {
	@Resource
	private UacMenuService uacMenuService;

	@Test
	public void findMenuListByUserIdTest() {
		List<MenuVo> menuVoListByUserId = uacMenuService.getMenuVoList(1L, 1L);
		logger.info("findByLoginNameTest = {}", menuVoListByUserId);
	}
}
