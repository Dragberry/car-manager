package net.dragberry.carmanager.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import net.dragberry.carmanager.ws.client.WebServiceClient;

@Configuration
@ComponentScan(basePackageClasses = WebServiceClient.class)
public class WSConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
