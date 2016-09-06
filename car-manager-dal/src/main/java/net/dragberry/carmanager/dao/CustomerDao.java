package net.dragberry.carmanager.dao;

import java.util.Map;

import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.CustomerSetting;

public interface CustomerDao extends DataAccessObject<Customer, Long> {
	
	Customer findByCustomerName(String name);

	Map<CustomerSetting, String> fetchCustomerSettings(Long customerKey);

}
