package net.dragberry.carmanager.service;

import net.dragberry.carmanager.transferobject.TransactionTO;

/**
 * Transaction service
 * 
 * @author Maksim Drahun
 *
 */
public interface TransactionService {

	TransactionTO createTransaction(TransactionTO transaction);
	
	
}
