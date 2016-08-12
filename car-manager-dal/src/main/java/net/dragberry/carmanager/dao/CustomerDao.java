package net.dragberry.carmanager.dao;

import net.dragberry.carmanager.domain.Customer;

public interface CustomerDao extends DataAccessObject<Customer, Long> {
	
	Customer findByCustomerName(String name);

}
