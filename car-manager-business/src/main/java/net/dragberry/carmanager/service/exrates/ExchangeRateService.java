package net.dragberry.carmanager.service.exrates;

import java.time.LocalDate;

import net.dragberry.carmanager.common.Currency;

public interface ExchangeRateService {

	Double getExchangeRate(Currency currency, Currency baseCurrency, LocalDate date);
}
