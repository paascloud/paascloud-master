package com.paascloud.discovery.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * The class Paas cloud eureka application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableEurekaServer
@SpringBootApplication
public class PaasCloudEurekaApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(PaasCloudEurekaApplication.class, args);
	}
}
