package net.dragberry.carmanager.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.dao.ExchangeRateDao;
import net.dragberry.carmanager.ws.client.CurrencyService;

@Service("ExchangeRateBYService")
public class ExchangeRateBYServiceBean implements ExchangeRateService {
	
	@Autowired
	private ExchangeRateDao exRateDao;
	@Autowired
	private CurrencyService currencyService;

	@Override
	public Double getExchangeRate(Currency currency, Currency baseCurrency, LocalDate date) {
		if (baseCurrency == currency) {
			return 1.0;
		}
		if (Currency.BYN == baseCurrency) {
			return getExchangeRateForBYN(currency, date);
		}
		if (Currency.BYN == currency) {
			Double exRate = getExchangeRateForBYN(baseCurrency, date);
			return exRate == null ? null : Math.pow(exRate, -1);
		}
		Double currExRate = getExchangeRateForBYN(currency, date);
		Double baseCurrExRate = getExchangeRateForBYN(baseCurrency, date);
		if (currExRate != null && baseCurrExRate != null) {
			return currExRate / baseCurrExRate;
		}
		return null;
		
	}
	
	private Double getExchangeRateForBYN(Currency currency, LocalDate date) {
		Double exRate = exRateDao.getExchangeRate(currency, Currency.BYN, date);
		if (exRate == null) {
			exRate = currencyService.getExchangeRate(currency, date);
		}
		return exRate;
	}
		
}
