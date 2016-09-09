package net.dragberry.carmanager.web.controller.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.service.CarService;
import net.dragberry.carmanager.service.CustomerService;
import net.dragberry.carmanager.service.TransactionService;
import net.dragberry.carmanager.service.TransactionTypeService;
import net.dragberry.carmanager.to.CarTO;
import net.dragberry.carmanager.to.CustomerSettingsTO;
import net.dragberry.carmanager.to.CustomerTO;
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
@SessionAttributes(TransactionController.Models.CUSTOMER_SETTINGS)
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private CustomerService customerService;
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
		String PAYER_LIST = "payerList";
		String CREDITOR_LIST = "creditorList";
		String CUSTOMER_SETTINGS = "customerSettings";
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
	public ModelAndView submitTransaction(@ModelAttribute(Models.CUSTOMER_SETTINGS) CustomerSettingsTO customerSettingsTO, TransactionTO transaction) {
		ResultTO<TransactionTO> result = transactionService.createTransaction(transaction);
		if (!result.hasIssues()) {
			return new ModelAndView(Constants.Path.redirect(Constants.Path.TRANSACTION_LIST));
		} else {
			return prepareCreateTransactionScreen(transaction);
		}
	}
	
	@RequestMapping(value = Constants.Path.TRANSACTION_CREATE, method = RequestMethod.GET)
	public ModelAndView createTransaction(@ModelAttribute(Models.CUSTOMER_SETTINGS) CustomerSettingsTO customerSettingsTO) {
		TransactionTO to = new TransactionTO();
		if (customerSettingsTO != null) {
			to.setCurrency(customerSettingsTO.getPreferredPaymentCurrency());
			TransactionTypeTO preferredPaymentType = customerSettingsTO.getPreferredPaymentType();
			to.setTransactionTypeKey(preferredPaymentType != null ? preferredPaymentType.getTransactionTypeKey() : null);
			FuelTO fuel = customerSettingsTO.getPreferredFuel();
			if (fuel != null) {
				to.setFuel(customerSettingsTO.getPreferredFuel());
				BigDecimal cost = fuel.getCost() != null ? fuel.getCost() : BigDecimal.ZERO;
				Double qunatity = fuel.getQuantity() != null ? fuel.getQuantity() : 0.0;
				to.setAmount(cost.multiply(new BigDecimal(qunatity)));
			}
		}
		to.setExecutionDate(LocalDate.now());
		return prepareCreateTransactionScreen(to);
	}

	private ModelAndView prepareCreateTransactionScreen(TransactionTO transaction) {
		transaction.setFuel(transaction.getFuel() == null ? new FuelTO() : transaction.getFuel());
		ModelAndView modelAndView = new ModelAndView(Constants.View.TRANSACTION_CREATE);
		Long customerKey = CMSecurityContext.getCustomerKey();
		List<CarTO> carList = carService.fetchCarsForCustomer(customerKey).getResult();
		modelAndView.addObject(Models.CAR_LIST, carList);
		List<TransactionTypeTO> typeList = transactionTypeService.fetchTypeListForCustomer(customerKey).getResult();
		modelAndView.addObject(Models.TRANSACTION_TYPE_LIST, typeList);
		modelAndView.addObject(Models.TRANSACTION, transaction);
		List<CustomerTO> payerList = customerService.fetchPayersForCustomer(customerKey).getResult();
		modelAndView.addObject(Models.PAYER_LIST, payerList);
		List<CustomerTO> creditorList = customerService.fetchCreditorsForCustomer(customerKey).getResult();
		modelAndView.addObject(Models.CREDITOR_LIST, creditorList);
		modelAndView.addObject(Models.CURRENCY_LIST, Currency.values());
		return modelAndView;
	}
}
