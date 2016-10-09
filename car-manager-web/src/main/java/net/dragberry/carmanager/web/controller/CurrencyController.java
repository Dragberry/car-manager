package net.dragberry.carmanager.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.dragberry.carmanager.common.Currency;

@Controller
public class CurrencyController {

	@RequestMapping("/service/currency/list")
	@ResponseBody
	public Currency[] fetchCurrencyList() {
		return Currency.values();
	}
}
