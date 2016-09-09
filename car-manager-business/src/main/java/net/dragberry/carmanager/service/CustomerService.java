package net.dragberry.carmanager.service;

import net.dragberry.carmanager.to.CustomerSettingsTO;
import net.dragberry.carmanager.to.CustomerTO;
import net.dragberry.carmanager.to.ResultList;

/**
 * Customer service
 * 
 * @author Maksim Drahun
 *
 */
public interface CustomerService {

	/**
	 * Performs search for customer with the given name
	 * 
	 * @param customerName
	 * @return {@link CustomerTO}
	 */
	CustomerTO findByCustomerName(String customerName);

	CustomerSettingsTO fetchCustomerSettings(Long customerKey);

	ResultList<CustomerTO> fetchPayersForCustomer(Long customerKey);
	
	ResultList<CustomerTO> fetchCreditorsForCustomer(Long customerKey);
}
