/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：PaginationPlugin.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.provider.main;

import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.ShellRunner;

import java.util.List;

/**
 * The class Pagination plugin.
 *
 * @author paascloud.net@gmail.com
 */
public class PaginationPlugin extends PluginAdapter {

	/**
	 * Validate boolean.
	 *
	 * @param warnings the warnings
	 *
	 * @return the boolean
	 */
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	private static void generate() {
		String config = PaginationPlugin.class.getClassLoader().getResource("generator/generatorConfig-B.xml").getFile();
		String[] arg = {"-configfile", config, "-overwrite"};
		ShellRunner.main(arg);
	}

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		generate();
	}
}