package net.dragberry.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.domain.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

	/**
	 * Performs search for customer with the given name
	 * 
	 * @param customerName
	 * @return {@link Customer}
	 */
	Customer findByCustomerName(String customerName);
}
