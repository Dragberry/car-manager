package net.dragberry.carmanager.application.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import net.dragberry.carmanager.service.BusinessServices;
import net.dragberry.carmanager.to.Record;

@Configuration
@Import({ DataConfig.class, ValidationConfig.class })
@ComponentScan(basePackageClasses = { BusinessServices.class, WSConfig.class })
public class BusinessConfig {

	@Bean
	public BlockingQueue<Record> recordQueue() {
		return new ArrayBlockingQueue<>(25);
	}
}
