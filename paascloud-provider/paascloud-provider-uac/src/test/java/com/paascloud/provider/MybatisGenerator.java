package com.paascloud.provider;

import com.sun.tools.corba.se.idl.Generator;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * The class Mybatis generator.
 * @author paascloud.net@gmail.com
 */
public class MybatisGenerator {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 *
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(
				Generator.class.getResourceAsStream("/generator/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
	}
}
