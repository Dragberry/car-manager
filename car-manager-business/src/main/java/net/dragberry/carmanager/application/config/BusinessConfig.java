package net.dragberry.carmanager.application.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import net.dragberry.carmanager.service.BusinessServices;
import net.dragberry.carmanager.transferobject.Record;

@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackageClasses = BusinessServices.class)
public class BusinessConfig {

	@Bean
	public BlockingQueue<Record> recordQueue() {
		return new ArrayBlockingQueue<>(25);
	}
}
