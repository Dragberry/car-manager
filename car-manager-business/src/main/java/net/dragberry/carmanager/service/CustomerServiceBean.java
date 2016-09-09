package net.dragberry.carmanager.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.CustomerSetting;
import net.dragberry.carmanager.domain.Role;
import net.dragberry.carmanager.to.CustomerSettingsTO;
import net.dragberry.carmanager.to.CustomerTO;
import net.dragberry.carmanager.to.ResultList;
import net.dragberry.carmanager.to.TransactionTypeTO;

/**
 * Customer service bean
 * 
 * @author Maksim Drahun
 *
 */
@Service
public class CustomerServiceBean implements CustomerService {

	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private TransactionTypeService transactionTypeService;
	
	@Override
	public CustomerTO findByCustomerName(String customerName) {
		Customer customer = customerDao.findByCustomerName(customerName);
		CustomerTO to = null;
		if (customer != null) {
			to = new CustomerTO();
			to.setCustomerKey(customer.getEntityKey());
			to.setBirtdate(customer.getBirthDate());
			to.setCustomerName(customer.getCustomerName());
			to.setEmail(customer.getEmail());
			to.setEnabled(true);
			to.setFirstName(customer.getFirstName());
			to.setLastName(customer.getLastName());
			to.setPassword(customer.getPassword());
			Set<String> roles = new HashSet<>();
			for (Role role : customer.getRoles()) {
				roles.add(role.getRoleName());
			}
			to.setRoles(roles);
		}
		return to;
	}

	@Override
	public CustomerSettingsTO fetchCustomerSettings(Long customerKey) {
		CustomerSettingsTO settingsTO = new CustomerSettingsTO();
		Map<CustomerSetting, String> settings = customerDao.fetchCustomerSettings(customerKey);
		String currency = settings.get(CustomerSetting.PREFERRED_PAYMENT_CURRENCY);
		if (currency != null) {
			settingsTO.setPreferredPaymentCurrency(Currency.valueOf(currency));
		}
		String type = settings.get(CustomerSetting.PREFERRED_PAYMENT_TYPE);
		if (type != null) {
			TransactionTypeTO typeTO = transactionTypeService.fetchTypeByName(type).getObject();
			settingsTO.setPreferredPaymentType(typeTO);
		}
		settingsTO.getPreferredFuel().setType(settings.get(CustomerSetting.PREFERRED_FUEL_TYPE));
		String fueldCost = settings.get(CustomerSetting.LAST_FUEL_COST);
		if (fueldCost != null) {
			settingsTO.getPreferredFuel().setCost(new BigDecimal(fueldCost));
		}
		String fuelQuantity = settings.get(CustomerSetting.PREFERRED_FUEL_QUANTITY);
		if (fuelQuantity != null) {
			settingsTO.getPreferredFuel().setQuantity(Double.valueOf(fuelQuantity));
		}
		return settingsTO;
	}

	@Override
	@Transactional
	public ResultList<CustomerTO> fetchPayersForCustomer(Long customerKey) {
		ResultList<CustomerTO> payerList = new ResultList<>();
		Customer customer = customerDao.fetchWithPayers(customerKey);
		Set<Customer> customerList = customer.getPayers();
		payerList.addItem(transformCustomer(customer));
		customerList.forEach(cust -> payerList.addItem(transformCustomer(cust)));
		payerList.sort((o1, o2) -> {
			if (o1.getCustomerKey().equals(customerKey)) {
				return -1;
			} else if (o2.getCustomerKey().equals(customerKey)) {
				return 1;
			} else {
				return o1.getFirstName().compareTo(o2.getFirstName());
			}
		});
		return payerList;
	}

	@Override
	@Transactional
	public ResultList<CustomerTO> fetchCreditorsForCustomer(Long customerKey) {
		ResultList<CustomerTO> creditorList = new ResultList<>();
		Customer customer = customerDao.fetchWithPayers(customerKey);
		Set<Customer> customerList = customer.getPayers();
		customerList.forEach(cust -> creditorList.addItem(transformCustomer(cust)));
		creditorList.sort((o1, o2) -> o1.getFirstName().compareTo(o2.getFirstName()));
		return creditorList;
	}
	
	private static CustomerTO transformCustomer(Customer cust) {
		CustomerTO to = new CustomerTO();
		to.setCustomerKey(cust.getEntityKey());
		to.setFirstName(cust.getFirstName());
		to.setLastName(cust.getLastName());
		return to;
	}

}
