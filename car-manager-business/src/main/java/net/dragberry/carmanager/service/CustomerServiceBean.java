package net.dragberry.carmanager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.CustomerSetting;
import net.dragberry.carmanager.domain.Role;
import net.dragberry.carmanager.to.CustomerTO;
import net.dragberry.carmanager.to.ResultList;

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
	public Map<CustomerSetting, String> fetchCustomerSettings(Long customerKey) {
		return customerDao.fetchCustomerSettings(customerKey);
	}

	@Override
	public ResultList<CustomerTO> fetchPayersForCustomer(Long customerKey) {
		ResultList<CustomerTO> payerList = new ResultList<>();
		Set<Customer> customerList = customerDao.fetchPayersForCustomer(customerKey);
		customerList.forEach(customer -> {
			CustomerTO payer = new CustomerTO();
			payer.setCustomerKey(customer.getEntityKey());
			payer.setFirstName(customer.getFirstName());
			payer.setLastName(customer.getLastName());
			payerList.addItem(payer);
		});
		payerList.sort((o1, o2) -> o1.getCustomerKey().equals(customerKey) ? -1 : 0);
		return payerList;
	}

}
