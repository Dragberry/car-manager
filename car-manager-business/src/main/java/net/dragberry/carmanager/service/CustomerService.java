package net.dragberry.carmanager.service;

import java.util.Map;

import net.dragberry.carmanager.domain.CustomerSetting;
import net.dragberry.carmanager.to.CustomerTO;

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

	Map<CustomerSetting, String> fetchCustomerSettings(Long customerKey);
}
