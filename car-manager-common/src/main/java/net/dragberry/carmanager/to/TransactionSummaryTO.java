package net.dragberry.carmanager.to;

import java.math.BigDecimal;

import net.dragberry.carmanager.common.Currency;

public class TransactionSummaryTO implements TransferObject {

	private static final long serialVersionUID = -1108741730332854092L;
	
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	private BigDecimal totalAmountByCustomer = BigDecimal.ZERO;
	
	private BigDecimal totalFuelAmount = BigDecimal.ZERO;
	
	private BigDecimal amountPerMounth = BigDecimal.ZERO;
	
	private Currency displayCurrency;

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	public void addTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = this.totalAmount.add(totalAmount);
	}

	public BigDecimal getTotalAmountByCustomer() {
		return totalAmountByCustomer;
	}

	public void setTotalAmountByCustomer(BigDecimal totalAmountByCustomer) {
		this.totalAmountByCustomer = totalAmountByCustomer;
	}
	
	public void addTotalAmountByCustomer(BigDecimal totalAmount) {
		this.totalAmountByCustomer = this.totalAmountByCustomer.add(totalAmount);
	}

	public BigDecimal getTotalFuelAmount() {
		return totalFuelAmount;
	}

	public void setTotalFuelAmount(BigDecimal totalFuelAmount) {
		this.totalFuelAmount = totalFuelAmount;
	}
	
	public void addTotalFuelAmount(BigDecimal totalFuelAmount) {
		this.totalFuelAmount = this.totalFuelAmount.add(totalFuelAmount);
	}

	public Currency getDisplayCurrency() {
		return displayCurrency;
	}

	public void setDisplayCurrency(Currency displayCurrency) {
		this.displayCurrency = displayCurrency;
	}

	public BigDecimal getAmountPerMounth() {
		return amountPerMounth;
	}

	public void setAmountPerMounth(BigDecimal amountPerMounth) {
		this.amountPerMounth = amountPerMounth;
	}

	
}
