package net.dragberry.carmanager.to;

import java.math.BigDecimal;

import net.dragberry.carmanager.common.Currency;

public class TransactionSummaryTO implements TransferObject {

	private static final long serialVersionUID = -1108741730332854092L;
	
	private BigDecimal totalAmount = BigDecimal.ZERO;
	
	private BigDecimal totalAmountByCustomer = BigDecimal.ZERO;
	
	private BigDecimal totalFuelAmount = BigDecimal.ZERO;
	
	private BigDecimal amountPerMonth = BigDecimal.ZERO;
	
	private BigDecimal totalFuel = BigDecimal.ZERO;
	
	private BigDecimal fuelPerMonth = BigDecimal.ZERO;
	
	private BigDecimal fuelCostPerMonth = BigDecimal.ZERO;
	
	private BigDecimal totalRepairAmount = BigDecimal.ZERO;
	
	private Currency displayCurrency;

	
	public BigDecimal getFuelCostPerMonth() {
		return fuelCostPerMonth;
	}

	public void setFuelCostPerMonth(BigDecimal fuelCostPerMonth) {
		this.fuelCostPerMonth = fuelCostPerMonth;
	}

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

	public BigDecimal getAmountPerMonth() {
		return amountPerMonth;
	}

	public void setAmountPerMonth(BigDecimal amountPerMounth) {
		this.amountPerMonth = amountPerMounth;
	}

	public BigDecimal getTotalFuel() {
		return totalFuel;
	}
	
	public void addTotalFuel(Double totalFuel) {
		this.totalFuel = this.totalFuel.add(new BigDecimal(totalFuel));
	}

	public void setTotalFuel(BigDecimal totalFuel) {
		this.totalFuel = totalFuel;
	}

	public BigDecimal getFuelPerMonth() {
		return fuelPerMonth;
	}
	
	public void setFuelPerMonth(BigDecimal fuelPerMonth) {
		this.fuelPerMonth = fuelPerMonth;
	}

	public BigDecimal getTotalRepairAmount() {
		return totalRepairAmount;
	}
	
	public void addTotalRepairAmount(BigDecimal totalRepairAmount) {
		this.totalRepairAmount = this.totalRepairAmount.add(totalRepairAmount);
	}

	public void setTotalRepairAmount(BigDecimal totalRepairAmount) {
		this.totalRepairAmount = totalRepairAmount;
	}

	
}
