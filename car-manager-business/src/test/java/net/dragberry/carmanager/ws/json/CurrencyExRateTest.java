package net.dragberry.carmanager.ws.json;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class CurrencyExRateTest {

	@Test
	public void jsonSerializationTest() throws Exception {
	     CurrencyExRate currencyExRate = new CurrencyExRate();
	     currencyExRate.setCurrencyCode("USD");
	     currencyExRate.setDate(LocalDateTime.now());
	     currencyExRate.setId(145L);
	     currencyExRate.setScale(1);
	     currencyExRate.setRate(15396.00);

	     ObjectMapper mapper = new ObjectMapper();
	     mapper.registerModule(new JavaTimeModule());
	     mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	     System.out.println(mapper.writeValueAsString(currencyExRate));
	}
}
