package net.dragberry.carmanager.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.domain.Fuel;
import net.dragberry.carmanager.domain.Transaction;

@Repository
public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

	public TransactionDaoImpl() {
		super(Transaction.class);
	}

	@Override
	public List<Transaction> fetchList() {
		return getEntityManager().createQuery("FROM " + getEntityName() + " t LEFT JOIN FETCH t.fuel f", getEntityType()).getResultList();
	}
}
