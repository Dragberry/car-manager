package net.dragberry.carmanager.dao.impl;

import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.dao.CustomerDao;
import net.dragberry.carmanager.domain.Customer;
import net.dragberry.carmanager.domain.CustomerSetting;

@Repository("customerDao")
public class CustomerDaoImpl extends AbstractDao<Customer> implements CustomerDao {

	public CustomerDaoImpl() {
		super(Customer.class);
	}

	@Override
	public Customer findByCustomerName(String name) {
		List<Customer> result = getEntityManager().createNamedQuery(Customer.FIND_BY_CUSTOMER_NAME_QUERY, getEntityType())
			.setParameter("customerName", name)
			.getResultList();
		return result.size() > 0  ? result.get(0) : null;
	}

	@Override
	public Map<CustomerSetting, String> fetchCustomerSettings(Long customerKey) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Customer> cq = cb.createQuery(getEntityType());
		Root<Customer> cRoot = cq.from(getEntityType());
		cRoot.fetch("settings", JoinType.LEFT);
		cq.where(cb.equal(cRoot.get("entityKey"), customerKey));
		cq.select(cRoot);
		Customer customer = getEntityManager().createQuery(cq).getSingleResult();
		return customer.getSettings();
	}

	@Override
	public Customer fetchWithPayers(Long customerKey) {
		List<Customer> result =  getEntityManager().createNamedQuery(Customer.FETCH_PAYERS_QUERY, getEntityType())
				.setParameter("customerKey", customerKey)
				.getResultList();
		return result.size() > 0  ? result.get(0) : null;
	}

}
