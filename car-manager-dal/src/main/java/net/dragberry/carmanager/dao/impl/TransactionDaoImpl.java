package net.dragberry.carmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

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
		StringBuilder hql = new StringBuilder(FETCH_HQL);
		Map<String, Object> params = new HashMap<>();
		buildFetchQuery(hql, params, query);
		return preparePageableQuery(hql.toString(), query, params, getEntityType()).getResultList();
	}

	@Override
	public Long count(TransactionQueryListTO query) {
		StringBuilder hql = new StringBuilder(COUNT_HQL);
		Map<String, Object> params = new HashMap<>();
		buildFetchQuery(hql, params, query);
		return prepateFetchQuery(hql.toString(), params, Long.class).getSingleResult();
	}
	
	private static final void buildFetchQuery(StringBuilder hql, Map<String, Object> params, TransactionQueryListTO query) {
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
