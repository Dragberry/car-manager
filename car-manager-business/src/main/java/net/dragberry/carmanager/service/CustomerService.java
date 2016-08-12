package net.dragberry.carmanager.service;

import net.dragberry.carmanager.transferobject.CustomerTO;

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
}
