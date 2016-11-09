package net.dragberry.carmanager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import net.dragberry.carmanager.service.validation.ValidationIssue;
import net.dragberry.carmanager.service.validation.ValidationService;
import net.dragberry.carmanager.to.IssueTO;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.common.TransactionStatus;
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
	@Autowired
	@Qualifier("TransactionValidationService")
	private ValidationService<Transaction> validationService;
	
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
				to.setTransactionTypeName(tnx.getTransactionType().getName());
				
				Currency currency = tnx.getCurrency();
				if (query.getDisplayCurrency() != null && query.getDisplayCurrency() !=  currency) {
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
	public ResultTO<TransactionTO> createTransaction(TransactionTO to) {
		Transaction transaction = new Transaction();
		transaction.setAmount(to.getAmount());
		transaction.setDescription(to.getDescription());
		transaction.setCurrency(to.getCurrency());
		transaction.setExchangeRate(to.getExchangeRate());
		transaction.setExecutionDate(to.getExecutionDate());
		
		Double exchangeRate = currencyService.getExchangeRate(Currency.USD, to.getExecutionDate());
		if (exchangeRate == null) {
			transaction.setStatus(TransactionStatus.PROCESSING);
		} else {
			transaction.setStatus(TransactionStatus.ACTIVE);
			transaction.setExchangeRate(exchangeRate);
		}
		if (to.getCarKey() != null) {
			Car car = carDao.findOne(to.getCarKey());
			transaction.setCar(car);
		}
		if (to.getTransactionTypeKey() != null) {
			TransactionType tType = transactionTypeDao.findOne(to.getTransactionTypeKey());
			transaction.setTransactionType(tType);
		}
		if (to.getCustomerKey() != null) {
			Customer customer = customerDao.findOne(to.getCustomerKey());
			transaction.setCustomer(customer);
		}
		if (to.getCreditorKey() != null) {
			Customer creditor = customerDao.findOne(to.getCreditorKey());
			transaction.setCreditor(creditor);
		}
		
		if (to.getFuel() != null) {
			Fuel fuel = new Fuel();
			fuel.setCost(to.getFuel().getCost());
			fuel.setQuantity(to.getFuel().getQuantity());
			fuel.setType(to.getFuel().getType());
			transaction.setFuel(fuel);
		}
		
		Collection<ValidationIssue<Transaction>> issues = validationService.validate(Arrays.asList(transaction));
		Set<IssueTO> issuesTO = new HashSet<>();
		if (issues.isEmpty()) {
			transaction = transactionDao.create(transaction);
		} else {
			issues.forEach(issue -> {
				IssueTO issueTO = new IssueTO();
				issueTO.setMsgCode(issue.getMsgNumber());
				issueTO.setParams(issue.getParams());
				issuesTO.add(issueTO);
			});
		}

		to.setTransactionKey(transaction.getEntityKey());
		return new ResultTO<TransactionTO>(to, issuesTO);
	}

	@Override
	public ResultTO<TransactionSummaryTO> fetchSummary(TransactionQueryListTO query) {
		TransactionSummaryTO summary = new TransactionSummaryTO();
		Object[] total = transactionDao.summary(query);
		summary.setTotalAmount(total[0] == null ? BigDecimal.ZERO : ((BigDecimal) total[0]).setScale(2, RoundingMode.HALF_UP));
		summary.setTotalAmountByCustomer(total[1] == null ? BigDecimal.ZERO : ((BigDecimal) total[1]).setScale(2, RoundingMode.HALF_UP));
		summary.setTotalFuelAmount(total[2] == null ? BigDecimal.ZERO : ((BigDecimal) total[2]).setScale(2, RoundingMode.HALF_UP));
		summary.setDisplayCurrency(Currency.USD);
		return new ResultTO<TransactionSummaryTO>(summary);
	}
}

