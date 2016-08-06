package net.dragberry.carmanager.domain;

import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CAR")
@AttributeOverrides({
	@AttributeOverride(column = @Column(name =  "CAR_KEY"), name = "entityKey")
})
public class Car extends AbstractEntity {

	private static final long serialVersionUID = 6026805942501399545L;
	
	@Column(name = "MODEL")
	private String model;
	@Column(name = "PURCHASE_DATE")
	private LocalDate purchaseDate;
	@Column(name = "SALE_DATE")
	private LocalDate saleDate;
	@Column(name = "BRAND")
	private String brand;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "OWNER", referencedColumnName = "CUSTOMER_KEY")
	private Customer owner;
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public LocalDate getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(LocalDate saleDate) {
		this.saleDate = saleDate;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Customer getOwner() {
		return owner;
	}
	public void setOwner(Customer owner) {
		this.owner = owner;
	}
	
}
