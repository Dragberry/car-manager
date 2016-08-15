package net.dragberry.carmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.transferobject.QueryListTO;
import net.dragberry.carmanager.transferobject.ResultList;
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
	private TransactionDao transactionDao;
	
	@Override
	public ResultList<TransactionTO> fetchList(QueryListTO query) {
		ResultList<TransactionTO> result = new ResultList<>();
		Long count = transactionDao.count();
		result.setTotalCount(count);
		if (count > 0L) {
			List<Transaction> list = transactionDao.fetchList();
			list.forEach(tnx -> {
				TransactionTO to = new TransactionTO();
				to.setAmount(tnx.getAmount());
				to.setTransactionKey(tnx.getEntityKey());
				to.setExecutionDate(tnx.getExecutionDate());
				to.setDescription(tnx.getDescription());
				result.addItem(to);
			});
		}
		return result;
	}
	
	@Override
	public TransactionTO createTransaction(TransactionTO to) {
//		Transaction transaction = new Transaction();
//		transaction.setAmount(to.getAmount());
//		transaction.setCurrency(to.getCurrency());
//		transaction.setExchangeRate(to.getExchangeRate());
//		transaction.setExecutionDate(to.getExecutionDate());
//		
//		Car car = carRepo.findOne(to.getCarKey());
//		transaction.setCar(car);
//		TransactionType tType = transactionTypeRepo.findOne(to.getTransactionTypeKey());
//		transaction.setTransactionType(tType);
//		Customer customer = customerRepo.findOne(to.getCustomerKey());
//		transaction.setCustomer(customer);
//		
//		transaction = transactionRepo.save(transaction);
//		if (to.getFuel() != null) {
//			Fuel fuel = new Fuel();
//			fuel.setCost(to.getFuel().getCost());
//			fuel.setTransaction(transaction);
//			fuel.setQuantity(to.getFuel().getQuantity());
//			fuel.setType(to.getFuel().getType());
//			fuel = fuelRepo.save(fuel);
//			to.getFuel().setFuelKey(fuel.getEntityKey());
//		}
//
//		to.setTransactionKey(transaction.getEntityKey());
		return to;
	}

}
