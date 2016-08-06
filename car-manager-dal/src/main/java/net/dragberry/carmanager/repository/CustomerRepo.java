package net.dragberry.carmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.domain.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

}
