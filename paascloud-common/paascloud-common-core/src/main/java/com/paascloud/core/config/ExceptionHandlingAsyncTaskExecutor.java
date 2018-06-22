/*
 * Copyright (c) 2018. paascloud.net All Rights Reserved.
 * 项目名称：paascloud快速搭建企业级分布式微服务平台
 * 类名称：ExceptionHandlingAsyncTaskExecutor.java
 * 创建人：刘兆明
 * 联系方式：paascloud.net@gmail.com
 * 开源地址: https://github.com/paascloud
 * 博客地址: http://blog.paascloud.net
 * 项目官网: http://paascloud.net
 */

package com.paascloud.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * The class Exception handling async task executor.
 *
 * @author paascloud.net @gmail.com
 */
@Slf4j
public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor, InitializingBean, DisposableBean {

	private final AsyncTaskExecutor executor;

	/**
	 * Instantiates a new Exception handling async task executor.
	 *
	 * @param executor the executor
	 */
	ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
		this.executor = executor;
	}

	/**
	 * Execute.
	 *
	 * @param task the task
	 */
	@Override
	public void execute(Runnable task) {
		executor.execute(createWrappedRunnable(task));
	}

	/**
	 * Execute.
	 *
	 * @param task         the task
	 * @param startTimeout the start timeout
	 */
	@Override
	public void execute(Runnable task, long startTimeout) {
		executor.execute(createWrappedRunnable(task), startTimeout);
	}

	private <T> Callable<T> createCallable(final Callable<T> task) {
		return () -> {
			try {
				return task.call();
			} catch (Exception e) {
				handle(e);
				throw e;
			}
		};
	}

	private Runnable createWrappedRunnable(final Runnable task) {
		return () -> {
			try {
				task.run();
			} catch (Exception e) {
				handle(e);
			}
		};
	}

	/**
	 * Handle.
	 *
	 * @param e the e
	 */
	private void handle(Exception e) {
		log.error("Caught async exception", e);
	}

	/**
	 * Submit future.
	 *
	 * @param task the task
	 *
	 * @return the future
	 */
	@Override
	public Future<?> submit(Runnable task) {
		return executor.submit(createWrappedRunnable(task));
	}

	/**
	 * Submit future.
	 *
	 * @param <T>  the type parameter
	 * @param task the task
	 *
	 * @return the future
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(createCallable(task));
	}

	/**
	 * Destroy.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void destroy() throws Exception {
		if (executor instanceof DisposableBean) {
			DisposableBean bean = (DisposableBean) executor;
			bean.destroy();
		}
	}

	/**
	 * After properties set.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (executor instanceof InitializingBean) {
			InitializingBean bean = (InitializingBean) executor;
			bean.afterPropertiesSet();
		}
	}
}
