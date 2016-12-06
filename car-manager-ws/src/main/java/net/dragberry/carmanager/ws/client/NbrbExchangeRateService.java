package net.dragberry.carmanager.ws.client;

import java.time.LocalDate;
import java.util.Map;

import net.dragberry.carmanager.common.Currency;

public interface NbrbExchangeRateService {
	
	Map<LocalDate, Double> getExchangeRateDynamics(Currency currency, LocalDate startDate, LocalDate endDate);

	Double getExchangeRate(Currency currency, LocalDate date);
	
}
