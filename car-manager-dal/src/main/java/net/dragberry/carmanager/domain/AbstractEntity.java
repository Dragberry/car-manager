package net.dragberry.carmanager.domain;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.StringUtils;

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
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("[").append(entityKey).append("]=[\n");
		for (Method method : getClass().getMethods()) {
			if (method.getName().startsWith("get") && !"getClass".equals(method.getName())) {
				sb.append("\t[");
				sb.append(method.getName().replace("get", StringUtils.EMPTY));
				sb.append("=");
				try {
					sb.append(method.invoke(this));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					sb.append("error");
				}
				sb.append("]\n");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	
}
