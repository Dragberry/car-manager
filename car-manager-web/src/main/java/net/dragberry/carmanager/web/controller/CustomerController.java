package net.dragberry.carmanager.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.service.CustomerService;
import net.dragberry.carmanager.to.CustomerTO;
import net.dragberry.carmanager.web.security.CMSecurityContext;

@Controller
@RequestMapping("/service/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/payer/list", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerTO> fetchPayerList() {
		return customerService.fetchPayersForCustomer(CMSecurityContext.getLoggedCustomerKey()).getResult();
	}
	
	@RequestMapping(value = "/creditor/list", method = RequestMethod.GET)
	@ResponseBody
	public List<CustomerTO> fetchCreditorList() {
		return customerService.fetchCreditorsForCustomer(CMSecurityContext.getLoggedCustomerKey()).getResult();
	}
}
