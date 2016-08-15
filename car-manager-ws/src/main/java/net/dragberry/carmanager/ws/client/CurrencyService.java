package net.dragberry.carmanager.ws.client;

import java.time.LocalDate;

public interface CurrencyService {

	double getCurrency(String currecnyCode, LocalDate date);
}
