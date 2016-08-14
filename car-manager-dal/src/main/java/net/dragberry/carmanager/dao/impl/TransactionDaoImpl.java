package net.dragberry.carmanager.dao.impl;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.domain.Transaction;

@Repository
public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao{

	public TransactionDaoImpl() {
		super(Transaction.class);
	}

}
