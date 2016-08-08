package net.dragberry.carmanager.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import net.dragberry.carmanager.service.BusinessServices;

@Configuration
@Import(DataConfig.class)
@ComponentScan(basePackageClasses = BusinessServices.class)
public class BusinessConfig {

}
