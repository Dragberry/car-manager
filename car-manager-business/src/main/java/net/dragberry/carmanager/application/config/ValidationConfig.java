package net.dragberry.carmanager.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.service.validation.TransactionValidationGroup;
import net.dragberry.carmanager.service.validation.ValidationGroup;
import net.dragberry.carmanager.service.validation.ValidationService;
import net.dragberry.carmanager.service.validation.validator.AmountValidator;
import net.dragberry.carmanager.service.validation.validator.CustomerValidator;
import net.dragberry.carmanager.service.validation.validator.DateValidator;

@Configuration
@ComponentScan(basePackageClasses = { ValidationService.class })
public class ValidationConfig {

	@Bean
	public ValidationGroup<Transaction> transactionValidationGroup() {
		TransactionValidationGroup group = new TransactionValidationGroup();
		group.add(dateValidator());
		group.add(amountValidator());
		group.add(customerValidator());
		return group;
	}
	
	@Bean
	public DateValidator dateValidator() {
		return new DateValidator();
	}
	
	@Bean
	public CustomerValidator customerValidator() {
		return new CustomerValidator();
	}
	
	public AmountValidator amountValidator() {
		return new AmountValidator();
	}
	

}
