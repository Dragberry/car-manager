package net.dragberry.carmanager.application;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.dragberry.carmanager.application.config.DataConfig;

public class Launcher {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class);
		context.close();
	}
}
