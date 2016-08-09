package net.dragberry.carmanager.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import net.dragberry.carmanager.service.BusinessServices;

@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackageClasses = BusinessServices.class)
public class BusinessConfig {

	@Bean
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() / 4);
		taskExecutor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() / 4);
		taskExecutor.setQueueCapacity(25);
		return taskExecutor;
	}
}
