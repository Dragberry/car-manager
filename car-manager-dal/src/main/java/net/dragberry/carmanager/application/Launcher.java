package net.dragberry.carmanager.application;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import net.dragberry.carmanager.application.config.DataConfig;
import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Role;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.repository.CarRepo;
import net.dragberry.carmanager.repository.CustomerRepo;
import net.dragberry.carmanager.repository.FuelRepo;
import net.dragberry.carmanager.repository.TransactionRepo;
import net.dragberry.carmanager.repository.TransactionTypeRepo;

public class Launcher {
	
	public static void main(String[] args) {
		try(ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(DataConfig.class)) {
			CustomerRepo customerRepo = context.getBean(CustomerRepo.class);
			Customer customer = customerRepo.findOne(3L);
//			customer.setCustomerName("Maksim");
//			customer.setPassword("password");
//			customer.setEnabled(true);
//			customerRepo.save(customer);
			
			CarRepo carRepo = context.getBean(CarRepo.class);
			Car car = carRepo.findOne(1L);
			
			TransactionTypeRepo ttRepo = context.getBean(TransactionTypeRepo.class);
			TransactionType tt = ttRepo.findOne(1L);
			
			TransactionRepo tRepo = context.getBean(TransactionRepo.class);
			Transaction t = new Transaction();
			t.setAmount(new BigDecimal("55.50"));
			t.setExecutionDate(LocalDate.now());
			t.setCar(car);
			t.setTransactionType(tt);
			t.setCurrency("BYN");
			t.setCustomer(customer);
			t.setExchangeRate(1.996);
			tRepo.save(t);
//			Car car = new Car();
//			car.setBrand("Mersedes-Benz");
//			car.setModel("C180");
//			car.setOwner(customer);
//			car.setPurchaseDate(LocalDate.parse("2014-11-07"));
//			carRepo.save(car);
			
			FuelRepo fuelRepo = context.getBean(FuelRepo.class);
			Fuel ful = new Fuel();
			ful.setCost(1.11);
			ful.setQuantity(50);
			ful.setType("E-92");
			ful.setTransaction(t);
			fuelRepo.save(ful);
			
			
			
			
			Customer newCust = customerRepo.findOne(1L);
			System.out.println(newCust.getCustomerName());
			System.out.println(newCust.getPassword());
			System.out.println(newCust.getEnabled());
			for (Role role : newCust.getRoles()) {
				System.out.println(role.getRoleName());
			}
		}
	}
}
