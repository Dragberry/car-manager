package net.dragberry.carmanager.dao.impl;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
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
		List<TransactionType> list = getEntityManager().createNamedQuery(TransactionType.FIND_BY_NAME_QUERY, TransactionType.class)
				.setParameter("name", name)
				.getResultList();
		return CollectionUtils.get(list, 0);
	}

}
