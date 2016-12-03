package net.dragberry.carmanager.ws.client;

import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.util.BYDenominator;
import net.dragberry.carmanager.ws.json.CurrencyExRate;

@Service("CurrencyServiceBYBean")
public class CurrencyServiceBYBean implements CurrencyService {
	
	private static final String EX_RATE_TEMPLATE_URL = "http://www.nbrb.by/API/ExRates/Rates/{0}?onDate={1}&ParamMode=2";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Override
	public Double getExchangeRate(Currency currency, LocalDate date) {
		URI uri = null;
		try {
			String formattedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
			uri = new URI(MessageFormat.format(EX_RATE_TEMPLATE_URL, currency, formattedDate));
		    CurrencyExRate result = restTemplate.getForObject(uri, CurrencyExRate.class);
		    double exRate = denominate(date, result);
		    System.out.println(MessageFormat.format("{0}: [{1}] BYN/{2}={3}", CurrencyServiceBYBean.class.getName(), formattedDate, currency, exRate));
		    return exRate;
		} catch (Exception exc) {
			System.out.println("An exception has been occured during CurrencyService#getExchangeRate invoking!");
			if (uri != null) {
				System.out.println(uri);
			}
			exc.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Get the exchange rate  of numerator/denominator currency
	 * @param numeratorCurrency
	 * @param denominatorCurrency
	 * @param date
	 * @return
	 */
	public Double getExchangeRate(Currency numeratorCurrency, Currency denominatorCurrency, LocalDate date) {
		URI uri = null;
		try {
			double exRateNumerator = 1.0;
			double exRateDenominator = 1.0;
			String formattedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
			if (Currency.BYN != numeratorCurrency) {
				uri = new URI(MessageFormat.format(EX_RATE_TEMPLATE_URL, numeratorCurrency, formattedDate));
				CurrencyExRate numeratorResult = restTemplate.getForObject(uri, CurrencyExRate.class);
				exRateNumerator = calculateExRate(date, numeratorResult);
			}
			if (Currency.BYN != denominatorCurrency) {
				uri = new URI(MessageFormat.format(EX_RATE_TEMPLATE_URL, denominatorCurrency, formattedDate));
				CurrencyExRate denominatorResult = restTemplate.getForObject(uri, CurrencyExRate.class);
				exRateDenominator = calculateExRate(date, denominatorResult);
			}
			return exRateNumerator / exRateDenominator;
		} catch (Exception exc) {
			System.out.println("An exception has been occured during CurrencyService#getExchangeRate invoking!");
			if (uri != null) {
				System.out.println(uri);
			}
			exc.printStackTrace();
		}
		return null;
	}

	private double calculateExRate(LocalDate date, CurrencyExRate numeratorResult) {
		return denominate(date, numeratorResult) / numeratorResult.getScale();
	}

	private double denominate(LocalDate date, CurrencyExRate denominatorResult) {
		return date.isBefore(BYDenominator.DATE) ? BYDenominator.denominate(denominatorResult.getRate()) : denominatorResult.getRate();
	}

}
