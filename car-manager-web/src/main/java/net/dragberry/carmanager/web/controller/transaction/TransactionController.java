package net.dragberry.carmanager.web.controller.transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.service.CarService;
import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.service.TransactionTypeService;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.FuelTO;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.ResultTO;
import net.dragberry.carmanager.to.TransactionQueryListTO;
import net.dragberry.carmanager.to.TransactionSummaryTO;
import net.dragberry.carmanager.to.TransactionTO;
import net.dragberry.carmanager.to.TransactionTypeTO;
import net.dragberry.carmanager.web.common.Constants;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@Controller
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private CarService carService;
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	private static interface Models {
		String TRANSACTION_LIST = "transactionList";
		String TRANSACTION_SUMMARY = "transactionSummary";
		String CAR_LIST = "carList";
		String TRANSACTION_TYPE_LIST = "transactionTypeList";
		String TRANSACTION = "transaction";
		String CURRENCY_LIST = "currencyList";
	}

	@RequestMapping(Constants.Path.TRANSACTION_LIST)
	public ModelAndView transactionList() {
		ModelAndView mv = new ModelAndView(Constants.View.TRANSACTION_LIST);
		TransactionQueryListTO query = new TransactionQueryListTO();
		query.setCarKey(1L);
		query.setPageSize(200);
		query.setCarOwnerKey(3L);
		ResultList<TransactionTO> list = transactionService.fetchList(query);
		mv.addObject(Models.TRANSACTION_LIST, list.getResult());
		ResultTO<TransactionSummaryTO> summary = transactionService.fetchSummary(query);
		mv.addObject(Models.TRANSACTION_SUMMARY, summary.getObject());
		return mv;
	}
	
	@RequestMapping(value = Constants.Path.TRANSACTION_CREATE, method = RequestMethod.POST)
	public ModelAndView submitTransaction(TransactionTO transaction) {
		ResultTO<TransactionTO> result = transactionService.createTransaction(transaction);
		if (result.hasIssues()) {
			return new ModelAndView(Constants.Path.redirect(Constants.Path.TRANSACTION_LIST));
		} else {
			return prepareCreateTransactionScreen(transaction);
		}
	}
	
	@RequestMapping(value = Constants.Path.TRANSACTION_CREATE, method = RequestMethod.GET)
	public ModelAndView createTransaction() {
		TransactionTO to = new TransactionTO();
		to.setExecutionDate(LocalDate.now());
		to.setFuel(new FuelTO());
		return prepareCreateTransactionScreen(to);
	}

	private ModelAndView prepareCreateTransactionScreen(TransactionTO transaction) {
		ModelAndView modelAndView = new ModelAndView(Constants.View.TRANSACTION_CREATE);
		List<CarTO> carList = carService.fetchCarsForCustomer(CMSecurityContext.getCustomerKey()).getResult();
		modelAndView.addObject(Models.CAR_LIST, carList);
		List<TransactionTypeTO> typeList = transactionTypeService.fetchTypeListforCustomer(CMSecurityContext.getCustomerKey()).getResult();
		modelAndView.addObject(Models.TRANSACTION_TYPE_LIST, typeList);
		modelAndView.addObject(Models.TRANSACTION, transaction);
		modelAndView.addObject(Models.CURRENCY_LIST, Currency.values());
		return modelAndView;
	}
}
