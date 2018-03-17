package com.paascloud.discovery;

import org.springframework.cloud.sleuth.Sampler;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.cloud.sleuth.stream.StreamSpanReporter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * The class Custom poller configuration.
 *
 * @author paascloud.net@gmail.com
 */
@Configuration
public class CustomPollerConfiguration {
	/**
	 * Custom poller poller metadata.
	 *
	 * @return the poller metadata
	 */
	@Bean(name = StreamSpanReporter.POLLER)
	PollerMetadata customPoller() {
		PollerMetadata poller = new PollerMetadata();
		poller.setMaxMessagesPerPoll(500);
		poller.setTrigger(new PeriodicTrigger(5000L));
		return poller;
	}

	@Bean
	public Sampler defaultSampler() {
		return  new AlwaysSampler();
	}
}
