package net.dragberry.carmanager.service;

import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;

/**
 * Transaction service
 * 
 * @author Maksim Drahun
 *
 */
public interface TransactionService {

	ResultTO<TransactionTO> createTransaction(TransactionTO transaction);
	
	ResultList<TransactionTO> fetchList(TransactionQueryListTO query);
	
	ResultTO<TransactionSummaryTO> fetchSummary(TransactionQueryListTO query);
	
	
}
