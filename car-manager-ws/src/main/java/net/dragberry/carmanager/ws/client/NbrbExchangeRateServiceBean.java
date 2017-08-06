package net.dragberry.carmanager.ws.client;

import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.util.BYDenominator;
import net.dragberry.carmanager.ws.json.CurrencyExRate;

@Service("NbrbExchangeRateServiceBean")
public class NbrbExchangeRateServiceBean implements NbrbExchangeRateService {
	
	private static final Logger LOG = LogManager.getLogger(NbrbExchangeRateServiceBean.class);
	
	private static final String EX_RATE_TEMPLATE_URL = "http://www.nbrb.by/API/ExRates/Rates/{0}?onDate={1}&ParamMode=2";
	private static final String EX_RATE_DYNAMICS_TEMPLATE_URL = "http://www.nbrb.by/API/ExRates/Rates/Dynamics/{0}?startDate={1}&endDate={2}";
	
	private static final long RUB_BEFORE_DENOMINATION = 190L;
	private static final long RUB_AFTER_DENOMINATION = 298L;
	private static final long EUR_BEFORE_DENOMINATION = 19L;
	private static final long EUR_AFTER_DENOMINATION = 292L;
	
	private static final Map<Currency, Long> BY_CURRENCY_CODES = new HashMap<>();
	static {
		BY_CURRENCY_CODES.put(Currency.USD, 145L);
		BY_CURRENCY_CODES.put(Currency.EUR, 292L);
	}
	
	private static Long getInternalCurrecnyCode(Currency currency, LocalDate date) {
		switch (currency) {
			case RUB:
				if (date.isBefore(BYDenominator.DATE)) {
					return RUB_BEFORE_DENOMINATION;
				}
				return RUB_AFTER_DENOMINATION;
			case EUR:
				if (date.isBefore(BYDenominator.DATE)) {
					return EUR_BEFORE_DENOMINATION;
				}
				return EUR_AFTER_DENOMINATION;
			default:
				return BY_CURRENCY_CODES.get(currency);
		}
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Double getExchangeRate(Currency currency, LocalDate date) {
		URI uri = null;
		try {
			String formattedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
			uri = new URI(MessageFormat.format(EX_RATE_TEMPLATE_URL, currency, formattedDate));
		    CurrencyExRate result = restTemplate.getForObject(uri, CurrencyExRate.class);
		    double exRate = denominate(result);
		    LOG.info(MessageFormat.format("Get exRate: [{0}] BYN/{1}={2}", formattedDate, currency, exRate));
		    return exRate;
		} catch (Exception exc) {
			LOG.error("An exception has been occured during CurrencyService#getExchangeRate invoking!", exc);
			if (uri != null) {
				LOG.error(uri);
			}
		}
		return null;
	}
	
	@Override
	public Map<LocalDate, Double> getExchangeRateDynamics(Currency currency, LocalDate startDate, LocalDate endDate) throws Exception {
		if (startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("Start date cannot be after end date!");
		}
		return getExchangeRates(currency, startDate, endDate);
	}
	
	private Map<LocalDate, Double> getExchangeRates(Currency currency, LocalDate startDate, LocalDate endDate) throws Exception {
		URI uri = null;
		try {
			String formattedStartDate = DateTimeFormatter.ISO_LOCAL_DATE.format(startDate);
			String formattedEndDate = DateTimeFormatter.ISO_LOCAL_DATE.format(endDate);
			uri = new URI(MessageFormat.format(EX_RATE_DYNAMICS_TEMPLATE_URL, getInternalCurrecnyCode(currency, startDate), formattedStartDate, formattedEndDate));
			CurrencyExRate[] result = restTemplate.getForObject(uri, CurrencyExRate[].class);
			LOG.info(MessageFormat.format("Get exRates BYN/{2} for period {0}/{1}", startDate, endDate, currency));
			Map<LocalDate, Double> exRateMap = new TreeMap<>();
			for (CurrencyExRate exRate : result) {
				exRateMap.put(exRate.getDate().toLocalDate(), denominate(exRate));
		    }
		    return exRateMap;
		} catch (Exception exc) {
			LOG.error("An exception has been occured during CurrencyService#getExchangeRates invoking!", exc);
			if (uri != null) {
				LOG.error(uri);
			}
			throw exc;
		}
	}

	private double denominate(CurrencyExRate curExRate) {
		return curExRate.getDate().toLocalDate().isBefore(BYDenominator.DATE) ? BYDenominator.denominate(curExRate.getRate()) : curExRate.getRate();
	}

}
