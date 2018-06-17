package com.paascloud.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import zipkin.server.EnableZipkinServer;

/**
 * The class Paas cloud zipkin application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableZipkinServer
public class PaasCloudZipkinApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PaasCloudZipkinApplication.class, args);
	}
}
