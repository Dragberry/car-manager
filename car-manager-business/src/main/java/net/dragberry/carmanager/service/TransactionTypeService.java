package net.dragberry.carmanager.service;

import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.TransactionTypeTO;

public interface TransactionTypeService {

	ResultList<TransactionTypeTO> fetchTypeListforCustomer(Long customerKey);
}
