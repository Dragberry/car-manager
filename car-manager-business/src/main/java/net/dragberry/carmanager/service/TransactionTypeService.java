package net.dragberry.carmanager.service;

import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionTypeTO;

public interface TransactionTypeService {

	ResultList<TransactionTypeTO> fetchTypeListForCustomer(Long customerKey);
	
	ResultTO<TransactionTypeTO> fetchTypeByName(String name);
}
