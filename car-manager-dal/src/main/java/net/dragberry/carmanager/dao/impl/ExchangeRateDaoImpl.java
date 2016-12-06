package net.dragberry.carmanager.dao.impl;

import java.time.LocalDate;
import java.time.Month;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.dao.ExchangeRateDao;
import net.dragberry.carmanager.domain.ExchangeRate;

@Repository("exchangeRateDao")
public class ExchangeRateDaoImpl extends AbstractDao<ExchangeRate> implements ExchangeRateDao {

	private static final LocalDate START_DATE = LocalDate.of(2014, Month.JANUARY, 1);
	
	public ExchangeRateDaoImpl() {
		super(ExchangeRate.class);
	}

	@Override
	public LocalDate findLastProcessedDate(Currency currency, Currency baseCurrency) {
		try {
			LocalDate lastProcessedDate = null;
			lastProcessedDate = getEntityManager()
				.createNamedQuery(ExchangeRate.FIND_LAST_PROCESSED_DATE, LocalDate.class)
				.setParameter("currency", currency)
				.setParameter("baseCurrency", baseCurrency)
				.getSingleResult();
			return lastProcessedDate == null ? START_DATE : lastProcessedDate;
		} catch (NoResultException nre) {
			return START_DATE;
		}
	}

	@Override
	public Double getExchangeRate(Currency currency, Currency baseCurrency, LocalDate date) {
		try {
			return getEntityManager().createNamedQuery(ExchangeRate.FIND_EXCHANGE_RATE, Double.class)
				.setParameter("date", date)
				.setParameter("baseCurrency", baseCurrency)
				.setParameter("currency", currency)
				.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}


}
