package net.dragberry.carmanager.schedule;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.dao.ExchangeRateDao;
import net.dragberry.carmanager.domain.ExchangeRate;
import net.dragberry.carmanager.util.BYDenominator;
import net.dragberry.carmanager.ws.client.CurrencyService;

@Service
public class ExchangeRateRefresher {
	
	@Autowired
	private CurrencyService currencyService;
	@Autowired
	private ExchangeRateDao exRateDao;
	
	@Scheduled(cron = "0 28 * * * *")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void updateExRates() {
		for (Currency currency : Currency.values()) {
			if (Currency.BYN == currency) {
				continue;
			}
			processCurrency(currency);
		}
		
	}
	
	private void processCurrency(Currency currency) {
		LocalDate dateToProcess = exRateDao.findLastProcessedDate(currency, Currency.BYN);
		LocalDate today = LocalDate.now();
		int dayDifference = today.getDayOfYear() - dateToProcess.getDayOfYear();
		if (dayDifference == 1) {
			Double exRate = currencyService.getExchangeRate(currency, dateToProcess);
			createExRateEntity(currency, dateToProcess, exRate);
		} else if (dayDifference > 1) {
			periods().forEach(period -> {
				try {
					processCurrencyPeriod(currency, period);
				} catch (Exception e) {
					System.out.println(MessageFormat.format("An error has been occured during processing periof {0} for currency {1}", period, currency));
					return;
				}
			});
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void processCurrencyPeriod(Currency currency, Period period) throws Exception {
		long startTime = System.currentTimeMillis();
		Map<LocalDate, Double> exRateMap = currencyService.getExchangeRateDynamics(currency, period.from(), period.to());
		exRateMap.entrySet().forEach(exRate -> {
			createExRateEntity(currency, exRate.getKey(), exRate.getValue());
		});
		long time = System.currentTimeMillis() - startTime;
		System.out.println(MessageFormat.format("Time elapsed: {0} for Currency {1} for period {2}", time, currency, period));
	}

	private void createExRateEntity(Currency currency, LocalDate dateToProcess, Double exRate) {
		ExchangeRate exRateEntity = new ExchangeRate();
		exRateEntity.setCurrency(currency);
		exRateEntity.setBaseCurrency(Currency.BYN);
		exRateEntity.setDate(dateToProcess);
		exRateEntity.setExRate(exRate);
		exRateDao.create(exRateEntity);
	}
	
	private static Stream<Period> periods() {
		Builder<Period> builder = Stream.builder();
		LocalDate dateFrom = LocalDate.of(2014, 1, 1);
		LocalDate today = LocalDate.now();
		LocalDate dateTo = null;
		do {
			dateTo = dateFrom.with(TemporalAdjusters.lastDayOfYear());
			if (dateFrom.isBefore(BYDenominator.DATE)) {
				if (dateTo.isAfter(BYDenominator.DATE)) {
					dateTo = BYDenominator.DATE.minusDays(1);
				}
			}
			if (dateTo.isAfter(today)) {
				dateTo = today;
			}
			builder.accept(Period.create(dateFrom, dateTo));
			dateFrom = dateTo.plusDays(1);
		} while (dateTo.isBefore(today));
		return builder.build();
	}

	private static class Period {
		private LocalDate from;
		private LocalDate to;
		
		public static final Period create(LocalDate from, LocalDate to) {
			return new Period(from, to);
		}
		
		public Period(LocalDate from, LocalDate to) {
			this.from = from;
			this.to= to;
		}

		public LocalDate from() {
			return from;
		}

		public LocalDate to() {
			return to;
		}
		
		@Override
		public String toString() {
			String formattedStartDate = DateTimeFormatter.ISO_LOCAL_DATE.format(from);
			String formattedEndDate = DateTimeFormatter.ISO_LOCAL_DATE.format(to);
			return MessageFormat.format("[{0}--{1}]", formattedStartDate, formattedEndDate);
		}
	}

}
