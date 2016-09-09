package net.dragberry.carmanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.dao.TransactionTypeDao;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionTypeTO;

@Service
public class TransactionTypeServiceBean implements TransactionTypeService {

	@Autowired
	private TransactionTypeDao transactionTypeDao;
	@Override
	public ResultList<TransactionTypeTO> fetchTypeListForCustomer(Long customerKey) {
		List<TransactionType> typeList = transactionTypeDao.fetchList();
		ResultList<TransactionTypeTO> typeListTO = new ResultList<>();
		typeList.forEach(type -> typeListTO.addItem(transform(type)));
		return typeListTO;
	}
	@Override
	public ResultTO<TransactionTypeTO> fetchTypeByName(String name) {
		TransactionType type = transactionTypeDao.findByName(name);
		return new ResultTO<TransactionTypeTO>(transform(type));
	}
	
	private static TransactionTypeTO transform(TransactionType type) {
		TransactionTypeTO typeTO = new TransactionTypeTO();
		typeTO.setName(type.getName());
		typeTO.setTransactionTypeKey(type.getEntityKey());
		return typeTO;
	}

}
