package net.dragberry.carmanager.dao;

import java.util.List;

import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.to.TransactionQueryListTO;

public interface TransactionDao extends DataAccessObject<Transaction, Long> {

	List<Transaction> fetchList(TransactionQueryListTO query);
	
	List<Transaction> fetchInactiveTransactions();
	
	Long count(TransactionQueryListTO query);
	
	Object[] summary(TransactionQueryListTO query);
}
