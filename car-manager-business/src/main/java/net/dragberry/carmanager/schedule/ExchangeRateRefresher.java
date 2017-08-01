package net.dragberry.carmanager.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import net.dragberry.carmanager.service.exrates.ExchangeRateService;

@Service
public class ExchangeRateRefresher {
	
	@Autowired
	@Qualifier("BYExchangeRateServiceBean")
	private ExchangeRateService exRateService;
	
	@Scheduled(cron = "0 09 15 ? * *")
	public void updateByExchangeRates() {
		exRateService.updateExRates();
	}
	

}
