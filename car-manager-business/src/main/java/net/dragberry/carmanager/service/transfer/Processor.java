package net.dragberry.carmanager.service.transfer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.transferobject.Record;

@Component
@Scope("prototype")
class Processor implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
//	@Autowired
//	private CarRepo carRepo;
//	@Autowired
//	private TransactionTypeRepo transactionTypeRepo;
//	@Autowired
//	private CustomerRepo customerRepo;
//	@Autowired
//	private TransactionRepo transactionRepo;
//	@Autowired
//	private FuelRepo fuelRepo;
//	
//	private Car car;
//	
//	private Customer customer;
//
//	private BlockingQueue<Record> recordQueue;
//	
//	public void setQueue(BlockingQueue<Record> recordQueue) {
//		this.recordQueue = recordQueue;
//	}
//	
//	public Processor(String name) {
//		Thread.currentThread().setName(name);
//		System.out.println(name);
//	}
//	
//	@Override
//	public void run() {
//		try {	
//			this.car = carRepo.findOne(1L);
//			this.customer = customerRepo.findOne(3L);
//		
//			Record record= null;
//			while ((record = recordQueue.poll()) != null) {
//				processRecord(record);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * Resolves a transaction type
//	 * 
//	 * @param record
//	 * @return
//	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	private TransactionType resolveType(Record record) {
//		String type = record.getType();
//		TransactionType tType = transactionTypeRepo.findByName(type);
//		if (tType == null && StringUtils.isNotBlank(type)) {
//			tType = new TransactionType();
//			tType.setName(type);
//			tType = transactionTypeRepo.save(tType);
//		}
//		return tType;
//	}
//
//	/**
//	 * Process record
//	 * 
//	 * @param record
//	 * @return
//	 */
//	@Transactional(propagation = Propagation.REQUIRES_NEW)
//	private Transaction processRecord(Record record) {
//		Transaction transaction = new Transaction();
//		transaction.setCar(car);
//		TransactionType tType = resolveType(record);
//		transaction.setTransactionType(tType);
//		
//		return transaction;
//	}
	
}