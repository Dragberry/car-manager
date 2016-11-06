package net.dragberry.carmanager.ws.client;

import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.util.Denominator;
import net.dragberry.carmanager.ws.json.CurrencyExRate;

@Service
public class CurrencyServiceBean implements CurrencyService {
	
	private static final String EX_RATE_TEMPLATE_URL = "http://www.nbrb.by/API/ExRates/Rates/{0}?onDate={1}";
	
	private static final Map<Currency, Long> CURRENCY_MAP;
	
	static {
		Map<Currency, Long> tempMap = new HashMap<>();
		tempMap.put(Currency.USD, 145L);
		tempMap.put(Currency.EUR, 19L);
		tempMap.put(Currency.RUR, 141L);
		CURRENCY_MAP = Collections.unmodifiableMap(tempMap);
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public double getExchangeRate(Currency currency, LocalDate date) {
		URI uri = null;
		try {
			String formattedDate = DateTimeFormatter.ISO_LOCAL_DATE.format(date);
			uri = new URI(MessageFormat.format(EX_RATE_TEMPLATE_URL, CURRENCY_MAP.get(currency), formattedDate));
		    CurrencyExRate result = restTemplate.getForObject(uri, CurrencyExRate.class);
		    double exRate = date.isBefore(Denominator.DATE) ? Denominator.denominate(result.getRate()) : result.getRate();
		    System.out.println(MessageFormat.format("{0}: [{1}] BYN/{2}={3}", CurrencyServiceBean.class.getName(), formattedDate, currency, exRate));
		    return exRate;
		} catch (Exception exc) {
			System.out.println("An exception has been occured during CurrencyService#getCurrency invoking!");
			if (uri != null) {
				System.out.println(uri);
			}
			exc.printStackTrace();
		}
		return 0;
	}

}
