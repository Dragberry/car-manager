package net.dragberry.carmanager.service.transfer;

import java.text.MessageFormat;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.dao.TransactionTypeDao;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.transferobject.Record;

@Component
@Scope(value = "prototype")
public class Consumer implements Callable<Integer>{
	
	@Autowired
	private BlockingQueue<Record> queue;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private TransactionTypeDao transactionTypeDao;

	private Context context = new Context();
	
	@Override
	public Integer call() throws Exception {
		try {
			Customer customer = customerDao.findOne(3L);
			context.setCustomer(customer);
			
			Record record= null;
			while ((record = queue.poll(3, TimeUnit.SECONDS)) != null) {
				System.out.println(Thread.currentThread().getName() + " " + record);
				processRecord(record);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		return null;
	}
	
	private Transaction processRecord(Record record) {
		Transaction transaction = new Transaction();
		TransactionType tType = resolveType(record);
		transaction.setTransactionType(tType);
		transaction.setCustomer(context.getCustomer());
		
		return transaction;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private TransactionType resolveType(Record record) {
		String type = record.getType();
		System.out.println(MessageFormat.format("Record [{0}] has type [{1}]", record.getIndex(), type));
		TransactionType tType = null;
		if (StringUtils.isNotBlank(type)) {
			for (;;) {
				try {
					tType = transactionTypeDao.findByName(type);
					if (tType == null) {
						tType = new TransactionType();
						tType.setName(type);
						return transactionTypeDao.create(tType);
					} else {
						return tType;
					}
				} catch (Exception cve) {
					System.out.println(MessageFormat.format("Duplicate entry found for record [{0}] with type [{1}]", record.getIndex(), record.getType()));
				}
			}
		}
		System.out.println(MessageFormat.format("Transaction type for record [{0}] is not resolved", record.getIndex()));
		return null;
	}

	private static class Context {
		private Customer customer;

		public Customer getCustomer() {
			return customer;
		}

		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
		
	}
}
