package net.dragberry.carmanager.to;

import net.dragberry.carmanager.common.Currency;

public class CustomerSettingsTO implements TransferObject {

	private static final long serialVersionUID = -6327033084225122892L;
	
	private Currency preferredPaymentCurrency;
	
	private TransactionTypeTO preferredPaymentType;
	
	private FuelTO preferredFuel = new FuelTO();

	public Currency getPreferredPaymentCurrency() {
		return preferredPaymentCurrency;
	}

	public void setPreferredPaymentCurrency(Currency preferredPaymentCurrency) {
		this.preferredPaymentCurrency = preferredPaymentCurrency;
	}

	public TransactionTypeTO getPreferredPaymentType() {
		return preferredPaymentType;
	}

	public void setPreferredPaymentType(TransactionTypeTO preferredPaymentType) {
		this.preferredPaymentType = preferredPaymentType;
	}

	public FuelTO getPreferredFuel() {
		return preferredFuel;
	}

}
