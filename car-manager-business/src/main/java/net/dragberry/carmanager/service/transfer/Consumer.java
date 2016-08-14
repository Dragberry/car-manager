package net.dragberry.carmanager.service.transfer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.dao.CarDao;
import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.dao.FuelDao;
import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.dao.TransactionTypeDao;
import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.transferobject.Record;

@Component
@Scope(value = "prototype")
public class Consumer implements Callable<Integer>{
	
	private static final String FUEL_REGEXP = "^([0-9]+([,.][0-9]+)*)";
	@Autowired
	private BlockingQueue<Record> queue;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private TransactionTypeDao transactionTypeDao;
	@Autowired
	private CarDao carDao;
	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private FuelDao fuelDao;

	private Context context = new Context();
	
	@Override
	public Integer call() throws Exception {
		try {
			context.addCustomer(3L, customerDao.findOne(3L));
			context.addCustomer(4L, customerDao.findOne(4L));
			context.addCustomer(5L, customerDao.findOne(5L));
			context.setCar(carDao.findOne(1L));
			
			Record record= null;
			while ((record = queue.poll(3, TimeUnit.SECONDS)) != null) {
//				System.out.println(Thread.currentThread().getName() + " " + record);
				if (isRecordValid(record)) {
					processRecord(record);
				} else {
					System.out.println(MessageFormat.format("Record [{0}] is not valid", record.getIndex()));
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		System.out.println(MessageFormat.format("Thread [{0}] has been finished!", Thread.currentThread().getName()));
		return 1;
	}
	
	private boolean isRecordValid(Record record) {
		boolean isValid = true 
				&& record.getIndex() != null
				&& record.getDate() != null;
		return isValid;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private List<Transaction> processRecord(Record record) {
		List<Transaction> list = new ArrayList<>();
		try {
			resolveTransactionCount(record).forEach(currencyCustomer -> {
				Transaction transaction = new Transaction();
				transaction.setCar(context.getCar());
				transaction.setExecutionDate(record.getDate());
				transaction.setDescription(record.getDescription());
				transaction.setCurrency(currencyCustomer.getCurrency());
				transaction.setAmount(resolveAmount(record, currencyCustomer));
				transaction.setExchangeRate(record.getExchangeRate());
				TransactionType tType = resolveType(record);
				transaction.setTransactionType(tType);
				transaction.setCustomer(getCustomer(record, currencyCustomer));
				transactionDao.create(transaction);
				if (TransactionType.FUEL.equals(tType.getName())) {
					Fuel fuel = createFuel(record, transaction);
//					System.out.println(fuel);
				}
//				System.out.println(transaction);
				list.add(transaction);

			});
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
			exc.printStackTrace();
		}
		
		return list;
	}
	
	private Customer getCustomer(Record record, CurrencyCustomer currencyCustomer) {
		switch (currencyCustomer) {
		case BYR:
		case USD:
			return context.getCustomer(record.getDescription().endsWith("получено") ? 5L : 3L);
		case BYR_DAD:
		case USD_DAD:
			return context.getCustomer(4L);
		default:
			throw new IllegalArgumentException(MessageFormat.format("Invalid value for CurrencyCustomer [{0}]!", currencyCustomer));
		}
	}

	private Fuel createFuel(Record record, Transaction transaction) {
		Fuel fuel = new Fuel();
		Pattern pattern = Pattern.compile(FUEL_REGEXP);
		Matcher matcher = pattern.matcher(record.getDescription());
		if (matcher.find()) {
			double quantity = Double.parseDouble(matcher.group().replace(',', '.'));
			fuel.setQuantity(quantity);
			fuel.setCost(transaction.getAmount().divide(new BigDecimal(quantity), 2, RoundingMode.HALF_UP));
			fuel.setType("92");
			fuel.setTransaction(transaction);
			fuelDao.create(fuel);
		} else {
			throw new RuntimeException(MessageFormat.format("Cannot parse fueld for record [{0}]!", record.getIndex()));
		}
		return fuel;
	}

	private List<CurrencyCustomer> resolveTransactionCount(Record record) {
		List<CurrencyCustomer> list = new ArrayList<>(CurrencyCustomer.values().length);
		if (record.getCostBYR() != 0) {
			list.add(CurrencyCustomer.BYR);
		}
		if (record.getCostUSD() != 0) {
			list.add(CurrencyCustomer.USD);
		}
		if (record.getCostBYRDad() != 0) {
			list.add(CurrencyCustomer.BYR_DAD);
		}
		if (record.getCostUSDDad() != 0) {
			list.add(CurrencyCustomer.USD_DAD);;
		}
		return list;
	}

	private double denominate(double byr) {
		return byr / 10000;
	}
	
	private BigDecimal resolveAmount(Record record, CurrencyCustomer currencyCustomer) {
		switch (currencyCustomer) {
		case BYR:
			return new BigDecimal(denominate(record.getCostBYR())).setScale(2, RoundingMode.HALF_UP);
		case BYR_DAD:
			return new BigDecimal(denominate(record.getCostBYRDad())).setScale(2, RoundingMode.HALF_UP);
		case USD:
			return new BigDecimal(record.getCostUSD()).setScale(2, RoundingMode.HALF_UP);
		case USD_DAD:
			return new BigDecimal(record.getCostUSDDad()).setScale(2, RoundingMode.HALF_UP);
		default:
			throw new RuntimeException(MessageFormat.format("Amount cannot be resolved for record [{0}]", record.getIndex()));
		}
	}

	private TransactionType resolveType(Record record) {
		String type = record.getType();
//		System.out.println(MessageFormat.format("Record [{0}] has type [{1}]", record.getIndex(), type));
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
		private Map<Long, Customer> customers = new HashMap<>();
		private Car car;

		public Customer getCustomer(Long customerKey) {
			return customers.get(customerKey);
		}

		public void addCustomer(Long customerKey, Customer customer) {
			customers.put(customerKey, customer);
		}

		public Car getCar() {
			return car;
		}

		public void setCar(Car car) {
			this.car = car;
		}
	}
	
	private static enum CurrencyCustomer {
		BYR("BYN"), BYR_DAD("BYN"), USD("USD"), USD_DAD("USD");
		
		private String currency;
		
		private CurrencyCustomer(String currency) {
			this.currency = currency;
		}
		
		public String getCurrency() {
			return currency;
		}
	}
}
