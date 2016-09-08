package net.dragberry.carmanager.domain;

import java.math.BigDecimal;

import net.dragberry.carmanager.common.Currency;

public enum CustomerSetting {
	
	PREFERRED_PAYMENT_CURRENCY(Currency.class),
	PREFERRED_PAYMENT_TYPE(String.class),
	PREFERRED_FUEL_TYPE(String.class),
	PREFERRED_FUEL_QUANTITY(Double.class),
	LAST_FUEL_COST(BigDecimal.class);
	
	private Class<?> type;
	
	private CustomerSetting(Class<?> type) {
		this.type = type;
	}
	
	public static <T> T getValue(String value) {
		return null;
	}

}
