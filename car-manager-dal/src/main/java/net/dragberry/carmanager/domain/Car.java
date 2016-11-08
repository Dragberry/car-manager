package net.dragberry.carmanager.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "CAR")
@NamedQueries({
	@NamedQuery(
			name = Car.CARS_FOR_CUSTOMER_QUERY,
			query = "select c from Car c where c.owner.entityKey = :owner")
})
@TableGenerator(
		name = "CAR_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "CAR_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Car extends AbstractEntity {

	private static final long serialVersionUID = 6026805942501399545L;
	
	public static final String CARS_FOR_CUSTOMER_QUERY = "Car.CarsForCustomer";
	
	@Id
	@Column(name = "CAR_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "CAR_GEN")
	private Long entityKey;
	@Column(name = "MODEL")
	private String model;
	@Column(name = "PURCHASE_DATE")
	private LocalDate purchaseDate;
	@Column(name = "SALE_DATE")
	private LocalDate saleDate;
	@Column(name = "BRAND")
	private String brand;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_KEY", referencedColumnName = "CUSTOMER_KEY")
	private Customer owner;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}
	
	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}
	
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
