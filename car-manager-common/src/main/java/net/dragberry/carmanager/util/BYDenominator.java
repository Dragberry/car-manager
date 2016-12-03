package net.dragberry.carmanager.util;

import java.time.LocalDate;
import java.time.Month;

public final class BYDenominator {
	
	private static final int DENOMINATION_SCALE = 10000;
	
	public static final LocalDate DATE = LocalDate.of(2016, Month.JULY, 1);

	private BYDenominator() {}
	
	public static double denominate(double value) {
		return value / DENOMINATION_SCALE;
	}

}
