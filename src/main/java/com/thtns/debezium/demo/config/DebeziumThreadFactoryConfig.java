package com.thtns.debezium.demo.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author thtns
 * @description debezium config
 * @date 2022/4/20
 */
@Slf4j
@Configuration
public class DebeziumThreadFactoryConfig {

	public static final String MYSQL_LISTENER_POOL = "MySQL-listener-pool";

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(MYSQL_LISTENER_POOL + "-%d").build();
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(CORE_POOL_SIZE);
		threadPoolTaskExecutor.setMaxPoolSize(2 * CORE_POOL_SIZE);
		threadPoolTaskExecutor.setThreadFactory(threadFactory);
		threadPoolTaskExecutor.setKeepAliveSeconds(60);
		threadPoolTaskExecutor.setQueueCapacity(256);
		threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
		return threadPoolTaskExecutor;

	}






}
