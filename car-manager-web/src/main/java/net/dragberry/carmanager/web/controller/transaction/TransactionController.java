package net.dragberry.carmanager.web.controller.transaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.to.TransactionTypeTO;
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
	
	@RequestMapping(value = Constants.Path.TRANSACTION_CREATE)
	public ModelAndView createTransaction() {
		ModelAndView modelAndView = new ModelAndView(Constants.View.TRANSACTION_CREATE);
		List<CarTO> carList = new ArrayList<>();
		CarTO car = new CarTO();
		car.setCarKey(1L);
		car.setBrand("Mercedes-Benz");
		car.setModel("C180");
		carList.add(car);
		modelAndView.addObject("carList", carList);
		List<TransactionTypeTO> typeList = new ArrayList<>();
		TransactionTypeTO type = new TransactionTypeTO();
		type.setName("Fuel");
		type.setTransactionTypeKey(1L);
		typeList.add(type);
		modelAndView.addObject("transactionTypeList", typeList);
		modelAndView.addObject("transaction", new TransactionTO());
		return modelAndView;
	}
}
