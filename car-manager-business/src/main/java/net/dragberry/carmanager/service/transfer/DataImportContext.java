package net.dragberry.carmanager.service.transfer;

import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.repository.CarRepo;
import net.dragberry.carmanager.repository.CustomerRepo;
import net.dragberry.carmanager.repository.FuelRepo;
import net.dragberry.carmanager.repository.TransactionRepo;
import net.dragberry.carmanager.repository.TransactionTypeRepo;

/**
 * Data importer context
 * 
 * @author Maxim Drahun
 *
 */
public class DataImportContext {
	
	private CarRepo carRepo;
	private TransactionTypeRepo transactionTypeRepo;
	private CustomerRepo customerRepo;
	private TransactionRepo transactionRepo;
	private FuelRepo fuelRepo;
	
	private Car car;
	private Customer customer;
	
	public DataImportContext(
			CarRepo carRepo, 
			TransactionTypeRepo transactionTypeRepo, 
			CustomerRepo customerRepo,
			TransactionRepo transactionRepo, 
			FuelRepo fuelRepo,
			Long carKey,
			Long customerKey) {
		this.carRepo = carRepo;
		this.transactionTypeRepo = transactionTypeRepo;
		this.customerRepo = customerRepo;
		this.transactionRepo = transactionRepo;
		this.fuelRepo = fuelRepo;
		this.car = carRepo.findOne(carKey);
		this.customer = customerRepo.findOne(customerKey);
	}

	public CarRepo getCarRepo() {
		return carRepo;
	}

	public TransactionTypeRepo getTransactionTypeRepo() {
		return transactionTypeRepo;
	}

	public CustomerRepo getCustomerRepo() {
		return customerRepo;
	}

	public TransactionRepo getTransactionRepo() {
		return transactionRepo;
	}

	public FuelRepo getFuelRepo() {
		return fuelRepo;
	}

	public Car getCar() {
		return car;
	}

	public Customer getCustomer() {
		return customer;
	}
	
}
