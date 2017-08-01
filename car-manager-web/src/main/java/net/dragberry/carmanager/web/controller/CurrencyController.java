package net.dragberry.carmanager.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.common.Currency;
import net.dragberry.carmanager.service.exrates.ExchangeRateService;

@Controller
public class CurrencyController {
	
	@Autowired
	private ExchangeRateService exRateService;

	@RequestMapping("/service/currency/list")
	@ResponseBody
	public Currency[] fetchCurrencyList() {
		return Currency.values();
	}
	
	@RequestMapping("/service/currency/refresh")
	@ResponseBody
	public void refreshCurrencies() {
		exRateService.updateExRates();
	}
}
