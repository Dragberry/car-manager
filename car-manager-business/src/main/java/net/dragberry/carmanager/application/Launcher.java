package net.dragberry.carmanager.application;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.dragberry.carmanager.application.config.BusinessConfig;
import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.transferobject.FuelTO;
import net.dragberry.carmanager.transferobject.TransactionTO;

public class Launcher {

	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(BusinessConfig.class)) {
			TransactionService transactionService = context.getBean(TransactionService.class);
			TransactionTO to = new TransactionTO();
			to.setAmount(new BigDecimal("55.50"));
			to.setCarKey(1L);
			to.setCurrency("BYN");
			to.setCustomerKey(3L);
			to.setExchangeRate(1.996);
			to.setExecutionDate(LocalDate.now());
			
			FuelTO fuel = new FuelTO();
			fuel.setCost(1.11);
			fuel.setQuantity(50.0);
			fuel.setType("E-92");
			to.setFuel(fuel);
			to.setTransactionTypeKey(1L);
			transactionService.createTransaction(to);
		}
	}

}
