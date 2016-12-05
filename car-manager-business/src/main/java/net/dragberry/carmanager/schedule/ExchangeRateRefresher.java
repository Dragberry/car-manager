package net.dragberry.carmanager.schedule;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.dao.ExchangeRateDao;
import net.dragberry.carmanager.domain.ExchangeRate;
import net.dragberry.carmanager.ws.client.CurrencyService;

@Service
public class ExchangeRateRefresher {
	
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private ExchangeRateDao exRateDao;
	
	@Scheduled(cron = "0 56 * * * *")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void updateExRates() {
		for (Currency currency : Currency.values()) {
			if (Currency.BYN == currency) {
				continue;
			}
			processCurrency(currency);
		}
		
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void processCurrency(Currency currency) {
		LocalDate dateToProcess = exRateDao.findLastProcessedDate(currency, Currency.BYN);
		LocalDate today = LocalDate.now();
		int dayDifference = today.getDayOfYear() - dateToProcess.getDayOfYear();
		if (dayDifference == 1) {
			Double exRate = currencyService.getExchangeRate(currency, dateToProcess);
			createExRateEntity(currency, dateToProcess, exRate);
		} else if (dayDifference > 1) {
			Map<LocalDate, Double> exRateMap = currencyService.getExchangeRateDynamics(currency, dateToProcess, today);
			exRateMap.entrySet().forEach(exRate -> {
				createExRateEntity(currency, exRate.getKey(), exRate.getValue());
			});
		}
	}

	private void createExRateEntity(Currency currency, LocalDate dateToProcess, Double exRate) {
		ExchangeRate exRateEntity = new ExchangeRate();
		exRateEntity.setCurrency(currency);
		exRateEntity.setBaseCurrency(Currency.BYN);
		exRateEntity.setDate(dateToProcess);
		exRateEntity.setExRate(exRate);
		exRateDao.create(exRateEntity);
	}

}
