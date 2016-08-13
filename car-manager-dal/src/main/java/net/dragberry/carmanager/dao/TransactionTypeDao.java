package net.dragberry.carmanager.dao;

import net.dragberry.carmanager.domain.TransactionType;

public interface TransactionTypeDao extends DataAccessObject<TransactionType, Long> {
	
	TransactionType findByName(String name);

}
