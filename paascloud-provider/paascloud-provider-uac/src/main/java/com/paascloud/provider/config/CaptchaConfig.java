package com.paascloud.provider.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * The class Captcha config.
 *
 * @author paascloud.net @gmail.com
 */
@Configuration
public class CaptchaConfig {

	/**
	 * Get kaptcha bean default kaptcha.
	 *
	 * @return the default kaptcha
	 */
	@Bean(name = "captchaProducer")
	public DefaultKaptcha getKaptchaBean() {
		DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "no");
		properties.setProperty("kaptcha.border.color", "no");
		properties.setProperty("kaptcha.border.color", "220,227,232");
		properties.setProperty("kaptcha.textproducer.char.string", "2345689");
		properties.setProperty("kaptcha.textproducer.font.color", "black");
		properties.setProperty("kaptcha.textproducer.font.size", "16");
		properties.setProperty("kaptcha.image.width", "106");
		properties.setProperty("kaptcha.image.height", "30");
		properties.setProperty("kaptcha.session.key", "kaptchaCode");
		properties.setProperty("kaptcha.textproducer.char.length", "4");
		properties.setProperty("kaptcha.background.clear.from", "white");
		properties.setProperty("kaptcha.background.clear.to", "white");
		properties.setProperty("kaptcha.textproducer.char.space", "5");
		properties.setProperty("kaptcha.obscurificator.impl", "com.google.code.kaptcha.impl.ShadowGimpy");
		properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
		properties.setProperty("kaptcha.textproducer.font.names", "Verdana");
		Config config = new Config(properties);
		defaultKaptcha.setConfig(config);
		return defaultKaptcha;
	}
}
