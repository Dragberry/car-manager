package net.dragberry.carmanager.service.exrates;

import java.time.LocalDate;

import net.dragberry.carmanager.common.Currency;

public interface ExchangeRateService {

	Double getExchangeRate(Currency currency, Currency baseCurrency, LocalDate date);

	/**
	 * Start process of updating of exchange rates. Return
	 * 
	 * @return <code>true</code> if process has been started or <code>false</code>
	 *         if process hasn't been started (process is already run)
	 */
	Boolean updateExRates();

	Boolean isRefreshing();
	
	
}
