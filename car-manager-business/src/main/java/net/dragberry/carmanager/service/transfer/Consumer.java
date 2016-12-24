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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.common.TransactionStatus;
import net.dragberry.carmanager.dao.CarDao;
import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.dao.TransactionTypeDao;
import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.to.Record;
import net.dragberry.carmanager.to.UploadTransactionResult;
import net.dragberry.carmanager.util.BYDenominator;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Consumer implements Callable<UploadTransactionResult> {
	
	private static final Logger LOG = LogManager.getLogger(Consumer.class);
	
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
	
	private Context context = new Context();
	
	@Override
	public UploadTransactionResult call() throws Exception {
		context.addCustomer(1002L, customerDao.findOne(1002L));
		context.addCustomer(1003L, customerDao.findOne(1003L));
		context.addCustomer(1004L, customerDao.findOne(1004L));
		context.setCar(carDao.findOne(1000L));
		
		UploadTransactionResult result = new UploadTransactionResult();
		
		Record record= null;
		while ((record = queue.poll(3, TimeUnit.SECONDS)) != null) {
			if (isRecordValid(record)) {
				processRecord(record, result);
			} else {
				LOG.info(MessageFormat.format("Record [{0}] is not valid", record.getIndex()));
			}
		} 
		return result;
	}
	
	private boolean isRecordValid(Record record) {
		boolean isValid = true 
				&& record.getIndex() != null
				&& record.getDate() != null;
		return isValid;
	}
	
	/**
	 * Processes a single records
	 * 
	 * @param record
	 * @param result 
	 * @return {@link UploadTransactionResult}
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private UploadTransactionResult processRecord(Record record, UploadTransactionResult result) {
		List<Transaction> list = new ArrayList<>();
		try {
			resolveTransactionCount(record).forEach(currencyCustomer -> {
				Transaction transaction = new Transaction();
				transaction.setCar(context.getCar());
				transaction.setExecutionDate(record.getDate());
				transaction.setDescription(record.getDescription());
				transaction.setCurrency(currencyCustomer.getCurrency());
				transaction.setAmount(resolveAmount(record, currencyCustomer));
				TransactionType tType = resolveType(record);
				transaction.setTransactionType(tType);
				transaction.setCustomer(getCustomer(record, currencyCustomer));
				transaction.setStatus(TransactionStatus.ACTIVE);
				
				
				if (TransactionType.LOAN_PAYMENT.equals(tType.getName())) {
					transaction.setCreditor(context.getCustomer(4L));
				}
				
				if (TransactionType.FUEL.equals(tType.getName())) {
					transaction.setFuel(createFuel(record, transaction));
				}
				transactionDao.create(transaction);
				list.add(transaction);

			});
			result.addSuccessfulTransactions(list.size());
		} catch (Exception exc) {
			LOG.error(MessageFormat.format("An error has occured in the consumer task in the record [{0}]!", record.getIndex()), exc);
			result.addFailedTransactions(1);
		}
		return result;
	}
	
	private Customer getCustomer(Record record, CurrencyCustomer currencyCustomer) {
		switch (currencyCustomer) {
		case BYR:
		case USD:
			return context.getCustomer(record.getDescription().endsWith("получено") ? 1004L : 1002L);
		case BYR_DAD:
		case USD_DAD:
			return context.getCustomer(1003L);
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
		} else {
			throw new RuntimeException(MessageFormat.format("Cannot parse fueld for record [{0}]!", record.getIndex()));
		}
		return fuel;
	}

	private List<CurrencyCustomer> resolveTransactionCount(Record record) {
		List<CurrencyCustomer> list = new ArrayList<>(CurrencyCustomer.values().length);
		if (record.getCostBYR() != 0 || record.getLoanPaymentBYR() != 0) {
			list.add(CurrencyCustomer.BYR);
		}
		if (record.getCostUSD() != 0 || record.getLoanPaymentUSD() != 0) {
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

	private BigDecimal resolveAmount(Record record, CurrencyCustomer currencyCustomer) {
		switch (currencyCustomer) {
		case BYR:
			if (TransactionType.LOAN_PAYMENT.equals(record.getType())) {
				return new BigDecimal(BYDenominator.denominate(record.getLoanPaymentBYR())).setScale(2, RoundingMode.HALF_UP);
			} else {
				return new BigDecimal(BYDenominator.denominate(record.getCostBYR())).setScale(2, RoundingMode.HALF_UP);
			}
		case BYR_DAD:
			return new BigDecimal(BYDenominator.denominate(record.getCostBYRDad())).setScale(2, RoundingMode.HALF_UP);
		case USD:
			if (TransactionType.LOAN_PAYMENT.equals(record.getType())) {
				return new BigDecimal(record.getLoanPaymentUSD()).setScale(2, RoundingMode.HALF_UP);
			} else {
				return new BigDecimal(record.getCostUSD()).setScale(2, RoundingMode.HALF_UP);
			}
		case USD_DAD:
			return new BigDecimal(record.getCostUSDDad()).setScale(2, RoundingMode.HALF_UP);
		default:
			throw new RuntimeException(MessageFormat.format("Amount cannot be resolved for record [{0}]", record.getIndex()));
		}
	}

	private TransactionType resolveType(Record record) {
		String type = record.getType();
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
					LOG.info(MessageFormat.format("Duplicate entry found for record [{0}] with type [{1}]", record.getIndex(), record.getType()));
				}
			}
		}
		LOG.info(MessageFormat.format("Transaction type for record [{0}] is not resolved", record.getIndex()));
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
		BYR(Currency.BYN), BYR_DAD(Currency.BYN), USD(Currency.USD), USD_DAD(Currency.USD);
		
		private Currency currency;
		
		private CurrencyCustomer(Currency currency) {
			this.currency = currency;
		}
		
		public Currency getCurrency() {
			return currency;
		}
	}
}
