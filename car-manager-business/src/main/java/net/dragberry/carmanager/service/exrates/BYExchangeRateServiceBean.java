package net.dragberry.carmanager.service.exrates;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.dao.ExchangeRateDao;
import net.dragberry.carmanager.domain.ExchangeRate;
import net.dragberry.carmanager.util.BYDenominator;
import net.dragberry.carmanager.ws.client.NbrbExchangeRateService;

@Service("BYExchangeRateServiceBean")
public class BYExchangeRateServiceBean implements ExchangeRateService {
	
	private static final Logger LOG = LogManager.getLogger(BYExchangeRateServiceBean.class);
	
	private AtomicBoolean isRefreshing = new AtomicBoolean(false);
	
	@Autowired
	private ExchangeRateDao exRateDao;
	@Autowired
	private NbrbExchangeRateService nbrbExRateService;

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
			exRate = nbrbExRateService.getExchangeRate(currency, date);
		}
		return exRate;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Boolean updateExRates() {
		if (isRefreshing.compareAndSet(false, true)) {
			new Thread(() -> {
				LOG.info("Updating exchange rates started...");
				Stream.of(Currency.values())
					.filter(currency -> Currency.BYN != currency)
					.parallel()
					.forEach(this::processCurrency);
				isRefreshing.set(false);
				LOG.info("Updating exchange rates finished...");
			});
			return Boolean.TRUE;
		} 
		return Boolean.FALSE;
	}
	
	private void processCurrency(Currency currency) {
		LocalDate dateToProcess = exRateDao.findLastProcessedDate(currency, Currency.BYN);
		LOG.info(MessageFormat.format("Last processed date {0} for currency {1}", dateToProcess, currency));
		LocalDate today = LocalDate.now();
		int dayDifference = today.getDayOfYear() - dateToProcess.getDayOfYear();
		if (dayDifference == 1) {
			Double exRate = nbrbExRateService.getExchangeRate(currency, dateToProcess);
			createExRateEntity(currency, dateToProcess, exRate);
		} else if (dayDifference > 1 || dayDifference < 0) {
			periods(dateToProcess.plusDays(1)).forEach(period -> {
				try {
					processCurrencyPeriod(currency, period);
				} catch (Exception e) {
					LOG.info(MessageFormat.format("An error has been occured during processing periof {0} for currency {1}", period, currency), e);
					e.printStackTrace();
					return;
				}
			});
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private void processCurrencyPeriod(Currency currency, Period period) throws Exception {
		long startTime = System.currentTimeMillis();
		Map<LocalDate, Double> exRateMap = nbrbExRateService.getExchangeRateDynamics(currency, period.from(), period.to());
		exRateMap.entrySet().forEach(exRate -> {
			createExRateEntity(currency, exRate.getKey(), exRate.getValue());
		});
		long time = System.currentTimeMillis() - startTime;
		LOG.info(MessageFormat.format("Time elapsed: {0} for Currency {1} for period {2}", time, currency, period));
	}

	private void createExRateEntity(Currency currency, LocalDate dateToProcess, Double exRate) {
		ExchangeRate exRateEntity = new ExchangeRate();
		exRateEntity.setCurrency(currency);
		exRateEntity.setBaseCurrency(Currency.BYN);
		exRateEntity.setDate(dateToProcess);
		exRateEntity.setExRate(exRate);
		exRateDao.create(exRateEntity);
	}
	
	private static Stream<Period> periods(LocalDate dateToProcess) {
		Builder<Period> builder = Stream.builder();
		LocalDate dateFrom = dateToProcess != null ? dateToProcess : LocalDate.of(2014, 1, 1);
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
			builder.add(Period.create(dateFrom, dateTo));
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
			return MessageFormat.format("[{0}/{1}]", formattedStartDate, formattedEndDate);
		}
	}
		
}
