package net.dragberry.carmanager.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.TransactionTypeDao;
import net.dragberry.carmanager.domain.TransactionType;

@Repository
public class TransactionTypeDaoImpl extends AbstractDao<TransactionType> implements TransactionTypeDao {

	public TransactionTypeDaoImpl() {
		super(TransactionType.class);
	}

	@Override
	public TransactionType findByName(String name) {
		TypedQuery<TransactionType> query = getEntityManager().createQuery("FROM " + getEntityName() + " e WHERE e.name = :name", getEntityType());
		query.setParameter("name", name);
		List<TransactionType> resultList = query.getResultList();
		return resultList.isEmpty() ? null : resultList.get(0);
	}

}
