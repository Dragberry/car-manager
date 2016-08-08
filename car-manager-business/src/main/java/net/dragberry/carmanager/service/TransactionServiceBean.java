package net.dragberry.carmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.repository.CarRepo;
import net.dragberry.carmanager.repository.CustomerRepo;
import net.dragberry.carmanager.repository.FuelRepo;
import net.dragberry.carmanager.repository.TransactionRepo;
import net.dragberry.carmanager.repository.TransactionTypeRepo;
import net.dragberry.carmanager.transferobject.TransactionTO;

/**
 * Transaction service bean
 * 
 * @author Maksim Drahun
 *
 */
@Service
public class TransactionServiceBean implements TransactionService {
	
	@Autowired
	private CarRepo carRepo;
	@Autowired
	private TransactionTypeRepo transactionTypeRepo;
	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private TransactionRepo transactionRepo;
	@Autowired
	private FuelRepo fuelRepo;

	@Override
	public TransactionTO createTransaction(TransactionTO to) {
		Transaction transaction = new Transaction();
		transaction.setAmount(to.getAmount());
		transaction.setCurrency(to.getCurrency());
		transaction.setExchangeRate(to.getExchangeRate());
		transaction.setExecutionDate(to.getExecutionDate());
		
		Car car = carRepo.findOne(to.getCarKey());
		transaction.setCar(car);
		TransactionType tType = transactionTypeRepo.findOne(to.getTransactionTypeKey());
		transaction.setTransactionType(tType);
		Customer customer = customerRepo.findOne(to.getCustomerKey());
		transaction.setCustomer(customer);
		
		transaction = transactionRepo.save(transaction);
		if (to.getFuel() != null) {
			Fuel fuel = new Fuel();
			fuel.setCost(to.getFuel().getCost());
			fuel.setTransaction(transaction);
			fuel.setQuantity(to.getFuel().getQuantity());
			fuel.setType(to.getFuel().getType());
			fuel = fuelRepo.save(fuel);
			to.getFuel().setFuelKey(fuel.getEntityKey());
		}

		to.setTransactionKey(transaction.getEntityKey());
		return to;
	}

}
