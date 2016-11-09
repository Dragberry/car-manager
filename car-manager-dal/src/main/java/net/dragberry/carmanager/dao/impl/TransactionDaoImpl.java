package net.dragberry.carmanager.dao.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.common.TransactionStatus;
import net.dragberry.carmanager.dao.TransactionDao;
import net.dragberry.carmanager.domain.Transaction;
import net.dragberry.carmanager.domain.TransactionType;
import net.dragberry.carmanager.to.TransactionQueryListTO;

@Repository
public class TransactionDaoImpl extends AbstractDao<Transaction> implements TransactionDao {

	public TransactionDaoImpl() {
		super(Transaction.class);
	}
	
	@Override
	public List<Transaction> fetchInactiveTransactions() {
		return getEntityManager().createNamedQuery(Transaction.FIND_BY_STATUS, getEntityType())
				.setParameter("status", TransactionStatus.PROCESSING)
				.getResultList();
	}

	@Override
	public List<Transaction> fetchList(TransactionQueryListTO query) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Transaction> cq = cb.createQuery(Transaction.class);
		Root<Transaction> tRoot = cq.from(Transaction.class);
		tRoot.fetch("transactionType");
		
		cq.where(buildConditions(cb, tRoot, query));
		cq.orderBy(cb.desc(tRoot.get("executionDate")));
		cq.select(tRoot);
		
		return getEntityManager().createQuery(cq).getResultList();
	}

	@Override
	public Long count(TransactionQueryListTO query) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Transaction> tRoot = cq.from(Transaction.class);
		
		cq.where(buildConditions(cb, tRoot, query));
		cq.select(cb.count(tRoot));
		
		return getEntityManager().createQuery(cq).getSingleResult();
	}
	
	@Override
	public Object[] summary(TransactionQueryListTO query) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		Root<Transaction> tRoot = cq.from(Transaction.class);
		
		cq.where(buildConditions(cb, tRoot, query));
		cq.multiselect(
				cb.sum(
						cb.<Number>selectCase().when(cb.notEqual(tRoot.<Long>get("transactionType"), TransactionType.LOAN_PAYMENT_KEY), 
								cb.<Number>selectCase().when(cb.equal(tRoot.get("currency"), Currency.USD), 
										tRoot.<Number>get("amount")).otherwise(cb.quot(tRoot.<BigDecimal>get("amount"), tRoot.<Double>get("exchangeRate")))
								).otherwise(BigDecimal.ZERO)
						),
				cb.sum(
						cb.<Number>selectCase().when(cb.equal(tRoot.<Long>get("customer"), query.getCarOwnerKey()), 
								cb.<Number>selectCase().when(cb.equal(tRoot.get("currency"), Currency.USD), 
										tRoot.<Number>get("amount")).otherwise(cb.quot(tRoot.<BigDecimal>get("amount"), tRoot.<Double>get("exchangeRate")))
								).otherwise(BigDecimal.ZERO)
						),
				cb.sum(
						cb.<Number>selectCase().when(cb.equal(tRoot.<Long>get("transactionType"), TransactionType.FUEL_KEY), 
								cb.<Number>selectCase().when(cb.equal(tRoot.get("currency"), Currency.USD), 
										tRoot.<Number>get("amount")).otherwise(cb.quot(tRoot.<BigDecimal>get("amount"), tRoot.<Double>get("exchangeRate")))
								).otherwise(BigDecimal.ZERO)
						)
				);
		return getEntityManager().createQuery(cq).getSingleResult();
	}
	
	private static Predicate[] buildConditions(CriteriaBuilder cb, Root<Transaction> tRoot, TransactionQueryListTO query) {
		List<Predicate> predicates = new ArrayList<>();
		if (query.getCustomerKey() != null) {
			predicates.add(cb.equal(tRoot.<Long>get("customer"), query.getCustomerKey()));
		}
		if (query.getCarKey() != null) {
			predicates.add(cb.equal(tRoot.<Long>get("car"), query.getCarKey()));
		}
		if (query.getDateFrom() != null) {
			predicates.add(cb.greaterThan(tRoot.<LocalDate>get("executionDate"), query.getDateFrom()));
		}
		if (query.getDateTo() != null) {
			predicates.add(cb.lessThanOrEqualTo(tRoot.<LocalDate>get("executionDate"), query.getDateTo()));
		}
		if (query.getAmountFrom() != null) {
			predicates.add(cb.lt(tRoot.<BigDecimal>get("amount"), query.getAmountFrom()));
		}
		if (query.getAmountTo() != null) {
			predicates.add(cb.ge(tRoot.<BigDecimal>get("amount"), query.getAmountTo()));
		}
		if (query.getFuelQuantityFrom() != null) {
			predicates.add(cb.lt(tRoot.get("fuel").<Double>get("quantity"), query.getFuelQuantityFrom()));
		}
		if (query.getFuelQuantityTo() != null) {
			predicates.add(cb.ge(tRoot.get("quantity").<Double>get("quantity"), query.getFuelQuantityTo()));
		}
		if (CollectionUtils.isNotEmpty(query.getTransactionTypeKeyList())) {
			predicates.add(tRoot.get("transactionType").in(query.getTransactionTypeKeyList()));
		}
		if (CollectionUtils.isNotEmpty(query.getCurrencyList())) {
			predicates.add(tRoot.get("currency").in(query.getCurrencyList()));
		}
		if (CollectionUtils.isNotEmpty(query.getStatuses())) {
			predicates.add(tRoot.get("status").in(query.getStatuses()));
		}
		return predicates.toArray(new Predicate[]{});
	}
	
}
