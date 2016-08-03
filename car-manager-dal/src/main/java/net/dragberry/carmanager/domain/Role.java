package net.dragberry.carmanager.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 2965399771263638041L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROLE_KEY")
	private Long roleKey;

	@Column(name = "ROLE_NAME")
	private String roleName;

	public Long getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(Long roleKey) {
		this.roleKey = roleKey;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	
}
