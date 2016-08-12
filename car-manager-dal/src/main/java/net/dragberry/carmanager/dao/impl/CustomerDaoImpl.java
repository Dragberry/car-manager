package net.dragberry.carmanager.dao.impl;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.domain.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao {

	public CustomerDaoImpl() {
		super(Customer.class);
	}

	@Override
	public Customer findByCustomerName(String name) {
		TypedQuery<Customer> query = getEntityManager().createQuery("FROM " + getEntityName() + " e WHERE e.customerName = :customerName", getEntityType());
		query.setParameter("customerName", name);
		return query.getSingleResult();
	}

}
