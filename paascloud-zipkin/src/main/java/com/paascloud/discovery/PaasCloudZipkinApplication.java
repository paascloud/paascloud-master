package com.paascloud.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.sleuth.zipkin.stream.EnableZipkinStreamServer;

/**
 * The class Paas cloud zipkin application.
 *
 * @author paascloud.net@gmail.com
 */
@EnableEurekaClient
@SpringBootApplication
@EnableZipkinStreamServer
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
