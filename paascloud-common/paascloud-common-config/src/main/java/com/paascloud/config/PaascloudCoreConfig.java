package com.paascloud.config;


import com.paascloud.config.properties.PaascloudProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The class Aliyun core config.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
@EnableConfigurationProperties(PaascloudProperties.class)
public class PaascloudCoreConfig {
}
