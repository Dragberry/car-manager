package net.dragberry.carmanager.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.service.TransactionTypeService;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.to.TransactionTypeTO;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@Controller
@RequestMapping("/service/transaction")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionTO> fetchTransactionList() {
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarOwnerKey(CMSecurityContext.getLoggedCustomerKey());
		return transactionService.fetchList(query).getResult();
	}

	@RequestMapping(value ="/summary", method = RequestMethod.GET)
	@ResponseBody
	public TransactionSummaryTO fetchTransactionSummary() {
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarOwnerKey(CMSecurityContext.getLoggedCustomerKey());
		return transactionService.fetchSummary(query).getObject();
	}
	
	@RequestMapping(value ="/type/list", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionTypeTO> fetchTransactionTypeList() {
		List<TransactionTypeTO> list = transactionTypeService.fetchTypeListForCustomer(CMSecurityContext.getLoggedCustomerKey()).getResult();
		Collections.sort(list, (type1, type2) -> {
			return type1.getTransactionTypeKey() == 1L ? -1 : type1.getTransactionTypeKey().compareTo(type2.getTransactionTypeKey());
		});
		return list;
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public TransactionTO submitTransaction(@RequestBody TransactionTO transactionTO) {
		transactionTO.setCustomerKey(CMSecurityContext.getLoggedCustomerKey());
		ResultTO<TransactionTO> tnx = transactionService.createTransaction(transactionTO);
		return tnx.getObject();
	}
	
}
