package net.dragberry.carmanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.transferobject.ResultList;
import net.dragberry.carmanager.transferobject.TransactionQueryListTO;
import net.dragberry.carmanager.transferobject.TransactionTO;

@Controller
public class HomeController {
	
	@Autowired
	private TransactionService transactionService;

	@RequestMapping("/")
	public ModelAndView home() {
		ModelAndView mv = new ModelAndView("home/index");
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarKey(1L);
		query.setPageSize(200);
		ResultList<TransactionTO> list = transactionService.fetchList(query);
		mv.addObject("transactionList", list.getResult());
		return mv;
	}
}
