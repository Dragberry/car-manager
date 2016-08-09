package net.dragberry.carmanager.service.transfer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.repository.CarRepo;
import net.dragberry.carmanager.repository.CustomerRepo;
import net.dragberry.carmanager.repository.FuelRepo;
import net.dragberry.carmanager.repository.TransactionRepo;
import net.dragberry.carmanager.repository.TransactionTypeRepo;
import net.dragberry.carmanager.transferobject.Record;

@Component
@Scope("prototype")
class Processor implements Callable<Object> {
	
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
	
	private Car car;
	
	private Customer customer;

	private BlockingQueue<Record> recordQueue;
	
	public void setQueue(BlockingQueue<Record> recordQueue) {
		this.recordQueue = recordQueue;
	}
	
	@Override
	public Object call() throws Exception {
		try {	
			this.car = carRepo.findOne(1L);
			this.customer = customerRepo.findOne(3L);
		
			Record record= null;
			while ((record = recordQueue.poll(1, TimeUnit.SECONDS)) != null) {
				processRecord(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Resolves a transaction type
	 * 
	 * @param record
	 * @return
	 */
	private TransactionType resolveType(Record record) {
		String type = record.getType();
		TransactionType tType = transactionTypeRepo.findByName(type);
		if (tType == null) {
			tType = new TransactionType();
			tType.setName(type);
			tType = transactionTypeRepo.save(tType);
		}
		return tType;
	}

	/**
	 * Process record
	 * 
	 * @param record
	 * @return
	 */
	private Transaction processRecord(Record record) {
		Transaction transaction = new Transaction();
		transaction.setCar(car);
		TransactionType tType = resolveType(record);
		transaction.setTransactionType(tType);
		
		return transaction;
	}
	
}