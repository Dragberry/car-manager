package net.dragberry.carmanager.ws.client;

import java.net.URI;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.dragberry.carmanager.ws.json.CurrencyExRate;

@Service
public class CurrencyServiceBean implements CurrencyService {
	
	private static final LocalDate DENOMINATION_DATE = LocalDate.of(2016, Month.JULY, 1);
	private static final String EX_RATE_TEMPLATE_URL = "http://www.nbrb.by/API/ExRates/Rates/{0}?onDate={1}";
	
	private static final Map<String, Long> CURRENCY_MAP;
	
	static {
		Map<String, Long> tempMap = new HashMap<>();
		tempMap.put("USD", 145L);
		
		CURRENCY_MAP = Collections.unmodifiableMap(tempMap);
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public double getCurrency(String currecnyCode, LocalDate date) {
		URI uri = null;
		try {
			uri = new URI(MessageFormat.format(EX_RATE_TEMPLATE_URL, CURRENCY_MAP.get(currecnyCode), DateTimeFormatter.ISO_LOCAL_DATE.format(date)));
		    CurrencyExRate result = restTemplate.getForObject(uri, CurrencyExRate.class);
		    return date.isBefore(DENOMINATION_DATE) ? denominate(result.getRate()) : result.getRate();
		} catch (Exception exc) {
			System.out.println("An exception has been occured during CurrencyService#getCurrency invoking!");
			exc.printStackTrace();
		}
		return 0;
	}
	
	private static double denominate(double byr) {
		return byr / 10000;
	}

}
