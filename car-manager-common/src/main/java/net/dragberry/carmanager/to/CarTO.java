package net.dragberry.carmanager.to;

public class CarTO implements TransferObject {

	private static final long serialVersionUID = 8083636806543699481L;

	private Long carKey;
	
	private String brand;
	
	private String model;

	public Long getCarKey() {
		return carKey;
	}

	public void setCarKey(Long carKey) {
		this.carKey = carKey;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
	
}
