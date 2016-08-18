package net.dragberry.carmanager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.dao.CarDao;
import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.dao.TransactionTypeDao;
import net.dragberry.carmanager.domain.Car;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.transferobject.ResultList;
import net.dragberry.carmanager.transferobject.TransactionQueryListTO;
import net.dragberry.carmanager.transferobject.TransactionTO;
import net.dragberry.carmanager.ws.client.CurrencyService;

/**
 * Transaction service bean
 * 
 * @author Maksim Drahun
 *
 */
@Service
public class TransactionServiceBean implements TransactionService {
	
	@Autowired
	private TransactionDao transactionDao;
	@Autowired
	private CarDao carDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private TransactionTypeDao transactionTypeDao;
	
	@Autowired
	private CurrencyService currencyService;
	
	@Override
	public ResultList<TransactionTO> fetchList(TransactionQueryListTO query) {
		ResultList<TransactionTO> result = new ResultList<>();
		Long count = transactionDao.count(query);
		result.setTotalCount(count);
		System.out.println("Count: " + count);
		if (count > 0L) {
			List<Transaction> list = transactionDao.fetchList(query);
			list.forEach(tnx -> {
				TransactionTO to = new TransactionTO();
				to.setTransactionKey(tnx.getEntityKey());
				to.setExecutionDate(tnx.getExecutionDate());
				to.setDescription(tnx.getDescription());
				
				Currency currency = Currency.valueOf(tnx.getCurrency());
				if (query.getDisplayCurrency() !=  currency) {
					BigDecimal newAmount = tnx.getAmount().divide(new BigDecimal(tnx.getExchangeRate()), 2, RoundingMode.HALF_UP);
					to.setAmount(newAmount);
					to.setCurrency(query.getDisplayCurrency());
				} else {
					to.setAmount(tnx.getAmount());
					to.setCurrency(currency);
				}
				result.addItem(to);
			});
		}
		result.setTotalCount(count);
		result.setPageNumber(query.getPageNumber());
		result.setPageSize(query.getPageSize());
		return result;
	}
	
	@Override
	@Transactional
	public TransactionTO createTransaction(TransactionTO to) {
		Transaction transaction = new Transaction();
		transaction.setAmount(to.getAmount());
		transaction.setDescription(to.getDescription());
		transaction.setCurrency(to.getCurrency().name());
		transaction.setExchangeRate(to.getExchangeRate());
		transaction.setExecutionDate(to.getExecutionDate());
		
		transaction.setExchangeRate(currencyService.getExchangeRate(Currency.USD, to.getExecutionDate()));
		
		Car car = carDao.findOne(to.getCarKey());
		transaction.setCar(car);
		TransactionType tType = transactionTypeDao.findOne(to.getTransactionTypeKey());
		transaction.setTransactionType(tType);
		Customer customer = customerDao.findOne(to.getCustomerKey());
		transaction.setCustomer(customer);
		
		if (to.getFuel() != null) {
			Fuel fuel = new Fuel();
			fuel.setCost(to.getFuel().getCost());
			fuel.setQuantity(to.getFuel().getQuantity());
			fuel.setType(to.getFuel().getType());
			transaction.setFuel(fuel);
		}
		transaction = transactionDao.create(transaction);

		to.setTransactionKey(transaction.getEntityKey());
		return to;
	}

}
