package net.dragberry.carmanager.web.controller.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.web.common.Constants;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(Constants.Path.TRANSACTION_LIST)
	public ModelAndView transactionList() {
		ModelAndView mv = new ModelAndView(Constants.View.TRANSACTION_LIST);
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarKey(1L);
		query.setPageSize(200);
		query.setCarOwnerKey(3L);
		ResultList<TransactionTO> list = transactionService.fetchList(query);
		mv.addObject("transactionList", list.getResult());
		ResultTO<TransactionSummaryTO> summary = transactionService.fetchSummary(query);
		mv.addObject("transactionSummary", summary.getObject());
		return mv;
	}
}
