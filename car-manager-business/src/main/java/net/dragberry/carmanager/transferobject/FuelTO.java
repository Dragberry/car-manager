package net.dragberry.carmanager.transferobject;

public class FuelTO implements TransferObject {

	private static final long serialVersionUID = -3211556035025074390L;
	
	private Long fuelKey;
	
	private Double cost;
	
	private Double quantity;
	
	private String type;

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getFuelKey() {
		return fuelKey;
	}

	public void setFuelKey(Long fuelKey) {
		this.fuelKey = fuelKey;
	}
	
}
