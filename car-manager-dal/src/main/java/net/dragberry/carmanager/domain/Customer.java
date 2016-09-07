package net.dragberry.carmanager.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CUSTOMER")
@AttributeOverrides({
	@AttributeOverride(column = @Column(name =  "CUSTOMER_KEY"), name = "entityKey")
})
@NamedQueries({
	@NamedQuery(
			name = "Customer.fetchPayers",
			query = "select c from Customer c left join c.payers p where c.entityKey  = :customerKey or c.entityKey = p.entityKey")
})
public class Customer extends AbstractEntity {

	private static final long serialVersionUID = 1951614770708868066L;

	@Column(name = "CUSTOMER_NAME")
	private String customerName;
	
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "BIRTHDATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "ENABLED")
	private Boolean enabled;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "CUSTOMER_ROLE", 
        joinColumns = @JoinColumn(name = "CUSTOMER_KEY", referencedColumnName = "CUSTOMER_KEY"), 
        inverseJoinColumns = @JoinColumn(name = "ROLE_KEY", referencedColumnName = "ROLE_KEY"))
	private Set<Role> roles = new HashSet<Role>();
	
	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "CUSTOMER_SETTING", joinColumns = @JoinColumn(name = "CUSTOMER_KEY", referencedColumnName = "CUSTOMER_KEY"))
	@MapKeyColumn(name = "NAME")
	@Column(name = "VALUE")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<CustomerSetting, String> settings;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CUSTOMER_PAYER", 
	    joinColumns = @JoinColumn(name = "CUSTOMER_KEY", referencedColumnName = "CUSTOMER_KEY"), 
	    inverseJoinColumns = @JoinColumn(name = "PAYER_KEY", referencedColumnName = "CUSTOMER_KEY"))
	private Set<Customer> payers;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<CustomerSetting, String> getSettings() {
		return settings;
	}

	public void setSettings(Map<CustomerSetting, String> settings) {
		this.settings = settings;
	}

	public Set<Customer> getPayers() {
		return payers;
	}

	public void setPayers(Set<Customer> payers) {
		this.payers = payers;
	}
	
}
