package net.dragberry.carmanager.web.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.service.TransactionTypeService;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.to.TransactionTypeTO;

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
		return transactionService.fetchList(new TransactionQueryListTO()).getResult();
	}

	@RequestMapping(value ="/summary", method = RequestMethod.GET)
	@ResponseBody
	public TransactionSummaryTO fetchTransactionSummary() {
		return transactionService.fetchSummary(new TransactionQueryListTO()).getObject();
	}
	
	@RequestMapping(value ="/type/list", method = RequestMethod.GET)
	@ResponseBody
	public List<TransactionTypeTO> fetchTransactionTypeList(Long customerKey) {
		List<TransactionTypeTO> list = transactionTypeService.fetchTypeListForCustomer(customerKey).getResult();
		Collections.sort(list, (type1, type2) -> {
			return type1.getTransactionTypeKey() == 1L ? -1 : type1.getTransactionTypeKey().compareTo(type2.getTransactionTypeKey());
		});
		return list;
	}
	
	
}
