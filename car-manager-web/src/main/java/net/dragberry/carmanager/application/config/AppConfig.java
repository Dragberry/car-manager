package net.dragberry.carmanager.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
public class AppConfig {

	@Bean
	public CommonAnnotationBeanPostProcessor postProcessor() {
		return new CommonAnnotationBeanPostProcessor();
	}
}
