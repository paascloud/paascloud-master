package com.paascloud.core.config;

import com.paascloud.config.properties.PaascloudProperties;
import com.paascloud.core.registry.RegistryCenterFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * The class Redis init runner.
 *
 * @author paascloud.net @gmail.com
 */
@Component
@Order
@Slf4j
public class ZookeeperInitRunner implements CommandLineRunner {
	@Resource
	private PaascloudProperties paascloudProperties;
	@Value("${spring.application.name}")
	private String applicationName;

	/**
	 * Run.
	 *
	 * @param args the args
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void run(String... args) throws Exception {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		log.info("###ZookeeperInitRunner，init. HostAddress={}, applicationName={}", hostAddress, applicationName);
		RegistryCenterFactory.startup(paascloudProperties, hostAddress, applicationName);
		log.info("###ZookeeperInitRunner，finish<<<<<<<<<<<<<");
	}

}