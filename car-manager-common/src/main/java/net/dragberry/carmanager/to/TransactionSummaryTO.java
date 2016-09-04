package net.dragberry.carmanager.to;

import java.math.BigDecimal;

import net.dragberry.carmanager.common.Currency;

public class TransactionSummaryTO implements TransferObject {

	private static final long serialVersionUID = -1108741730332854092L;
	
	private BigDecimal totalAmount;
	
	private BigDecimal totalAmountByCustomer;
	
	private BigDecimal totalFuelAmount;
	
	private Currency displayCurrency;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTotalAmountByCustomer() {
		return totalAmountByCustomer;
	}

	public void setTotalAmountByCustomer(BigDecimal totalAmountByCustomer) {
		this.totalAmountByCustomer = totalAmountByCustomer;
	}

	public BigDecimal getTotalFuelAmount() {
		return totalFuelAmount;
	}

	public void setTotalFuelAmount(BigDecimal totalFuelAmount) {
		this.totalFuelAmount = totalFuelAmount;
	}

	public Currency getDisplayCurrency() {
		return displayCurrency;
	}

	public void setDisplayCurrency(Currency displayCurrency) {
		this.displayCurrency = displayCurrency;
	}

}
