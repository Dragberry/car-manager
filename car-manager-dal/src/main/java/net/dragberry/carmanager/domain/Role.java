package net.dragberry.carmanager.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
@AttributeOverrides({
	@AttributeOverride(column = @Column(name =  "ROLE_KEY"), name = "entityKey")
})
public class Role extends AbstractEntity {
	
	private static final long serialVersionUID = 2965399771263638041L;

	@Column(name = "ROLE_NAME")
	private String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
