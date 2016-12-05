package net.dragberry.carmanager.dao;

import java.time.LocalDate;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.domain.ExchangeRate;

public interface ExchangeRateDao extends DataAccessObject<ExchangeRate, Long> {

	LocalDate findLastProcessedDate(Currency currency, Currency baseCurrency);
	
	Double getExchangeRate(Currency baseCurrency, Currency currency, LocalDate date);

}
