package net.dragberry.carmanager.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "ROLE")
@TableGenerator(
		name = "ROLE_GEN", 
		table = "GENERATOR",
		pkColumnName = "GEN_NAME", 
		pkColumnValue = "ROLE_GEN",
		valueColumnName = "GEN_VALUE",
		initialValue = 1000,
		allocationSize = 1)
public class Role extends AbstractEntity {
	
	private static final long serialVersionUID = 2965399771263638041L;

	@Id
	@Column(name = "ROLE_KEY")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ROLE_GEN")
	private Long entityKey;
	
	@Column(name = "ROLE_NAME")
	private String roleName;
	
	@Override
	public Long getEntityKey() {
		return entityKey;
	}
	
	@Override
	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
