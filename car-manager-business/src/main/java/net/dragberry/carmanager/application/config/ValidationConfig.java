package net.dragberry.carmanager.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.service.validation.TransactionValidationGroup;
import net.dragberry.carmanager.service.validation.ValidationGroup;
import net.dragberry.carmanager.service.validation.ValidationService;
import net.dragberry.carmanager.service.validation.Validator;
import net.dragberry.carmanager.service.validation.validator.AmountValidator;
import net.dragberry.carmanager.service.validation.validator.CustomerValidator;
import net.dragberry.carmanager.service.validation.validator.DateValidator;
import net.dragberry.carmanager.service.validation.validator.DescriptionValidator;
import net.dragberry.carmanager.service.validation.validator.FuelValidator;

@Configuration
@ComponentScan(basePackageClasses = { ValidationService.class })
public class ValidationConfig {
	
	@Bean
	public ValidationGroup<Car> carValidationGroup() {
		CarValidationGroup
	}

	@Bean
	public ValidationGroup<Transaction> transactionValidationGroup() {
		TransactionValidationGroup group = new TransactionValidationGroup();
		group.add(dateValidator());
		group.add(amountValidator());
		group.add(customerValidator());
		group.add(descriptionValidator());
		group.add(fuelValidator());
		return group;
	}
	
	@Bean
	public Validator<Transaction> dateValidator() {
		return new DateValidator();
	}
	
	@Bean
	public Validator<Transaction> customerValidator() {
		return new CustomerValidator();
	}
	
	@Bean
	public Validator<Transaction>  amountValidator() {
		return new AmountValidator();
	}
	
	@Bean
	public Validator<Transaction> descriptionValidator() {
		return new DescriptionValidator();
	}
	
	@Bean
	public Validator<Transaction> fuelValidator() {
		return new FuelValidator();
	}
	

}
