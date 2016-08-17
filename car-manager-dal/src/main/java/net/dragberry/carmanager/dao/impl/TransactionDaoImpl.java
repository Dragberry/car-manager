package net.dragberry.carmanager.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.criteria.Criteria;
import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.transferobject.TransactionQueryListTO;

@Repository
public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

	private static final String FETCH_HQL = "select t from " + Transaction.class.getName() + " t left join fetch t.fuel";
	private static final String COUNT_HQL = "select count(t) from " + Transaction.class.getName() + " t left join t.fuel";
	private static final String WHERE = " where ";
	private static final String AND = " and ";
	
	public TransactionDaoImpl() {
		super(Transaction.class);
	}

	@Override
	public List<Transaction> fetchList(TransactionQueryListTO query) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
		Root<Transaction> tRoot = cq.from(Transaction.class);
		Fetch<Object, Object> fetchF = tRoot.fetch("fuel", JoinType.LEFT);
		Predicate where = null;
		if (query.getDateFrom() != null) {
			where = cb.greaterThan(tRoot.<LocalDate>get("executionDate"), query.getDateFrom());
		}
		if (query.getDateTo() != null) {
			if (where == null) {
				where = cb.lessThanOrEqualTo(tRoot.<LocalDate>get("executionDate"), query.getDateTo());
			} else {
				where = cb.and(where ,cb.lessThanOrEqualTo(tRoot.<LocalDate>get("executionDate"), query.getDateTo()));
			}
		}
		if (query.getCarKey() != null) {
			if (where == null) {
				where = cb.equal(tRoot.get("executionDate"), query.getCarKey());
			} else {
				where = cb.and(where, cb.equal(tRoot.get("car"), query.getCarKey()));
			}
		}
		if (query.getAmountFrom() != null) {
			if (where == null) {
				where = cb.lessThan(tRoot.<BigDecimal>get("amount"), query.getAmountFrom());
			} else {
				where = cb.and(where, cb.lessThan(tRoot.<BigDecimal>get("amount"), query.getAmountFrom()));
			}
		}
		if (query.getAmountTo() != null) {
			if (where == null) {
				where = cb.greaterThanOrEqualTo(tRoot.<BigDecimal>get("amount"), query.getAmountTo());
			} else {
				where = cb.and(where, cb.greaterThanOrEqualTo(tRoot.<BigDecimal>get("amount"), query.getAmountTo()));
			}
		}
		if (where != null) {
			cq.where(where);
		}
		cq.select(tRoot);
		
		
		TypedQuery<Transaction> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
		
//		
//		StringBuilder hql = new StringBuilder(FETCH_HQL);
//		Map<String, Object> params = new HashMap<>();
//		buildFetchQuery(hql, params, query);
//		return preparePageableQuery(hql.toString(), query, params, getEntityType()).getResultList();
	}

	@Override
	public Long count(TransactionQueryListTO query) {
		StringBuilder hql = new StringBuilder(COUNT_HQL);
		Map<String, Object> params = new HashMap<>();
		buildFetchQuery(hql, params, query);
		return prepateFetchQuery(hql.toString(), params, Long.class).getSingleResult();
	}
	
	private static final void buildFetchQuery(StringBuilder hql, Map<String, Object> params, TransactionQueryListTO query) {
		Criteria<Transaction> criteria = new Criteria<>("");
		
		if (query.getCarKey() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.car.entityKey = :carKey");
			params.put("carKey", query.getCarKey());
		}
		if (query.getCustomerKey() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.customer.entityKey = :cutomerKey");
			params.put("cutomerKey", query.getCustomerKey());
		}
		if (query.getDateFrom() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.executionDate > :dateFrom");
			params.put("dateFrom", query.getDateFrom());
		}
		if (query.getDateTo() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.executionDate <= :dateTo");
			params.put("dateTo", query.getDateTo());
		}
		if (query.getFuelQuantityTo() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.fuel.quantity <= :fuelTo");
			params.put("fuelTo", query.getFuelQuantityTo());
		}
		if (query.getFuelQuantityFrom() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.fuel.quantity > :fuelFrom");
			params.put("fuelFrom", query.getFuelQuantityFrom());
		}
		if (query.getAmountFrom() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.amount > :amountFrom");
			params.put("amountFrom", query.getAmountFrom());
		}
		if (query.getAmountTo() != null) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.amount <= :amountTo");
			params.put("amountTo", query.getAmountTo());
		}
		if (CollectionUtils.isNotEmpty(query.getTransactionTypeKeyList())) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.transactionType.entityKey in :transactionTypeList");
			params.put("transactionTypeList", query.getTransactionTypeKeyList());
		}
		if (CollectionUtils.isNotEmpty(query.getCurrencyList())) {
			hql.append(params.isEmpty() ? WHERE : AND);
			hql.append("t.currency in :currencyList");
			params.put("currencyList", query.getCurrencyList());
		}
	}
}
