package net.dragberry.carmanager.web.util;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

import net.dragberry.carmanager.to.FuelTO;
import net.dragberry.carmanager.to.TransactionTO;

public class ReferenceBuilder {
	
	private static final String TYPE = "тип";
	private static final String DESCRIPTION_1 = "{0} литра бензина [{1}]";
	private static final String DESCRIPTION_2 = "{0} литров бензина [{1}]";
	private static final String DESCRIPTION_3 = "{0} литр бензина [{1}]";
	
	public static String buildDescription(TransactionTO tnx) {
		FuelTO fuel = tnx.getFuel();
		if (fuel != null) {
			Double quantity = fuel.getQuantity() != null ? fuel.getQuantity() : 0.0;
			String type = fuel.getType() != null ? fuel.getType() : TYPE;
			String quantityStr = quantity.toString();
			String description = DESCRIPTION_1;
			if (quantityStr.matches("[\\.,]")) {
				description = DESCRIPTION_1;
			} else if (quantityStr.matches("([056789]|(1[1-9]))$")) {
				description = DESCRIPTION_2;
			} else if (quantityStr.matches("\\d*[1]$")) {
				description = DESCRIPTION_3;
			}
			return MessageFormat.format(description, quantity, type);
		}
		return StringUtils.EMPTY;
	}

}
