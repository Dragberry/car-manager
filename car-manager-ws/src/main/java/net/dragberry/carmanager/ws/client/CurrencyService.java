package net.dragberry.carmanager.ws.client;

import java.time.LocalDate;

import net.dragberry.carmanager.common.Currency;

public interface CurrencyService {

	double getExchangeRate(Currency currency, LocalDate date);
}
