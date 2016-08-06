package net.dragberry.carmanager.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 54446865118204573L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ENTITY_KEY")
	private Long entityKey;

	public Long getEntityKey() {
		return entityKey;
	}

	public void setEntityKey(Long entityKey) {
		this.entityKey = entityKey;
	}
	
}
